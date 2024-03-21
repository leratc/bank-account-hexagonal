package com.exalt.adapters.driven.persistence.mapper;

import com.exalt.adapters.driven.persistence.jpaentity.AccountTransactionEntity;
import com.exalt.application.domain.model.AccountTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountTransactionMapper {
    AccountTransactionEntity toEntity(AccountTransaction accountTransaction);
    AccountTransaction toDomain(AccountTransactionEntity accountTransactionEntity);
}
