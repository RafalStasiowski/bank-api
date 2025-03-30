package com.rstasiowski.bank.impl;

import com.rstasiowski.bank.dto.TransferDto;
import com.rstasiowski.bank.factory.MoneyFactory;
import com.rstasiowski.bank.interfaces.TransferStrategy;
import com.rstasiowski.bank.model.BankAccount;
import com.rstasiowski.bank.model.Money;
import com.rstasiowski.bank.model.Transfer;
import com.rstasiowski.bank.repository.BankAccountRepository;
import com.rstasiowski.bank.repository.TransferRepository;
import com.rstasiowski.bank.service.TransferValidationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service("TRANSFER_DOMESTIC")
@AllArgsConstructor
public class DomesticTransferStrategy implements TransferStrategy {

    private TransferValidationService transferValidationService;
    private BankAccountRepository bankAccountRepository;
    private TransferRepository transferRepository;
    private MoneyFactory moneyFactory;

    @Override
    public Transfer processTransfer(TransferDto transferDto) {
        BankAccount accountFrom = findAccount(transferDto.getSenderId());
        BankAccount accountTo = findAccount(transferDto.getReceiverId());
        Money transferBalance = moneyFactory.create(transferDto.getAmount(), transferDto.getCurrencyCode());
        transferValidationService.validate(accountFrom, transferBalance);
        changeBalances(accountFrom, accountTo, transferBalance);
        return createTransfer(accountFrom, accountTo, transferBalance, transferDto.getDescription());
    }

    private BankAccount findAccount(Long userId) {
        return bankAccountRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Account does not exist"));
    }

    @Transactional
    private void changeBalances(BankAccount accountFrom, BankAccount accountTo, Money amount) {
        accountFrom.withdraw(amount);
        bankAccountRepository.save(accountFrom);
        accountTo.deposit(amount);
        bankAccountRepository.save(accountTo);
    }

    private Transfer createTransfer(BankAccount accountFrom, BankAccount accountTo, Money amount, String description) {
        Transfer transfer = Transfer.builder()
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .amount(amount)
                .description(description)
                .build();
        return transferRepository.save(transfer);
    }
}
