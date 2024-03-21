package com.exalt.adapters.driven.persistence.jparepository;

import com.exalt.adapters.driven.persistence.jpaentity.AccountTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccountTransactionRepository extends JpaRepository<AccountTransactionEntity, Long> {

	@Query("""
			select a from AccountTransactionEntity a 
			where a.accountId = :accountId 
			and a.transactionDate >= :since
			""")
	List<AccountTransactionEntity> findByAccountIdSince(
			@Param("accountId") long accountId,
			@Param("since") LocalDateTime since);

	@Query("""
			select sum(a.amount) from AccountTransactionEntity a
			where a.accountId = :accountId
			and a.transactionType = 'DEPOSIT'
			and a.transactionDate < :until
			""")
	Optional<BigDecimal> getDepositBalanceUntil(
			@Param("accountId") long accountId,
			@Param("until") LocalDateTime until);

	@Query("""
			select sum(a.amount) from AccountTransactionEntity a
			where a.accountId = :accountId
			and a.transactionType = 'WITHDRAWAL'
			and a.transactionDate < :until
			""")
	Optional<BigDecimal> getWithdrawalBalanceUntil(
			@Param("accountId") long accountId,
			@Param("until") LocalDateTime until);

}
