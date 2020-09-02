package com.jb.MailService;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Service {

    Properties prop = new Properties();

    public void loadProperties() {
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.starttls.enable", "false");
        prop.put("mail.smtp.host", "smtp.poczta.onet.pl");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.trust", "smtp.poczta.onet.pl");
    }

    Session session = Session.getInstance(prop, new Authenticator() {

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("jakub.bienkowski94@onet.pl", "infoShare1");
        }
    });

    public void send(String email, String subject, String userMessage) throws MessagingException {
        session.setDebug(true);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("jakub.bienkowski94@onet.pl"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(userMessage, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);
        Transport.send(message);
    }

}
