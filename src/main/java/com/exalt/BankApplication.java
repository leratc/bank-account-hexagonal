package com.exalt;

import com.exalt.adapters.driven.persistence.BankAccountRepositoryComponent;
import com.exalt.application.domain.model.BankAccount;
import com.exalt.common.commontype.AccountType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class BankApplication {
    public static void main(final String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

    @Bean
    public CommandLineRunner bootstrapData(BankAccountRepositoryComponent repository) {
        return (args) -> {

        };
    }
}
