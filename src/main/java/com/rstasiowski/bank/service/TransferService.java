package com.rstasiowski.bank.service;

import com.rstasiowski.bank.dto.TransferDto;
import com.rstasiowski.bank.enums.TransferType;
import com.rstasiowski.bank.interfaces.TransferStrategy;
import com.rstasiowski.bank.model.BankAccount;
import com.rstasiowski.bank.model.Money;
import com.rstasiowski.bank.factory.MoneyFactory;
import com.rstasiowski.bank.model.Transfer;
import com.rstasiowski.bank.repository.BankAccountRepository;
import com.rstasiowski.bank.repository.TransferRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class TransferService {

    private final Map<String, TransferStrategy> strategyMap;

    public Transfer performTransfer(TransferDto transferDto) {
        TransferType type = transferDto.getType();
        TransferStrategy strategy = strategyMap.get(type.getBeanName());
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported transfer type: " + type);
        }
        return strategy.processTransfer(transferDto);
    }

}
