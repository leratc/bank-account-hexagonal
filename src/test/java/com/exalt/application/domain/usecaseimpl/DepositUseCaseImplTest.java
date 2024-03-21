package com.exalt.application.domain.usecaseimpl;

import com.exalt.application.domain.model.BankAccount;
import com.exalt.application.port.driven.LoadBankAccountPort;
import com.exalt.application.port.driven.UpdateAccountStatePort;
import com.exalt.common.exceptions.AuthorizedMaximumBookletBalanceExeeded;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

class DepositUseCaseImplTest {
	private LoadBankAccountPort loadBankAccountPort= Mockito.mock(LoadBankAccountPort.class);
	private final UpdateAccountStatePort updateAccountStatePort = Mockito.mock(UpdateAccountStatePort.class);

	private final DepositUseCaseImpl depositUseCaseImpl =
			new DepositUseCaseImpl(loadBankAccountPort, updateAccountStatePort);

	@Test
	void transactionSucceeds() {
		BankAccount account = givenAccount();
		givenDepositMoneyWillSucceed(account);
		depositUseCaseImpl.depositMoney(account.getId(),BigDecimal.valueOf(500L));
		thenAccountHasBeenUpdated(account.getId());
	}
	@Test
	void transactionFails() {
		BankAccount account = givenAccount();
		givenDepositMoneyWillFail(account);
		Exception exception = Assertions.assertThrows(AuthorizedMaximumBookletBalanceExeeded.class,
				() -> depositUseCaseImpl.depositMoney(account.getId(),BigDecimal.valueOf(500L)));
		Assertions.assertEquals(exception.getMessage(),"Operation denied because deposit limit has been reached for the booklet account");
	}

	private void thenAccountHasBeenUpdated(Long... accountIds){
		ArgumentCaptor<BankAccount> accountCaptor = ArgumentCaptor.forClass(BankAccount.class);
		then(updateAccountStatePort).should(times(accountIds.length))
				.updateActivities(accountCaptor.capture());

		List<Long> updatedAccountIds = accountCaptor.getAllValues()
				.stream()
				.map(BankAccount::getId)
				.collect(Collectors.toList());

		for(Long accountId : accountIds){
			assertThat(updatedAccountIds).contains(accountId);
		}
	}

	private void givenDepositMoneyWillSucceed(BankAccount bankAccount) {
		given(bankAccount.depositMoney(any(BankAccount.class), any(BigDecimal.class)))
				.willReturn(true);
	}

	private void givenDepositMoneyWillFail(BankAccount bankAccount) {
		given(bankAccount.depositMoney(any(BankAccount.class), any(BigDecimal.class)))
				.willReturn(false);
	}



	private BankAccount givenAccount(){
		return givenAnAccountWithId(41L);
	}

	private BankAccount givenAnAccountWithId(Long id) {
		BankAccount bankAccount = Mockito.mock(BankAccount.class);
		given(bankAccount.getId())
				.willReturn(id);
		given(loadBankAccountPort.loadAccount(eq(bankAccount.getId())))
				.willReturn(bankAccount);
		return bankAccount;
	}

}
