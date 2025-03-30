package com.rstasiowski.bank.enums;

public enum TransferType {
    DOMESTIC,
    INTERNAL,
    INTERNATIONAL;

    public String getBeanName() {
        return "TRANSFER_" + this.name();
    }
}
