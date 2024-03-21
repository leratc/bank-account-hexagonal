package com.exalt.application.port.driven;

import com.exalt.application.domain.model.BankAccount;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LoadBankAccountPort {
    BankAccount loadAccount(Long accountId);
}