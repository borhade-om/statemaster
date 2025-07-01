package com.sm.statemaster.config;

import com.sm.statemaster.dto.pincode.PinCodeSearchDto;
import com.sm.statemaster.entity.PinCode;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.multipart.MultipartFile;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job pinCodeJob(JobRepository jobRepository, Step steps, JobCompletionNotificationImpl listener) {
        return new JobBuilder("job", jobRepository)
                .listener(listener)
                .start(steps)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager transactionManager,
                     ItemReader<PinCodeSearchDto> reader,
                     ItemProcessor<PinCodeSearchDto, PinCode> processor,
                     ItemWriter<PinCode> writer) {
        return new StepBuilder("step", jobRepository)
                .<PinCodeSearchDto, PinCode>chunk(1000, transactionManager) // CHUNK CONFIGURED HERE
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    

}
