package com.exalt.application.domain.usecaseimpl;

import com.exalt.application.domain.model.BankAccount;
import com.exalt.application.port.driven.LoadBankAccountPort;
import com.exalt.application.port.driven.UpdateAccountStatePort;
import com.exalt.application.port.driving.WithdrawUseCase;
import com.exalt.common.customannotation.UseCase;
import com.exalt.common.exceptions.AuthorizedOverdraftAccountBalanceExeeded;
import com.exalt.common.exceptions.IllegalAmountException;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;

@UseCase
@Transactional
public class WithdrawUseCaseImpl implements WithdrawUseCase {

    private LoadBankAccountPort loadBankAccountPort;
    private UpdateAccountStatePort updateAccountStatePort;

    public WithdrawUseCaseImpl(LoadBankAccountPort loadBankAccountPort, UpdateAccountStatePort updateAccountStatePort) {
        this.loadBankAccountPort = loadBankAccountPort;
        this.updateAccountStatePort = updateAccountStatePort;
    }

    @Override
    public void withdrawMoney(Long id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0 ) {
            throw new IllegalAmountException("Amount should be positive");
        }

        BankAccount account = loadBankAccountPort.loadAccount(id);

        boolean hasWithdrawn = account.withdrawMoney(account, amount);

        if(hasWithdrawn) {
            updateAccountStatePort.updateActivities(account);
        }
        else {
            throw new AuthorizedOverdraftAccountBalanceExeeded("Operation denied because withdraw amount exceed authorized overdraft for the account.");
        }
    }
}
