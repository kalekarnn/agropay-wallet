package com.agropay.wallet.ext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class CurrentBalanceResponse implements Serializable {

    private static final long serialVersionUID = -2829923207194569811L;
    private BigDecimal amount;

    public CurrentBalanceResponse() {
    }

    public CurrentBalanceResponse(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentBalanceResponse that = (CurrentBalanceResponse) o;
        return Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return "CurrentBalanceResponse{" +
                "amount=" + amount +
                '}';
    }
}
