package com.batchdemo.quotegenerator.boot.model;

import com.batchdemo.quotegenerator.boot.dao.TextSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

@Repository("EmailSender")
public class EmailSender implements TextSender {

    private JavaMailSender javaMailSenderImpl;

    @Autowired
    public EmailSender(JavaMailSender javaMailSenderImpl) {
        this.javaMailSenderImpl = javaMailSenderImpl;
    }

    @Override
    public void send(String recipientEmail, String subject, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(recipientEmail);

        msg.setSubject(subject);
        msg.setText(message);

        this.javaMailSenderImpl.send(msg);
    }
}
