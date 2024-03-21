package com.exalt.application.port.driven;

import com.exalt.application.domain.model.BankAccount;

public interface CreateBankAccountPort {
    BankAccount createAccount(BankAccount account);
}