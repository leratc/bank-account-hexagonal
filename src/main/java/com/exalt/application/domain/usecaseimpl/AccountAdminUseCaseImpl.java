package com.exalt.application.domain.usecaseimpl;

import com.exalt.application.domain.model.BankAccount;
import com.exalt.application.port.driven.CreateBankAccountPort;
import com.exalt.application.port.driven.UpdateAccountStatePort;
import com.exalt.application.port.driving.AccountAdminUseCase;
import com.exalt.common.customannotation.UseCase;
import jakarta.transaction.Transactional;

@UseCase
@Transactional

public class AccountAdminUseCaseImpl implements AccountAdminUseCase {
    private final CreateBankAccountPort createBankAccountPort;
    public AccountAdminUseCaseImpl(CreateBankAccountPort createBankAccountPort) {
        this.createBankAccountPort = createBankAccountPort;
    }

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {
        return createBankAccountPort.createAccount(bankAccount);
    }
}
