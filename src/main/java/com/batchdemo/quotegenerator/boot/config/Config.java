package com.batchdemo.quotegenerator.boot.config;

import com.batchdemo.quotegenerator.boot.dao.TextSender;
import com.batchdemo.quotegenerator.boot.model.PropertiesReader;
import com.batchdemo.quotegenerator.boot.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Properties;

@Configuration
public class Config {

    private PropertiesReader propertiesReader;

    @Autowired
    public Config(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    @Bean
    public JavaMailSender javaMailSender() {

        Map<String, String> fileProperties = this.propertiesReader.getPoperties();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(fileProperties.get("username"));
        mailSender.setPassword(fileProperties.get("password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public RecipientService recipientService(TextSender textSender) {
        return new RecipientService(textSender);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
