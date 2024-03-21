package com.exalt.adapters.driven.persistence.mapper;

import com.exalt.adapters.driven.persistence.jpaentity.BankAccountEntity;
import com.exalt.application.domain.model.BankAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccountEntity toEntity(BankAccount bankAccount);
    BankAccount toDomain(BankAccountEntity bankAccountEntity);
}
