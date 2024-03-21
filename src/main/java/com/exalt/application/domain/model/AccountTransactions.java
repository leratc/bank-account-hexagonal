package com.exalt.application.domain.model;

import com.exalt.common.commontype.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
@Getter
@Data
@Builder
@ToString
public class AccountTransactions {
    /**
     * handle the collection of transactions for one account
     */
    private @Getter List<AccountTransaction> accountTransactions;

    /**
     * The timestamp of the first transaction within this window.
     */
    public LocalDateTime getStartTimestamp() {
        return accountTransactions.stream()
                .min(Comparator.comparing(AccountTransaction::getTransactionDate))
                .orElseThrow(IllegalStateException::new)
                .getTransactionDate();
    }

    /**
     * The timestamp of the last transaction within this window.
     *
     * @return
     */
    public LocalDateTime getEndTimestamp() {
        return accountTransactions.stream()
                .max(Comparator.comparing(AccountTransaction::getTransactionDate))
                .orElseThrow(IllegalStateException::new)
                .getTransactionDate();
    }

    /**
     * Calculates the balance by summing up the values of all activities within this window.
     */
    public BigDecimal calculateBalance(Long accountId) {
        BigDecimal depositBalance = accountTransactions.stream()
                .filter(a -> TransactionType.DEPOSIT.equals(a.getTransactionType()) && a.getAccountId().equals(accountId))
                .map(AccountTransaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal withdrawalBalance = accountTransactions.stream()
                .filter(a -> TransactionType.WITHDRAWAL.equals(a.getTransactionType()) && a.getAccountId().equals(accountId))
                .map(AccountTransaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return depositBalance.subtract(withdrawalBalance);
    }

    public AccountTransactions(@NonNull List<AccountTransaction> accountTransactions) {
        this.accountTransactions = accountTransactions;
    }

    public AccountTransactions(@NonNull AccountTransaction... accountTransactions) {
        this.accountTransactions = new ArrayList<>(Arrays.asList(accountTransactions));
    }

    public List<AccountTransaction> getAccountTransactions() {
        return this.accountTransactions;
    }

    public void addAccountTransaction(AccountTransaction accountTransaction) {
        List<AccountTransaction> accountTransactionsTemp = new ArrayList<>(this.getAccountTransactions());
        accountTransactionsTemp.add(accountTransaction);
        this.accountTransactions = accountTransactionsTemp;
    }
}
