package com.rstasiowski.bank.model;

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
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private BankAccount accountFrom;
    @ManyToOne
    private BankAccount accountTo;
    @Embedded
    private Money amount;
    private String description;
    @CreatedDate
    private LocalDateTime timestamp;
}
