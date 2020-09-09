package dev.boiarshinov;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class AppConfig {

    @Bean
    public JavaMailSender createMailSender() {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.yandex.ru");
        mailSender.setProtocol("smtps");
        mailSender.setPort(465);
        mailSender.setUsername("artem.boiar");
        mailSender.setPassword(PropUtils.getPassword());

        final Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.debug", "true");

        return mailSender;
    }
}
