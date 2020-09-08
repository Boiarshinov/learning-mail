package dev.boiarshinov;

import org.junit.jupiter.api.Test;

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

public class SendMailApp {
    public static void main(String[] args) throws Exception {
        Session.getInstance(PropUtils.getMailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    this.getDefaultUserName(),
                    PropUtils.getPassword()
                );
            }
        });
    }

    @Test
    public void openSession() {
        this.createSession();
    }

    @Test
    public void createMessage() throws Exception {
        final Session session = this.createSession();
        final MimeMessage message = new MimeMessage(session);
        final Multipart multipart = this.createMultipart();
        message.setContent(multipart);
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

    private Set<BodyPart> createBodyParts() throws Exception {
        final MimeBodyPart mailBody = new MimeBodyPart();
        mailBody.setText("Java 20 new features");

        final MimeBodyPart attachment1 = new MimeBodyPart();
        attachment1.attachFile(getFile());

        final MimeBodyPart attachment2 = new MimeBodyPart();
        //todo add dataHandler

        return Stream.of(mailBody, attachment1, attachment2).collect(Collectors.toSet());
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
