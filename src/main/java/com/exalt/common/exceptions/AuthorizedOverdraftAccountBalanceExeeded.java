package com.exalt.common.exceptions;

import com.exalt.common.commontype.ErrorCode;

import java.util.List;
public class AuthorizedOverdraftAccountBalanceExeeded extends AbstractEntityException {
    public AuthorizedOverdraftAccountBalanceExeeded(String message ,List<String> errors){
        super(message, ErrorCode.AUTHORIZED_OVERDRAFT_ACCOUNT_BALANCE_EXCEEDED, errors);
    }

    public AuthorizedOverdraftAccountBalanceExeeded(String message) {
        super(message, ErrorCode.AUTHORIZED_OVERDRAFT_ACCOUNT_BALANCE_EXCEEDED);
    }
}
