package com.exalt.application.domain.usecaseimpl;

import com.exalt.application.domain.model.BankAccount;
import com.exalt.application.port.driven.LoadBankAccountPort;
import com.exalt.application.port.driven.UpdateAccountStatePort;
import com.exalt.common.exceptions.AuthorizedOverdraftAccountBalanceExeeded;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

class WithdrawUseCaseImplTest {
	private final LoadBankAccountPort loadBankAccountPort= Mockito.mock(LoadBankAccountPort.class);
	private final UpdateAccountStatePort updateAccountStatePort = Mockito.mock(UpdateAccountStatePort.class);

	private final WithdrawUseCaseImpl withdrawUseCaseImpl =
			new WithdrawUseCaseImpl(loadBankAccountPort, updateAccountStatePort);

	@Test
	void transactionSucceeds() {
		BankAccount account = givenAccount();
		givenWithdrawalWillSucceed(account);
		withdrawUseCaseImpl.withdrawMoney(account.getId(),BigDecimal.valueOf(500L));
		thenAccountHasBeenUpdated(account.getId());
	}
	@Test
	void transactionFails() {
		BankAccount account = givenAccount();
		givenWithdrawalWillFail(account);
		Exception exception = Assertions.assertThrows(AuthorizedOverdraftAccountBalanceExeeded.class,
				() -> withdrawUseCaseImpl.withdrawMoney(account.getId(),BigDecimal.valueOf(500L)));
		Assertions.assertEquals(exception.getMessage(),"Operation denied because withdraw amount exceed authorized overdraft for the account.");
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

	private void givenDepositWillSucceed(BankAccount bankAccount) {
		given(bankAccount.depositMoney(any(BankAccount.class), any(BigDecimal.class)))
				.willReturn(true);
	}

	private void givenWithdrawalWillFail(BankAccount bankAccount) {
		given(bankAccount.withdrawMoney(any(BankAccount.class), any(BigDecimal.class)))
				.willReturn(false);
	}

	private void givenWithdrawalWillSucceed(BankAccount bankAccount) {
		given(bankAccount.withdrawMoney(any(BankAccount.class), any(BigDecimal.class)))
				.willReturn(true);
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
