package com.rstasiowski.bank.repository;


import com.rstasiowski.bank.model.StandardTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<StandardTransfer, Long> {
}
