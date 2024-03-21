package com.exalt.common.commontype;

public enum ErrorCode {
    AUTHORIZED_OVERDRAFT_ACCOUNT_BALANCE_EXCEEDED(1001),
    AUTHORIZED_MAXIMUM_BOOKLET_BALANCE_EXCEEDED(1002);

    private int id;

    ErrorCode(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
}
