package com.rstasiowski.bank.dto;

import com.rstasiowski.bank.enums.BankAccountType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankAccountRegisterDto {
    String userEmail;
    BankAccountType type;
}
