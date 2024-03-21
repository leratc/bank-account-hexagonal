package com.exalt.application.domain.configuration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Configuration properties for money transfer use cases.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankBookletProperties {

  private BigDecimal maximumBankBookletProperties = BigDecimal.valueOf(22950L);

}
