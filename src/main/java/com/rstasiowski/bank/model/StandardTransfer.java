package com.rstasiowski.bank.model;

import com.rstasiowski.bank.interfaces.BankAccount;
import com.rstasiowski.bank.interfaces.Transfer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class StandardTransfer implements Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(targetEntity = StandardBankAccount.class)
    private BankAccount accountFrom;
    @ManyToOne(targetEntity = StandardBankAccount.class)
    private BankAccount accountTo;
    @Embedded
    private Money amount;
    private String description;
    @CreatedDate
    private LocalDateTime timestamp;
}
