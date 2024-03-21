package com.exalt.application.port.driving;

import com.exalt.common.exceptions.AuthorizedMaximumBookletBalanceExeeded;

import java.math.BigDecimal;

public interface DepositUseCase {
    void depositMoney(Long id, BigDecimal amount);
}