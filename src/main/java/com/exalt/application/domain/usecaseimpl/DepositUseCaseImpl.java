package com.exalt.application.domain.usecaseimpl;

import com.exalt.application.domain.model.BankAccount;
import com.exalt.application.port.driven.LoadBankAccountPort;
import com.exalt.application.port.driven.UpdateAccountStatePort;
import com.exalt.application.port.driving.DepositUseCase;
import com.exalt.common.customannotation.UseCase;
import com.exalt.common.exceptions.AuthorizedMaximumBookletBalanceExeeded;
import com.exalt.common.exceptions.IllegalAmountException;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;

@UseCase
@Transactional
public class DepositUseCaseImpl implements DepositUseCase {
    private LoadBankAccountPort loadBankAccountPort;
    private UpdateAccountStatePort updateAccountStatePort;

    public DepositUseCaseImpl(LoadBankAccountPort loadBankAccountPort, UpdateAccountStatePort updateAccountStatePort) {
        this.loadBankAccountPort = loadBankAccountPort;
        this.updateAccountStatePort = updateAccountStatePort;
    }

    @Override
    public void depositMoney(Long id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0 ) {
            throw new IllegalAmountException("Amount should be positive");
        }

        BankAccount account = loadBankAccountPort.loadAccount(id);

        boolean hasDeposit = account.depositMoney(account, amount);

        if(hasDeposit) {
            updateAccountStatePort.updateActivities(account);
        }
        else {
            throw new AuthorizedMaximumBookletBalanceExeeded("Operation denied because deposit limit has been reached for the booklet account");
        }
    }
}
