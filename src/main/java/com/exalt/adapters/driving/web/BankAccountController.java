package com.exalt.adapters.driving.web;

import com.exalt.application.domain.model.BankAccount;
import com.exalt.application.port.driving.AccountAdminUseCase;
import com.exalt.application.port.driving.DepositUseCase;
import com.exalt.application.port.driving.LoadBankAccountStatementUseCase;
import com.exalt.application.port.driving.WithdrawUseCase;
import com.exalt.common.customannotation.WebAdapter;
import com.exalt.common.exceptions.AuthorizedMaximumBookletBalanceExeeded;
import com.exalt.common.exceptions.AuthorizedOverdraftAccountBalanceExeeded;
import com.exalt.common.exceptions.IllegalAmountException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@WebAdapter
@RestController
@RequestMapping("/account")
public class BankAccountController {

    private final DepositUseCase depositUseCase;
    private final WithdrawUseCase withdrawUseCase;

    private final AccountAdminUseCase accountAdminUseCase;

    private final LoadBankAccountStatementUseCase loadBankAccountStatementUseCase;

    public BankAccountController(DepositUseCase depositUseCase, WithdrawUseCase withdrawUseCase, AccountAdminUseCase accountAdminUseCase, LoadBankAccountStatementUseCase loadBankAccountStatementUseCase) {
        this.depositUseCase = depositUseCase;
        this.withdrawUseCase = withdrawUseCase;
        this.accountAdminUseCase = accountAdminUseCase;
        this.loadBankAccountStatementUseCase = loadBankAccountStatementUseCase;
    }

    @PostMapping(value = "/create-account")
    @Transactional
    ResponseEntity<BankAccount> createAccount(@RequestBody BankAccount bankAccount) {
        return new ResponseEntity<>(accountAdminUseCase.createBankAccount(bankAccount), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/depositMoney/{amount}")
    @Transactional
    ResponseEntity<Long>  depositMoney(@PathVariable final Long id, @PathVariable final BigDecimal amount) {

        try {
            depositUseCase.depositMoney(id, amount);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalAmountException e) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        } catch (AuthorizedMaximumBookletBalanceExeeded e) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/{id}/withdrawMoney/{amount}")
    @Transactional
    ResponseEntity<Long> withdrawMoney(@PathVariable final Long id, @PathVariable final BigDecimal amount) {
        try {
            withdrawUseCase.withdrawMoney(id, amount);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalAmountException e) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        catch (AuthorizedOverdraftAccountBalanceExeeded e) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "{id}/account-statement/")
    @ResponseBody BankAccount get(@PathVariable final Long id) {
        return loadBankAccountStatementUseCase.loadBankAccountStatement(id);
    }

}
