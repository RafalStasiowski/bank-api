package com.rstasiowski.bank.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BankAccountDto {
    private String accountNumber;
    private String accountType;
}