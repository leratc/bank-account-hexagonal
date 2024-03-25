package com.exalt.application.domain;
import com.exalt.application.domain.model.AccountTransaction;
import com.exalt.application.domain.model.AccountTransactionsInterval;
import com.exalt.application.domain.model.BankAccount;
import com.exalt.common.AccountTestData;
import com.exalt.common.AccountTransactionTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

class AcccountTransactionsTest {

	@Test
	void calculatesStartTimestamp() {
		List<AccountTransaction> transactions = Arrays.asList(
				AccountTransactionTestData.defaultDepositTransaction().transactionDate(startDate()).build(),
				AccountTransactionTestData.defaultDepositTransaction().transactionDate(inBetweenDate()).build(),
				AccountTransactionTestData.defaultDepositTransaction().transactionDate(endDate()).build());
		AccountTransactionsInterval accountTransactionsInterval = new AccountTransactionsInterval(transactions);
		Assertions.assertThat(accountTransactionsInterval.getStartTimestamp()).isEqualTo(startDate());
	}

	@Test
	void calculatesEndTimestamp() {
		List<AccountTransaction> transactions = Arrays.asList(
						AccountTransactionTestData.defaultDepositTransaction().transactionDate(startDate()).build(),
						AccountTransactionTestData.defaultDepositTransaction().transactionDate(inBetweenDate()).build(),
						AccountTransactionTestData.defaultDepositTransaction().transactionDate(endDate()).build());
		AccountTransactionsInterval accountTransactionsInterval = new AccountTransactionsInterval(transactions);
		Assertions.assertThat(accountTransactionsInterval.getEndTimestamp()).isEqualTo(endDate());
	}

	@Test
	void calculatesBalance() {
		Long accountId=1L;
		// the other way of initialize
		AccountTransactionsInterval accountTransactionsInterval = new AccountTransactionsInterval(
					AccountTransactionTestData.defaultWithdrawalTransaction().accountId(accountId)
							.amount(BigDecimal.valueOf(999L)).build(),
					AccountTransactionTestData.defaultWithdrawalTransaction().accountId(accountId)
							.amount(BigDecimal.valueOf(1L)).build(),
					AccountTransactionTestData.defaultDepositTransaction().accountId(accountId)
							.amount(BigDecimal.valueOf(500L)).build()
				);
		Assertions.assertThat(accountTransactionsInterval.calculateBalance(accountId)).isEqualTo(BigDecimal.valueOf(-500));
	}
	@Test
	void testAddTransaction() {
		Long accountId = 1L;
		BankAccount account = AccountTestData.defaultCurrentAccount().id(accountId).build();
		// 2 initial transactions
		AccountTransaction accountTransaction = AccountTransactionTestData.defaultWithdrawalTransaction().accountId(accountId)
						.amount(BigDecimal.valueOf(999L)).build();
		// one more transaction
		account.getAccountTransactionsInterval().addAccountTransaction(accountTransaction);

		Assertions.assertThat(account.getAccountTransactionsInterval().getAccountTransactions()).hasSize(3);
	}
	private Date startDate() {
		LocalDateTime localDateTime = LocalDateTime.of(2024, 2, 3, 0, 0);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	private Date inBetweenDate() {
		LocalDateTime localDateTime = LocalDateTime.of(2024, 3, 4, 0, 0);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	private Date endDate() {
		LocalDateTime localDateTime = LocalDateTime.of(2024, 3, 5, 0, 0);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

}