package com.agropay.wallet.ext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class CreateWalletRequest implements Serializable {
    private static final long serialVersionUID = -1294390986561089621L;

    private BigDecimal overdraftLimit;

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(BigDecimal overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateWalletRequest that = (CreateWalletRequest) o;
        return Objects.equals(overdraftLimit, that.overdraftLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(overdraftLimit);
    }

    @Override
    public String toString() {
        return "CreateWalletRequest{" +
                "overdraftLimit=" + overdraftLimit +
                '}';
    }
}
