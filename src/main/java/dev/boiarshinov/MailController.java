package dev.boiarshinov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.URISyntaxException;

@RestController
@RequestMapping("mail")
public class MailController {

    @Autowired
    private final JavaMailSender mailSender;

    public MailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("/helper")
    public String sendByHelper() throws MessagingException, URISyntaxException {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setFrom("artem.boiar@yandex.ru");
        messageHelper.setTo("artyom.boyarshinov@cosysoft.ru");
        messageHelper.setSubject("Java 20 new hot features");
        messageHelper.setText("Java 20 new hot features. Look at the attachment");

        messageHelper.addAttachment("java-new-features.txt", FileUtils.getFile());

        this.mailSender.send(mimeMessage);

        return "Mail sent";
    }

    @GetMapping("/preparator")
    public String sendByPreparator() {
        final MimeMessagePreparator preparator = mimeMessage -> {
            final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setFrom("artem.boiar@yandex.ru");
            messageHelper.setTo("artyom.boyarshinov@cosysoft.ru");
            messageHelper.setSubject("Java 20 new hot features");
            messageHelper.setText("Java 20 new hot features. Look at the attachment");

            messageHelper.addAttachment("java-new-features.txt", FileUtils.getFile());
        };

        this.mailSender.send(preparator);

        return "Mail sent";
    }

    @GetMapping("/simple")
    public String sendSimple() {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("artem.boiar@yandex.ru");
        mailMessage.setTo("artyom.boyarshinov@cosysoft.ru");
        mailMessage.setSubject("Java 20 new hot features");
        mailMessage.setText("Java 20 new hot features. No attachments :(");

        this.mailSender.send(mailMessage);

        return "Mail sent";
    }
}
