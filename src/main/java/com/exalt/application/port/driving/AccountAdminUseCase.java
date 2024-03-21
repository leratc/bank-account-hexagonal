package com.exalt.application.port.driving;

import com.exalt.application.domain.model.BankAccount;

public interface AccountAdminUseCase {
    BankAccount createBankAccount(BankAccount bankAccount);
}
