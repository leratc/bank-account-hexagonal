package com.exalt.application.port.driving;

import java.math.BigDecimal;

public interface WithdrawUseCase {
    void withdrawMoney(Long id, BigDecimal withdraw);
}