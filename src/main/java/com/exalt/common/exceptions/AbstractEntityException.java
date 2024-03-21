package com.exalt.common.exceptions;

import com.exalt.common.commontype.ErrorCode;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class AbstractEntityException extends RuntimeException {
    @Getter
    private ErrorCode errorCode ;
    @Getter
    private List<String> errors;


    public AbstractEntityException(String message , ErrorCode errorCode , List<String> errors){
        super(message);
        this.errorCode = errorCode;
        this.errors = errors;
    }

    public AbstractEntityException(String message , ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
