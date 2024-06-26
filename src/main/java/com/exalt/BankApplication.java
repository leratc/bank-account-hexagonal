package com.exalt;

import com.exalt.adapters.driven.persistence.BankAccountRepositoryComponent;
import com.exalt.application.domain.model.BankAccount;
import com.exalt.common.commontype.AccountType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
@EntityScan
public class BankApplication {
    public static void main(final String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }
}
