package com.rstasiowski.bank.controller;

import com.rstasiowski.bank.dto.BankAccountRegisterDto;
import com.rstasiowski.bank.dto.BankAccountRequestDto;
import com.rstasiowski.bank.model.BankAccount;
import com.rstasiowski.bank.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user/bankAccounts")
public class BankAccountController {
    private BankAccountService bankAccountService;

    @PostMapping("/create")
    public ResponseEntity<?> createBankAccount(@RequestBody BankAccountRegisterDto bankAccountRegisterDto) {
        bankAccountService.createBankAccount(bankAccountRegisterDto);
        return ResponseEntity.ok("Account created successfully");
    }

    @PostMapping("/showAccounts")
    public ResponseEntity<List<String>> findAllBankAccountsNumberForUser(@RequestBody BankAccountRequestDto accountRequest, Pageable pageable) {
        List<String> bankAccounts = bankAccountService.findAllBankAccountsByEmail(accountRequest.getEmail(), pageable)
                .stream()
                .map(BankAccount::getAccountNumber)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bankAccounts);
    }

}
