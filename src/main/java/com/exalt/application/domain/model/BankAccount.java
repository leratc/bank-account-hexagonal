package com.exalt.application.domain.model;

import com.exalt.common.commontype.AccountType;
import com.exalt.common.commontype.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Builder
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    @Getter @NonNull private Long id;
    @Getter @NonNull private String firstName;
    @Getter @NonNull private String lastName;
    @Builder.Default
    @Getter @NonNull private BigDecimal overdraftAmountAuthorization =BigDecimal.ZERO;
    @Builder.Default
    @Getter @NonNull private BigDecimal maximumDepositAuthorization = BigDecimal.valueOf(Integer.MAX_VALUE);
    @Getter @NonNull private AccountType accountType;
    /**
     * The baseline balance of the account. This was the balance of the account before the first
     * activity in the activityWindow.
     */
    @Getter @Setter private BigDecimal baselineBalance;

    /**
     * The period of latest transactions on this account.
     */
    @Getter @Setter private AccountTransactionsInterval accountTransactionsInterval;


    public BankAccount(Long id, String firstName, String lastName, BigDecimal overdraftAmountAuthorization,BigDecimal maximumDepositAuthorization, AccountType accountType, BigDecimal baselineBalance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.maximumDepositAuthorization = maximumDepositAuthorization;
        this.overdraftAmountAuthorization = overdraftAmountAuthorization;
        this.accountType = accountType;
        this.baselineBalance = baselineBalance;
    }


    /**
     * Calculates the total balance of the account by adding the transactions that occurs after the baseline balance starting point
     * @return
     */
    public BigDecimal calculateBalance() {
        return this.baselineBalance.add(this.accountTransactionsInterval.calculateBalance(this.id));
    }
    private boolean isWithdrawalAuthorized(BankAccount account, BigDecimal withdraw) {
        BigDecimal resultingOperation = this.calculateBalance().add(this.overdraftAmountAuthorization).subtract(withdraw);
        if (resultingOperation.compareTo(BigDecimal.ZERO) >= 0) {
            return true;
        }
        return false;
    }

    public boolean withdrawMoney(BankAccount account, BigDecimal withdraw) {
        if (!isWithdrawalAuthorized(account, withdraw)) {
            return false;
        }

        AccountTransaction withdrawTransaction= new AccountTransaction(
                null,
                account.getId(),
                TransactionType.WITHDRAWAL,
                Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
                withdraw);
        this.accountTransactionsInterval.addAccountTransaction(withdrawTransaction);

        return true;
    }
    private boolean isDepositAuthorized(BankAccount account, BigDecimal deposit) {
        if (this.calculateBalance().add(deposit).compareTo(this.maximumDepositAuthorization) > 0) {
            return false;
        }
        return true;
    }
    public boolean depositMoney(BankAccount account, BigDecimal deposit) {
        if (AccountType.BOOKLET.equals(account.accountType) && !isDepositAuthorized(account, deposit)) {
            return false;
        }
        AccountTransaction depositTransaction= new AccountTransaction(
                null,
                account.getId(),
                TransactionType.DEPOSIT,
                Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
                deposit);
        this.accountTransactionsInterval.addAccountTransaction(depositTransaction);
        return true;
    }
}
