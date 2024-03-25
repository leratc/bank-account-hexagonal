package com.exalt.common;

import com.exalt.application.domain.model.AccountTransaction;
import com.exalt.common.commontype.TransactionType;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class AccountTransactionTestData {

	public static AccountTransaction.AccountTransactionBuilder defaultDepositTransaction(){
		return AccountTransaction.builder()
				.id(1L)
				.accountId(42L)
				.transactionType(TransactionType.DEPOSIT)
				.transactionDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
				.amount(BigDecimal.valueOf(999L));
	}
	public static AccountTransaction.AccountTransactionBuilder defaultWithdrawalTransaction(){
		return AccountTransaction.builder()
				.id(1L)
				.accountId(42L)
				.transactionType(TransactionType.WITHDRAWAL)
				.transactionDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
				.amount(BigDecimal.valueOf(500L));
	}
}
