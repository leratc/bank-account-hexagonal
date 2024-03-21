package com.exalt.application.domain.model;

import com.exalt.common.commontype.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransaction {
    @Getter
    private Long id;

    @Getter @NonNull
    private Long accountId;

    @Getter @NonNull
    private TransactionType transactionType;

    @Getter @NonNull
    private LocalDateTime transactionDate;

    @Getter @NonNull
    private BigDecimal amount;
}
