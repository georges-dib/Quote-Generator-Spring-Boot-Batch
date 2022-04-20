package com.batchdemo.quotegenerator.batch.step;

import com.batchdemo.quotegenerator.batch.entity.Recipient;
import com.batchdemo.quotegenerator.boot.service.RecipientService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class RecipientProcessor implements ItemProcessor<Recipient, Recipient> {

    private final RecipientService recipientService;

//    @Value("#{jobParameters['quote']}")       //this allows reading quote from jobParameters
    @Value("#{jobExecutionContext['quote']}")   //this allows reading quote from QuoteFetcher step
    private String quote;

    @Autowired
    public RecipientProcessor(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    @Override
    public Recipient process(Recipient recipient) {
        StringBuilder message = new StringBuilder();
        message.append("Hello ").append(recipient.getFirstName()).append("!\n");
        message.append("Today is awesome and your quote for the day is:\n\n").append(quote);
        message.append("\n\n").append("Have an amazing day!");
        message.append("\n").append("Sincerely,");

        this.recipientService.sendEmail(recipient,
                "Quote of the day",
                message.toString());
        return recipient;
    }
}
