package com.batchdemo.quotegenerator.boot.dao;

public interface TextSender {

    void send(String recipientEmail, String subject, String message);
}
