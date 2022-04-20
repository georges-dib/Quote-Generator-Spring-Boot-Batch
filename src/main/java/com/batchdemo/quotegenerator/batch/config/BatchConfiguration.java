package com.batchdemo.quotegenerator.batch.config;

import com.batchdemo.quotegenerator.batch.entity.Recipient;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job getJob(Step getQuote, Step sendEmails) {

        return this.jobBuilderFactory.get("Send-Email-Job")
                .incrementer(new RunIdIncrementer())
                .start(getQuote).on("FAILED").end()
                .from(getQuote).on("*").to(sendEmails).end()
                .build();
    }

    @Bean
    public FlatFileItemReader<Recipient> itemReader() {
        FlatFileItemReader<Recipient> itemReader = new FlatFileItemReader<>();
        itemReader.setName("CSV-Reader");
        itemReader.setResource(new FileSystemResource("src/main/resources/recipients.csv"));
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(getLineMapper());

        return itemReader;
    }

    public LineMapper<Recipient> getLineMapper() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("recipientId","firstName","lastName","email");
        lineTokenizer.setStrict(false);

        BeanWrapperFieldSetMapper<Recipient> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Recipient.class);

        DefaultLineMapper<Recipient> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public Step getQuote(Tasklet tasklet) {
        return this.stepBuilderFactory.get("get-quote").tasklet(tasklet).build();
    }

    @Bean
    public Step sendEmails(
            ItemReader<Recipient> itemReader,
            ItemProcessor<Recipient, Recipient> itemProcessor,
            ItemWriter<Recipient> itemWriter) {

        Step step = this.stepBuilderFactory.get("Send-Email-Job-Step")
                .<Recipient, Recipient>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return step;
    }
}
