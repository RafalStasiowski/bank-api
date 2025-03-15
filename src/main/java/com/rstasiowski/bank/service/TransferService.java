package com.rstasiowski.bank.service;

import com.rstasiowski.bank.dto.TransferDto;
import com.rstasiowski.bank.model.BankAccount;
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

    @Transactional
    public Transfer transfer(TransferDto transferDto) {
        BankAccount accountFrom = findAccount(transferDto.getSenderId());
        BankAccount accountTo = findAccount(transferDto.getReceiverId());
        validateTransferBalance(accountFrom.getBalance(), transferDto.getAmount());
        changeBalances(accountFrom, accountTo, transferDto.getAmount());
        return createTransfer(accountFrom, accountTo, transferDto.getAmount(), transferDto.getDescription());
    }

    private void validateTransferBalance(BigDecimal balanceFrom, BigDecimal transferAmount) {
        if (balanceFrom.compareTo(transferAmount) < 0)
            throw new IllegalArgumentException("Not enough funds to transfer");
    }

    private BankAccount findAccount(Long userId) {
        return bankAccountRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Account does not exist"));
    }

    @Transactional
    private void changeBalances(BankAccount accountFrom, BankAccount accountTo, BigDecimal amount) {
        accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
        bankAccountRepository.save(accountFrom);
        accountTo.setBalance(accountTo.getBalance().add(amount));
        bankAccountRepository.save(accountTo);
    }

    private Transfer createTransfer(BankAccount accountFrom, BankAccount accountTo, BigDecimal amount, String description) {
        Transfer transfer = Transfer.builder()
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .amount(amount)
                .description(description)
                .build();
        return transferRepository.save(transfer);
    }



}
