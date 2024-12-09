package com.rstasiowski.bank.controller;

import com.rstasiowski.bank.dto.BankAccountRegisterDto;
import com.rstasiowski.bank.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/api/bankAccounts")
public class BankAccountController {
    private BankAccountService bankAccountService;

    @PostMapping("/create")
    public ResponseEntity<?> createBankAccount(@RequestBody BankAccountRegisterDto bankAccountRegisterDto) {
        bankAccountService.createBankAccount(bankAccountRegisterDto);
        return ResponseEntity.ok("Account created successfully");
    }

}
