package com.agropay.wallet.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Wallet {
    final private UUID walletId;
    private BigDecimal balance;

    public Wallet(UUID walletId, BigDecimal balance) {
        this.walletId = walletId;
        this.balance = balance;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
