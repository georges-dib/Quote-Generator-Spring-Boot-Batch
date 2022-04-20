package com.batchdemo.quotegenerator.batch.repository;

import com.batchdemo.quotegenerator.boot.model.PropertiesReader;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository("QuoteFetcher")
public class QuoteFetcher implements Tasklet {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    PropertiesReader propertiesReader;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        //default value
        String quote = "Today is all yours, you get to use it however you choose!";

        try {
            //preparing http request
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("X-RapidAPI-Key", propertiesReader.getProperty("rapid_api_key"));
            httpHeaders.set("X-RapidAPI-Host", "motivational-quotes1.p.rapidapi.com");

            HttpEntity request =
                    new HttpEntity(new JSONObject().toString(), httpHeaders);

            ResponseEntity<String> quoteResp = restTemplate.postForEntity(
                    "https://motivational-quotes1.p.rapidapi.com/motivation",
                    request, String.class);
            quote = quoteResp.getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //setting the quote in the execution context to be used in later steps
        chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .put("quote", quote);

        return RepeatStatus.FINISHED;
    }
}
