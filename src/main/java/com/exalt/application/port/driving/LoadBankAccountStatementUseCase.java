package com.exalt.application.port.driving;

import com.exalt.application.domain.model.BankAccount;

public interface LoadBankAccountStatementUseCase {
    /*
    the account holds one month interval from now of account transactions
    and calculate dynamically the baseline balance one month from now
     */
    BankAccount loadBankAccountStatement(Long accountId);
}