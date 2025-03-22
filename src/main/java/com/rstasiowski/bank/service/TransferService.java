package com.rstasiowski.bank.service;

import com.rstasiowski.bank.dto.TransferDto;
import com.rstasiowski.bank.model.BankAccount;
import com.rstasiowski.bank.model.Money;
import com.rstasiowski.bank.model.MoneyFactory;
import com.rstasiowski.bank.model.Transfer;
import com.rstasiowski.bank.repository.BankAccountRepository;
import com.rstasiowski.bank.repository.TransferRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class TransferService {
    private BankAccountRepository bankAccountRepository;
    private TransferRepository transferRepository;
    private MoneyFactory moneyFactory;
    private TransferValidationService transferValidationService;

    @Transactional
    public Transfer transfer(TransferDto transferDto) {
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
        accountFrom.setBalance(accountFrom.getBalance().substract(amount));
        bankAccountRepository.save(accountFrom);
        accountTo.setBalance(accountTo.getBalance().add(amount));
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
