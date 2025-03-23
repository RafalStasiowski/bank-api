package com.rstasiowski.bank.repository;

import com.rstasiowski.bank.interfaces.BankAccount;
import com.rstasiowski.bank.model.StandardBankAccount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandardBankAccountRepository extends JpaRepository<StandardBankAccount, Long> {
    List<StandardBankAccount> findAllByUserId(Long ownerIdLong, Pageable pageable);
}
