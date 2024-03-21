package com.exalt.adapters.driven.persistence.jparepository;

import com.exalt.adapters.driven.persistence.jpaentity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {
}
