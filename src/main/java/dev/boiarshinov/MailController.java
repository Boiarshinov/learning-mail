package dev.boiarshinov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.URISyntaxException;

@RestController
public class MailController {

    @Autowired
    private final JavaMailSender mailSender;

    public MailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("/mail")
    public void send() throws MessagingException, URISyntaxException {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setFrom("artem.boiar@yandex.ru");
        messageHelper.setTo("artyom.boyarshinov@cosysoft.ru");
        messageHelper.setSubject("Java 20 new hot features");
        messageHelper.setText("Java 20 new hot features. Look at the attachment");

        messageHelper.addAttachment("java-new-features.txt", FileUtils.getFile());

        this.mailSender.send(mimeMessage);
    }
}
