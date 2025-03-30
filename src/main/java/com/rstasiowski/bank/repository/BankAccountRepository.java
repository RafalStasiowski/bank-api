package com.rstasiowski.bank.repository;

import com.rstasiowski.bank.model.BankAccount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findAllByUserId(Long ownerIdLong, Pageable pageable);
}
