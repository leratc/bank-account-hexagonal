package com.exalt.adapters.driven.persistence.jpaentity;

import com.exalt.common.commontype.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_transaction")
public class AccountTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column
    private BigDecimal amount;

    @Column
    private Date transactionDate;

    @Column
    private Long accountId;

}
