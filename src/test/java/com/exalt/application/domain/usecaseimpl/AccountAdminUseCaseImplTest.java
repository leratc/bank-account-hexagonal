package com.exalt.application.domain.usecaseimpl;

import com.exalt.application.domain.model.BankAccount;
import com.exalt.application.port.driven.CreateBankAccountPort;
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

class AccountAdminUseCaseImplTest {
	private LoadBankAccountPort loadBankAccountPort= Mockito.mock(LoadBankAccountPort.class);
	private final CreateBankAccountPort createBankAccountPort = Mockito.mock(CreateBankAccountPort.class);


	@Test
	void transactionSucceeds() {
		BankAccount account = givenAnAccountWithoutId();
		BankAccount accountResult = createBankAccountPort.createAccount(account);

	}

	private BankAccount givenAnAccountWithoutId() {
		BankAccount bankAccount = Mockito.mock(BankAccount.class);
		return bankAccount;
	}

}
