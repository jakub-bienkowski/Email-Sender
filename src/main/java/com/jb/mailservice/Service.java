package com.jb.mailservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Map;
import java.util.Properties;

public class Service {

    private static final Logger STDOUT = LoggerFactory.getLogger("CONSOLE_OUT");

    Properties prop = new Properties();

    public void loadProperties() {
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.starttls.enable", "false");
        prop.put("mail.smtp.host", "smtp.poczta.onet.pl");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.trust", "smtp.poczta.onet.pl");
    }

    //e-mail is created only for the project, that given the password is not hidden
    Session session = Session.getInstance(prop, new Authenticator() {

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("jakub.bienkowski94@onet.pl", "infoShare1");
        }
    });

    public boolean send(Map<String, String> data){

        if (!DataCheck.isEmailCorrect(data.get("email"))) {
            return false;
        }

        try {
            session.setDebug(true);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("jakub.bienkowski94@onet.pl"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(data.get("email")));
            message.setSubject(data.get("subject"));

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(data.get("message"), "text/html; charset=UTF-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart, "UTF-8");
            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            STDOUT.error(e.getMessage());
            return false;
        }
    }

}
