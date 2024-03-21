package com.exalt.application.port.driven;

import com.exalt.application.domain.model.BankAccount;

public interface UpdateAccountStatePort {
    void updateActivities(BankAccount account);
}