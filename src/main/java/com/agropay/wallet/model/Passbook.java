package com.agropay.wallet.model;

import java.util.List;
import java.util.UUID;

public class Passbook {
    final private UUID passbookId;
    final private UUID walletId;
    private List<Transaction> transactions;

    public Passbook(UUID passbookId, UUID walletId, List<Transaction> transactions) {
        this.passbookId = passbookId;
        this.walletId = walletId;
        this.transactions = transactions;
    }

    public UUID getPassbookId() {
        return passbookId;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
