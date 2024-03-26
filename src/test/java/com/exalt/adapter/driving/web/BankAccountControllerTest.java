package com.exalt.adapter.driving.web;

import com.exalt.adapters.driving.web.BankAccountController;
import com.exalt.application.domain.model.BankAccount;
import com.exalt.application.port.driven.LoadBankAccountPort;
import com.exalt.application.port.driving.AccountAdminUseCase;
import com.exalt.application.port.driving.DepositUseCase;
import com.exalt.application.port.driving.LoadBankAccountStatementUseCase;
import com.exalt.application.port.driving.WithdrawUseCase;
import com.exalt.common.commontype.AccountType;
import com.exalt.common.exceptions.AuthorizedOverdraftAccountBalanceExeeded;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BankAccountController.class)
class BankAccountControllerTest {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DepositUseCase depositUseCase;
	@MockBean
	private WithdrawUseCase withdrawUseCase;
	@MockBean
	private AccountAdminUseCase accountAdminUseCase;
	@MockBean
	private LoadBankAccountStatementUseCase loadBankAccountStatementUseCase;
	@MockBean
	private LoadBankAccountPort loadBankAccountPort;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testGetAccount() throws Exception {
		BankAccount account = new BankAccount(1L, "Jean", "dujardin", new BigDecimal(1000), new BigDecimal(999999), AccountType.CURRENT, new BigDecimal(100));
		when(loadBankAccountStatementUseCase.loadBankAccountStatement(1L)).thenReturn(account);
		mockMvc.perform(get("/account/1"))
				.andExpect(status().isOk());
	}

	@Test
	void testDepositMoney() throws Exception {
		BankAccount account = new BankAccount(1L, "Jean", "dujardin", new BigDecimal(1000), new BigDecimal(999999), AccountType.CURRENT, new BigDecimal(100));
		when(loadBankAccountPort.loadAccount(1L)).thenReturn(account);
		mockMvc.perform(post("/account/{id}/depositMoney/{amount}",
						1L, 500)
						.header("Content-Type", "application/json"))
				.andExpect(status().isOk());

	}

	@Test
	void testWithdrawMoney() throws Exception {
		BankAccount account = new BankAccount(1L, "Jean", "dujardin", new BigDecimal(1000), new BigDecimal(999999), AccountType.CURRENT, new BigDecimal(100));
		when(loadBankAccountPort.loadAccount(1L)).thenReturn(account);
		mockMvc.perform(post("/account/{id}/withdrawMoney/{amount}",
						1L, 500)
						.header("Content-Type", "application/json"))
				.andExpect(status().isOk());

	}
// pb de parsing json du requestBody
	@Test
	void testCreateAccount() throws Exception {
		BankAccount account = new BankAccount(1L, "Jean", "dujardin", new BigDecimal(1000), new BigDecimal(999999), AccountType.CURRENT, new BigDecimal(100));
		String requestJson = asJsonString(account);
		when(accountAdminUseCase.createBankAccount(account)).thenReturn(account);

		//accountTransactionsInterval
		mockMvc.perform(post("/account/create-account",
						1L, 500)
						.header("Content-Type", APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isCreated());

	}

	public static String asJsonString(final Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
			ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
