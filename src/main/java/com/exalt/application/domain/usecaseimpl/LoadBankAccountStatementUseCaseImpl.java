package com.exalt.application.domain.usecaseimpl;

import com.exalt.application.domain.model.BankAccount;
import com.exalt.application.port.driven.LoadBankAccountPort;
import com.exalt.application.port.driving.LoadBankAccountStatementUseCase;
import com.exalt.common.customannotation.UseCase;
import jakarta.transaction.Transactional;

@UseCase
public class LoadBankAccountStatementUseCaseImpl implements LoadBankAccountStatementUseCase {
    private final LoadBankAccountPort loadBankAccountPort;

    public LoadBankAccountStatementUseCaseImpl(LoadBankAccountPort loadBankAccountPort) {
        this.loadBankAccountPort = loadBankAccountPort;
    }

    @Override
    public BankAccount loadBankAccountStatement(Long accountId) {
        return loadBankAccountPort.loadAccount(accountId);
    }
}
