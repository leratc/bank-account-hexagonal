package com.exalt.adapter.driven.persistence;
import com.exalt.BankConfigurationProperties;
import com.exalt.adapters.driven.persistence.BankAccountRepositoryComponent;
import com.exalt.adapters.driven.persistence.jpaentity.AccountTransactionEntity;
import com.exalt.adapters.driven.persistence.jparepository.AccountTransactionRepository;
import com.exalt.adapters.driven.persistence.jparepository.BankAccountRepository;
import com.exalt.adapters.driven.persistence.mapper.AccountTransactionMapperImpl;
import com.exalt.adapters.driven.persistence.mapper.BankAccountMapperImpl;
import com.exalt.application.domain.model.AccountTransactionsInterval;
import com.exalt.application.domain.model.BankAccount;
import com.exalt.common.AccountTestData;
import com.exalt.common.AccountTransactionTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
// mapstruct based on interface generate on build time an implementation named xxxImpl that are needed for test purpose.
@Import({BankAccountRepositoryComponent.class, BankAccountMapperImpl.class, AccountTransactionMapperImpl.class, BankConfigurationProperties.class })
class AccountStatementTest {

	@Autowired
	private BankAccountRepositoryComponent adapterUnderTest;

	@Autowired
	private AccountTransactionRepository accountTransactionRepository;

	@Autowired
	private BankAccountRepository bankAccountRepository;

    @Test
	@Sql("AccountStatementTest.sql")
	void loadsAccount() {
		BankAccount bankAccount = adapterUnderTest.loadAccount(1L);
		assertThat(bankAccount.getAccountTransactionsInterval().getAccountTransactions()).hasSize(5);
		assertThat(bankAccount.calculateBalance()).isEqualTo(new BigDecimal("1700"));
		// Only the first account transaction of a 100.00 deposit is older than one month.
		assertThat(bankAccount.getBaselineBalance()).isEqualTo(new BigDecimal("100"));
	}
	@Test
	@Sql("AccountStatementTest.sql")
	void loadsAccountBookLet() {
		BankAccount bankAccount = adapterUnderTest.loadAccount(2L);
		// test if BankConfigurationProperties provide the value of the depositBookletThreshold property (22950).
		assertThat(bankAccount.getMaximumDepositAuthorization()).isEqualTo(new BigDecimal("22950"));
		assertThat(bankAccount.getAccountTransactionsInterval().getAccountTransactions()).hasSize(5);
		assertThat(bankAccount.calculateBalance()).isEqualTo(new BigDecimal("2200"));
		// Only the first account transaction of a 100.00 deposit is older than one month.
		assertThat(bankAccount.getBaselineBalance()).isEqualTo(new BigDecimal("800"));
	}

	@Test
	void updatesTransactions() {
		BankAccount account = AccountTestData.defaultCurrentAccount()
				.baselineBalance(BigDecimal.valueOf(555L))
				.accountTransactionsInterval(new AccountTransactionsInterval(
						AccountTransactionTestData.defaultDepositTransaction()
								.id(null)
								.amount(BigDecimal.valueOf(1)).build()))
				.build();

		adapterUnderTest.updateActivities(account);

		assertThat(accountTransactionRepository.count()).isEqualTo(1);

		AccountTransactionEntity savedAccountTransaction = accountTransactionRepository.findAll().get(0);
		assertThat(savedAccountTransaction.getAmount()).isEqualTo(new BigDecimal("1"));
	}
}