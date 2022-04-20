package com.batchdemo.quotegenerator.boot.service;

import com.batchdemo.quotegenerator.batch.entity.Recipient;
import com.batchdemo.quotegenerator.boot.dao.TextSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class RecipientService {

    private final TextSender textSender;

    @Autowired
    public RecipientService(@Qualifier("EmailSender") TextSender textSender) {
        this.textSender = textSender;
    }

    public void sendEmail(Recipient recipient, String subject, String message) {
        this.textSender.send(recipient.getEmail(), subject, message);
    }
}
