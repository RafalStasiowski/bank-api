package com.rstasiowski.bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Money {
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Currency currency;

    private Money(BigDecimal amount, Currency currency) {
        validateNonNegativeAmount(amount);
        this.amount = amount;
        this.currency = currency;
    }

    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    private void validateNonNegativeAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Required non negative amount");
        }
    }

    public Money add(Money other) {
        ensureSameCurrency(other);
        return Money.of(this.amount.add(other.getAmount()), this.currency);
    }

    public Money substract(Money other) {
        ensureSameCurrency(other);
        return Money.of(this.amount.subtract(other.getAmount()), this.currency);
    }

    public Money multiply(BigDecimal multiplier) {
        return Money.of(this.amount.multiply(multiplier), this.currency);
    }

    public Money divide(BigDecimal divisor) {
        if (divisor.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Nie można dzielić przez zero");
        }
        return Money.of(this.amount.divide(divisor, RoundingMode.HALF_UP), this.currency);
    }

    public int compareTo(Money other) {
        ensureSameCurrency(other);
        return this.amount.compareTo(other.getAmount());
    }

    private void ensureSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot perform operation on different currencies");
        }
    }

}
