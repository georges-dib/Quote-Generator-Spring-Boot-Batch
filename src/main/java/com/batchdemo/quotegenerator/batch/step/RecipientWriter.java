package com.batchdemo.quotegenerator.batch.step;

import com.batchdemo.quotegenerator.batch.entity.Recipient;
import com.batchdemo.quotegenerator.batch.repository.RecipientRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipientWriter implements ItemWriter<Recipient> {
    public RecipientRepository recipientRepository;

    @Autowired
    public RecipientWriter(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    @Override
    public void write(List<? extends Recipient> list) throws Exception {
        recipientRepository.saveAll(list);
        System.out.println("Item Writer finished execution");
    }
}
