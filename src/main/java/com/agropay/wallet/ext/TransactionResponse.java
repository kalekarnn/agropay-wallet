package com.agropay.wallet.ext;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class TransactionResponse implements Serializable {
    private static final long serialVersionUID = -4706464596842945245L;

    private UUID transactionId;

    public TransactionResponse(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionResponse that = (TransactionResponse) o;
        return Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "transactionId=" + transactionId +
                '}';
    }
}
