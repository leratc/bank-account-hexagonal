package com.exalt.adapters.driven.persistence;

import com.exalt.adapters.driven.persistence.jpaentity.AccountTransactionEntity;
import com.exalt.adapters.driven.persistence.jpaentity.BankAccountEntity;
import com.exalt.adapters.driven.persistence.jparepository.AccountTransactionRepository;
import com.exalt.adapters.driven.persistence.jparepository.BankAccountRepository;
import com.exalt.adapters.driven.persistence.mapper.AccountTransactionMapper;
import com.exalt.adapters.driven.persistence.mapper.BankAccountMapper;
import com.exalt.application.domain.model.AccountTransaction;
import com.exalt.application.domain.model.AccountTransactionsInterval;
import com.exalt.application.domain.model.BankAccount;
import com.exalt.application.port.driven.CreateBankAccountPort;
import com.exalt.application.port.driven.LoadBankAccountPort;
import com.exalt.application.port.driven.UpdateAccountStatePort;
import com.exalt.BankConfigurationProperties;
import com.exalt.common.commontype.AccountType;
import com.exalt.common.customannotation.PersistenceAdapter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@PersistenceAdapter
public class BankAccountRepositoryComponent
        implements LoadBankAccountPort, UpdateAccountStatePort, CreateBankAccountPort {

    private final BankAccountRepository bankAccountRepository;
    private final AccountTransactionRepository accountTransactionRepository;
    private final BankAccountMapper bankAccountMapper;
    private final AccountTransactionMapper accountTransactionMapper;
    private final BankConfigurationProperties bankConfigurationProperties;
    @Override
    public BankAccount loadAccount(Long accountId) {

        LocalDateTime baselineDate = LocalDateTime.now().minusMonths(1);

        BankAccountEntity accountEntity =
                bankAccountRepository.findById(accountId)
                        .orElseThrow(EntityNotFoundException::new);

        List<AccountTransactionEntity> transactionsEntity =
                accountTransactionRepository.findByAccountIdSince(
                        accountId,
                        baselineDate);

        BigDecimal withdrawalBalance = accountTransactionRepository
                .getWithdrawalBalanceUntil(
                        accountId,
                        baselineDate)
                .orElse(BigDecimal.ZERO);

        BigDecimal depositBalance = accountTransactionRepository
                .getDepositBalanceUntil(
                        accountId,
                        baselineDate)
                .orElse(BigDecimal.ZERO);
        BigDecimal baselineBalance= depositBalance.subtract(withdrawalBalance);
        BankAccount bankAccount = bankAccountMapper.toDomain(accountEntity);

        // additionnal setting to simple mapping
        // threhold specific to BOOKLET account
        if (AccountType.BOOKLET.equals(bankAccount.getAccountType())) {
            // from application properties
            bankAccount.setMaximumDepositAuthorization(bankConfigurationProperties.getDepositBookletThreshold());
        }
        // from dynamic calculation until 1 month from now
        bankAccount.setBaselineBalance(baselineBalance);
        // the transactions since 1 month
        List<AccountTransaction> transactionsDomain = transactionsEntity.stream().map(accountTransactionMapper::toDomain)
                .sorted(Comparator.comparing(AccountTransaction::getTransactionDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
        bankAccount.setAccountTransactionsInterval(new AccountTransactionsInterval(transactionsDomain));
        return bankAccount;
    }

    @Override
    public void updateActivities(BankAccount account) {
        for (AccountTransaction accountTransaction : account.getAccountTransactionsInterval().getAccountTransactions()) {
            if (accountTransaction.getId() == null) {
                accountTransactionRepository.save(accountTransactionMapper.toEntity(accountTransaction));
            }
        }
    }

    @Override
    public BankAccount createAccount(BankAccount account) {
        BankAccountEntity bankAccountEntityCreated = bankAccountRepository.save(bankAccountMapper.toEntity(account));

        BankAccount newAccount = bankAccountMapper.toDomain(bankAccountEntityCreated);
        // belong to application parameter, not the entity
        newAccount.setMaximumDepositAuthorization(account.getMaximumDepositAuthorization());
        return newAccount;
    }
}
