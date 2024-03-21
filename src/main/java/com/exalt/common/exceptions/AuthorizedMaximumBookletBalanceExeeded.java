package com.exalt.common.exceptions;

import com.exalt.common.commontype.ErrorCode;

import java.util.List;

public class AuthorizedMaximumBookletBalanceExeeded extends AbstractEntityException {
    public AuthorizedMaximumBookletBalanceExeeded(String message , List<String> errors){
        super(message, ErrorCode.AUTHORIZED_MAXIMUM_BOOKLET_BALANCE_EXCEEDED, errors);
    }

    public AuthorizedMaximumBookletBalanceExeeded(String message) {
        super(message, ErrorCode.AUTHORIZED_MAXIMUM_BOOKLET_BALANCE_EXCEEDED);
    }
}
