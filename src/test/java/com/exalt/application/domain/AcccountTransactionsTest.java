package com.exalt.application.domain;
import com.exalt.application.domain.model.AccountTransaction;
import com.exalt.application.domain.model.AccountTransactions;
import com.exalt.common.AccountTransactionTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class AcccountTransactionsTest {

	@Test
	void calculatesStartTimestamp() {
		List<AccountTransaction> transactions = Arrays.asList(
				AccountTransactionTestData.defaultDepositTransaction().transactionDate(startDate()).build(),
				AccountTransactionTestData.defaultDepositTransaction().transactionDate(inBetweenDate()).build(),
				AccountTransactionTestData.defaultDepositTransaction().transactionDate(endDate()).build());
		AccountTransactions accountTransactions = new AccountTransactions(transactions);
		Assertions.assertThat(accountTransactions.getStartTimestamp()).isEqualTo(startDate());
	}

	@Test
	void calculatesEndTimestamp() {
		List<AccountTransaction> transactions = Arrays.asList(
						AccountTransactionTestData.defaultDepositTransaction().transactionDate(startDate()).build(),
						AccountTransactionTestData.defaultDepositTransaction().transactionDate(inBetweenDate()).build(),
						AccountTransactionTestData.defaultDepositTransaction().transactionDate(endDate()).build());
		AccountTransactions accountTransactions = new AccountTransactions(transactions);
		Assertions.assertThat(accountTransactions.getEndTimestamp()).isEqualTo(endDate());
	}

	@Test
	void calculatesBalance() {
		Long accountId=1L;
		// the other way of initialize
		AccountTransactions accountTransactions = new AccountTransactions(
					AccountTransactionTestData.defaultWithdrawalTransaction().accountId(accountId)
							.amount(BigDecimal.valueOf(999L)).build(),
					AccountTransactionTestData.defaultWithdrawalTransaction().accountId(accountId)
							.amount(BigDecimal.valueOf(1L)).build(),
					AccountTransactionTestData.defaultDepositTransaction().accountId(accountId)
							.amount(BigDecimal.valueOf(500L)).build()
				);
		Assertions.assertThat(accountTransactions.calculateBalance(accountId)).isEqualTo(BigDecimal.valueOf(-500));
	}

	private LocalDateTime startDate() {
		return LocalDateTime.of(2024, 2, 3, 0, 0);
	}

	private LocalDateTime inBetweenDate() {
		return LocalDateTime.of(2024, 3, 4, 0, 0);
	}

	private LocalDateTime endDate() {
		return LocalDateTime.of(2024, 3, 5, 0, 0);
	}

}