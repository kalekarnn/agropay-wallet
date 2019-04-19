package com.agropay.wallet.ext;

import com.agropay.wallet.common.TransactionType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class TransactionRequest implements Serializable {
    private static final long serialVersionUID = -4305199881209774104L;

    private BigDecimal amount;
    private TransactionType type;

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionRequest that = (TransactionRequest) o;
        return Objects.equals(amount, that.amount) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, type);
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "amount=" + amount +
                ", type=" + type +
                '}';
    }
}
