package com.agropay.wallet.ext;

import com.agropay.wallet.model.Transaction;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class TransactionsResponse implements Serializable {
    private static final long serialVersionUID = 5180474166193484856L;

    private List<Transaction> transactions;

    public TransactionsResponse() {
    }

    public TransactionsResponse(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionsResponse that = (TransactionsResponse) o;
        return Objects.equals(transactions, that.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactions);
    }

    @Override
    public String toString() {
        return "TransactionsResponse{" +
                "transactions=" + transactions +
                '}';
    }
}
