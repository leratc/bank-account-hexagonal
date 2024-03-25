package com.exalt.application.domain;

import com.exalt.application.domain.model.AccountTransactionsInterval;
import com.exalt.application.domain.model.BankAccount;
import com.exalt.common.AccountTestData;
import com.exalt.common.AccountTransactionTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

class BankAccountTest {

	@Test
	void calculatesBalance() {
		Long accountId = 1L;
		BankAccount account = AccountTestData.defaultCurrentAccount()
				.id(accountId)
				.baselineBalance(BigDecimal.valueOf(555L))
				.accountTransactionsInterval(new AccountTransactionsInterval(
						Arrays.asList(AccountTransactionTestData.defaultDepositTransaction().accountId(accountId)
								.amount(BigDecimal.valueOf(999L)).build(),
								AccountTransactionTestData.defaultDepositTransaction().accountId(accountId)
										.amount(BigDecimal.valueOf(1L)).build())))
				.build();

		BigDecimal balance = account.calculateBalance();
		assertThat(account.calculateBalance()).isEqualTo(BigDecimal.valueOf(1555L));
	}

	@Test
	void withdrawalSucceeds() {
		Long accountId = 1L;
		BankAccount account = AccountTestData.defaultCurrentAccount()
				.id(accountId)
				.baselineBalance(BigDecimal.valueOf(555L))
				.accountTransactionsInterval(new AccountTransactionsInterval(Arrays.asList(AccountTransactionTestData.defaultDepositTransaction().accountId(accountId)
								.amount(BigDecimal.valueOf(999L)).build(),
						AccountTransactionTestData.defaultDepositTransaction().accountId(accountId)
								.amount(BigDecimal.valueOf(1L)).build())))
				.build();

		boolean success = account.withdrawMoney(account,BigDecimal.valueOf(555L));

		assertThat(success).isTrue();
		assertThat(account.getAccountTransactionsInterval().getAccountTransactions()).hasSize(3);
		assertThat(account.calculateBalance()).isEqualTo(BigDecimal.valueOf(1000L));
	}

	@Test
	void withdrawalFailure() {
		Long accountId = 1L;
		BankAccount account = AccountTestData.defaultCurrentAccount()
				.id(accountId)
				.baselineBalance(BigDecimal.valueOf(555L))
				.accountTransactionsInterval(new AccountTransactionsInterval(Arrays.asList(AccountTransactionTestData.defaultDepositTransaction().accountId(accountId)
								.amount(BigDecimal.valueOf(999L)).build(),
						AccountTransactionTestData.defaultDepositTransaction().accountId(accountId)
								.amount(BigDecimal.valueOf(1L)).build())))
				.build();
		boolean success = account.withdrawMoney(account,BigDecimal.valueOf(2556L));

		assertThat(success).isFalse();
		assertThat(account.getAccountTransactionsInterval().getAccountTransactions()).hasSize(2);
		assertThat(account.calculateBalance()).isEqualTo(BigDecimal.valueOf(1555L));
	}

	@Test
	void depositSuccess() {
		Long accountId = 1L;
		BankAccount account = AccountTestData.defaultCurrentAccount()
				.id(accountId)
				.baselineBalance(BigDecimal.valueOf(555L))
				.accountTransactionsInterval(new AccountTransactionsInterval(Arrays.asList(AccountTransactionTestData.defaultDepositTransaction().accountId(accountId)
								.amount(BigDecimal.valueOf(999L)).build(),
						AccountTransactionTestData.defaultDepositTransaction().accountId(accountId)
								.amount(BigDecimal.valueOf(1L)).build())))
				.build();
		boolean success = account.depositMoney(account,BigDecimal.valueOf(445L));
		assertThat(success).isTrue();
		assertThat(account.getAccountTransactionsInterval().getAccountTransactions()).hasSize(3);
		assertThat(account.calculateBalance()).isEqualTo(BigDecimal.valueOf(2000L));
	}
	@Test
	void depositBookletFail() {
		Long accountId = 1L;
		BankAccount bankAccount = AccountTestData.defaultBookletAccount()
				.id(accountId)
				.baselineBalance(BigDecimal.valueOf(20555L))
				.maximumDepositAuthorization(BigDecimal.valueOf(22950L))
				.accountTransactionsInterval(new AccountTransactionsInterval(Arrays.asList(AccountTransactionTestData.defaultDepositTransaction().accountId(accountId)
								.amount(BigDecimal.valueOf(1999L)).build(),
						AccountTransactionTestData.defaultDepositTransaction().accountId(accountId)
								.amount(BigDecimal.valueOf(1L)).build())))
				.build();
		boolean success = bankAccount.depositMoney(bankAccount, BigDecimal.valueOf(945L));
		Assertions.assertFalse(success);
	}
	@Test
	void depositBookletSuccess() {
		Long accountId = 1L;
		BankAccount bankAccount = AccountTestData.defaultBookletAccount()
				.id(accountId)
				.baselineBalance(BigDecimal.valueOf(10555L))
				.maximumDepositAuthorization(BigDecimal.valueOf(22950L))
				.accountTransactionsInterval(new AccountTransactionsInterval(Arrays.asList(AccountTransactionTestData.defaultDepositTransaction().accountId(accountId)
								.amount(BigDecimal.valueOf(1999L)).build(),
						AccountTransactionTestData.defaultDepositTransaction().accountId(accountId)
								.amount(BigDecimal.valueOf(1L)).build())))
				.build();
		boolean success = bankAccount.depositMoney(bankAccount, BigDecimal.valueOf(945L));
		Assertions.assertTrue(success);
	}

}