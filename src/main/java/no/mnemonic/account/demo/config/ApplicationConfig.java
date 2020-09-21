package no.mnemonic.account.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import no.mnemonic.account.demo.repository.entity.mapper.AccountEntityMapper;
import no.mnemonic.account.demo.repository.entity.mapper.TransactionEntityMapper;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "no.mnemonic.account.demo")
public class ApplicationConfig {

    @Bean
    public AccountEntityMapper accountEntityMapper() {
        return new AccountEntityMapper();
    }

    @Bean
    public TransactionEntityMapper transactionEntityMapper() {
        return new TransactionEntityMapper();
    }

    @Bean
    public ObjectMapper jsonObjectMapper() {

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        return Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .modules(javaTimeModule)
                .build();
    }
}
