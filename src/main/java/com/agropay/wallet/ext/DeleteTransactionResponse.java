package com.agropay.wallet.ext;

import com.agropay.wallet.common.TransactionStatus;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class DeleteTransactionResponse implements Serializable {
    private static final long serialVersionUID = -3781020311723057621L;

    private UUID transactionId;
    private TransactionStatus status;

    public DeleteTransactionResponse() {
    }

    public DeleteTransactionResponse(UUID transactionId, TransactionStatus status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteTransactionResponse that = (DeleteTransactionResponse) o;
        return Objects.equals(transactionId, that.transactionId) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, status);
    }
}
