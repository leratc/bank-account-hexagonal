package com.exalt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@Data
@ConfigurationProperties(prefix = "bank")
public class BankConfigurationProperties {

  private BigDecimal depositBookletThreshold = BigDecimal.valueOf(Long.MAX_VALUE);

}
