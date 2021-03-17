package util;

import config.ApplicationContext;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    private EmailSender() {
    }

    public static boolean send(String mailTo, String subject, String text) {
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        final String username = ApplicationContext.getPROPERTIES().getProperty("mail.user");
        final String password = ApplicationContext.getPROPERTIES().getProperty("mail.foruser");
        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mailTo, false));
            msg.setSubject(subject);
            msg.setText(text);
            Transport.send(msg);
            return true;
        } catch (MessagingException e) {
            System.out.println(e);
            return false;
        }
    }
}
