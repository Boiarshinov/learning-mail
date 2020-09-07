package dev.boiarshinov;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

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
}
