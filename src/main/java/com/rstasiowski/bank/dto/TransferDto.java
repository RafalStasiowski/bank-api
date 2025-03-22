package com.rstasiowski.bank.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferDto {
    private Long senderId;
    private Long receiverId;
    private BigDecimal amount;
    private String currencyCode;
    private String description;
}
