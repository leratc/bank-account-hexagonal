package com.exalt.adapters.driven.persistence.jpaentity;

import com.exalt.common.commontype.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bank_account")
public class BankAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private BigDecimal overdraftAmountAuthorization;

    @Column @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @OneToMany(
            mappedBy = "accountId",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Builder.Default
    private List<AccountTransactionEntity> transactions = new ArrayList<>();
}
