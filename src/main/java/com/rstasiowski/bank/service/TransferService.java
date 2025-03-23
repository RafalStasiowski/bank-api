package com.rstasiowski.bank.service;

import com.rstasiowski.bank.dto.TransferDto;
import com.rstasiowski.bank.interfaces.BankAccount;
import com.rstasiowski.bank.interfaces.Transfer;
import com.rstasiowski.bank.model.Money;
import com.rstasiowski.bank.factory.MoneyFactory;
import com.rstasiowski.bank.model.StandardTransfer;
import com.rstasiowski.bank.repository.StandardBankAccountRepository;
import com.rstasiowski.bank.repository.StandardTransferRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransferService {
    private StandardBankAccountRepository standardBankAccountRepository;
    private BankAccountService bankAccountService;
    private StandardTransferRepository standardTransferRepository;
    private MoneyFactory moneyFactory;
    private TransferValidationService transferValidationService;

    @Transactional
    public Transfer transfer(TransferDto transferDto) {
        BankAccount accountFrom = findAccount(transferDto.getSenderId());
        BankAccount accountTo = findAccount(transferDto.getReceiverId());
        Money transferBalance = moneyFactory.create(transferDto.getAmount(), transferDto.getCurrencyCode());
        transferValidationService.validate(accountFrom, transferBalance);
        changeBalances(accountFrom, accountTo, transferBalance);
        return createStandardTransfer(accountFrom, accountTo, transferBalance, transferDto.getDescription());
    }

    private BankAccount findAccount(Long userId) {
        return standardBankAccountRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Account does not exist"));
    }

    @Transactional
    private void changeBalances(BankAccount accountFrom, BankAccount accountTo, Money amount) {
        accountFrom.withdraw(amount);
        bankAccountService.saveInAppropriateRepository(accountFrom);
        accountTo.deposit(amount);
        bankAccountService.saveInAppropriateRepository(accountTo);
    }

    private Transfer createStandardTransfer(BankAccount accountFrom, BankAccount accountTo, Money amount, String description) {
        StandardTransfer transfer = StandardTransfer.builder()
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .amount(amount)
                .description(description)
                .build();
        return standardTransferRepository.save(transfer);
    }
}
