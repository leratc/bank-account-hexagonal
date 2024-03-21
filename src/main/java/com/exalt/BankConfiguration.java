package com.exalt;

import com.exalt.application.domain.configuration.BankBookletProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BankConfigurationProperties.class)
public class BankConfiguration {

  /**
   * Adds a use-case-specific {@link BankBookletProperties} object to the application context. The properties
   * are read from the Spring-Boot-specific {@link BankConfigurationProperties} object.
   */
  @Bean
  public BankBookletProperties bankBookletProperties(BankConfigurationProperties bankConfigurationProperties){
    return new BankBookletProperties(bankConfigurationProperties.getDepositBookletThreshold());
  }

}
