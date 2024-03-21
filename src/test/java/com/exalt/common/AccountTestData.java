package com.exalt.common;

import com.exalt.application.domain.model.AccountTransaction;
import com.exalt.application.domain.model.AccountTransactions;
import com.exalt.application.domain.model.BankAccount;
import com.exalt.common.commontype.AccountType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountTestData {

	public static BankAccount.BankAccountBuilder defaultCurrentAccount() {
		List<AccountTransaction> accountTransactions=new ArrayList<>();
		accountTransactions.add(AccountTransactionTestData.defaultDepositTransaction().build());
		accountTransactions.add(AccountTransactionTestData.defaultWithdrawalTransaction().build());
		return BankAccount.builder().id(42L).firstName("Jean").lastName("Dujardin").accountType(AccountType.CURRENT)
				.baselineBalance(BigDecimal.valueOf(999L))
				.overdraftAmountAuthorization(BigDecimal.valueOf(1000L))
				.accountTransactions(new AccountTransactions(accountTransactions));
	}

	public static BankAccount.BankAccountBuilder defaultBookletAccount() {
		List<AccountTransaction> accountTransactions=new ArrayList<>();
		accountTransactions.add(AccountTransactionTestData.defaultDepositTransaction().build());
		accountTransactions.add(AccountTransactionTestData.defaultWithdrawalTransaction().build());
		return BankAccount.builder().id(42L).firstName("Albert").lastName("Dupontel").accountType(AccountType.BOOKLET)
				.baselineBalance(BigDecimal.valueOf(999L))
				.overdraftAmountAuthorization(BigDecimal.ZERO)
				.accountTransactions(new AccountTransactions(accountTransactions));

	}

}
