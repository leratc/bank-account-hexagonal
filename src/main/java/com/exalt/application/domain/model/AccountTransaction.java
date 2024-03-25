package com.exalt.application.domain.model;

import com.exalt.common.commontype.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

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
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date transactionDate;

    @Getter @NonNull
    private BigDecimal amount;
}
