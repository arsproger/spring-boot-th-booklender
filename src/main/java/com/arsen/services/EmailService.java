package com.arsen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }

    }

    public void sendFeedback(String name, String email, String message) {

        String to = "booklender.it.academy@mail.com";
        String subject = "Обратная связь";
        String body = "Имя: " + name + "\n\n" + "Эл.адрес: " + email + "\n\n" + "Сообщение: " + message;

        Properties properties = System.getProperties();
        properties.setProperty("smtp.gmail.com", email);

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress(email));

            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            mimeMessage.setSubject(subject);

            mimeMessage.setText(body);

            Transport.send(mimeMessage);
            System.out.println("Message sent successfully...");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}

