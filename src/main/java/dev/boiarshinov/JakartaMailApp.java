package dev.boiarshinov;

import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JakartaMailApp {

    @Test
    public void openSession() {
        final Session session = this.createSession();
    }

    @Test
    public void createMessage() throws Exception {
        final Session session = this.createSession();
        final Message message = this.createMessage(session);
    }

    @Test
    public void createMessageWithContent() throws Exception {
        final Session session = this.createSession();
        final Message message = this.createMessage(session);
        final Multipart multipart = this.createMultipart();
        message.setContent(multipart);
    }

    @Test
    public void sendMessage() throws Exception {
        final Session session = this.createSession();
        final Message message = this.createMessage(session);
        final Multipart multipart = this.createMultipart();
        message.setContent(multipart);
        Transport.send(message);
    }

    private Session createSession() {
        final Properties mailProperties = PropUtils.getMailProperties();
        final Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    this.getDefaultUserName(),
                    PropUtils.getPassword()
                );
            }
        };
        return Session.getInstance(mailProperties, authenticator);
    }

    private Message createMessage(Session session) throws MessagingException {
        final MimeMessage message = new MimeMessage(session);
        message.setFrom("artem.boiar@yandex.ru");
        message.setRecipients(Message.RecipientType.TO, "artyom.boyarshinov@cosysoft.ru");
//        message.setRecipients(Message.RecipientType.TO, "joshua.bloch@google.com");
//        message.setRecipients(Message.RecipientType.CC, "tagir.valeev@jetbrains.com");
//        message.setRecipients(Message.RecipientType.BCC, "sergey.egorov@pivotal.com");
        message.setSubject("Java 20 new hot features");
        return message;
    }

    private Set<BodyPart> createBodyParts() throws Exception {
        final MimeBodyPart mailBody = new MimeBodyPart();
        mailBody.setText("Java 20 new features");

        final MimeBodyPart attachment1 = new MimeBodyPart();
        attachment1.attachFile(getFile());

//        final MimeBodyPart attachment2 = new MimeBodyPart();
//        final FileDataSource fileDataSource = new FileDataSource(getFile());
//        attachment2.setDataHandler(new DataHandler(fileDataSource));

        return Stream.of(mailBody, attachment1).collect(Collectors.toSet());
    }

    public Multipart createMultipart() throws Exception {
        final Set<BodyPart> bodyParts = this.createBodyParts();
        final Multipart multipart = new MimeMultipart();
        for (BodyPart bodyPart: bodyParts) {
            //cannot use streams because of throwing exception
            multipart.addBodyPart(bodyPart);
        }
        return multipart;
    }

    private File getFile() throws URISyntaxException {
        final URL resource = ClassLoader.getSystemResource("java_new_features.txt");
        return Paths.get(resource.toURI()).toFile();
    }
}
