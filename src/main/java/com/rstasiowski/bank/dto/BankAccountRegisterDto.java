package com.rstasiowski.bank.dto;

import com.rstasiowski.bank.enums.BankAccountType;
import lombok.Data;

@Data
public class BankAccountRegisterDto {
    String userEmail;
    BankAccountType type;
}
