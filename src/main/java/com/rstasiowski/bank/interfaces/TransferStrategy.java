package com.rstasiowski.bank.interfaces;

import com.rstasiowski.bank.dto.TransferDto;
import com.rstasiowski.bank.model.Transfer;

public interface TransferStrategy {
    Transfer processTransfer(TransferDto transferDto);
}
