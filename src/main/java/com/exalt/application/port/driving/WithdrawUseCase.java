package com.exalt.application.port.driving;

import com.exalt.common.exceptions.AuthorizedOverdraftAccountBalanceExeeded;

import java.math.BigDecimal;

public interface WithdrawUseCase {
    void withdrawMoney(Long id, BigDecimal withdraw);
}