package com.agropay.wallet.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Passbook")
public class Passbook {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToOne
    private Wallet wallet;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public Passbook() {
    }

    public Passbook(Wallet wallet, List<Transaction> transactions) {
        this.wallet = wallet;
        this.transactions = transactions;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
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
        Passbook passbook = (Passbook) o;
        return Objects.equals(id, passbook.id) &&
                Objects.equals(wallet, passbook.wallet) &&
                Objects.equals(transactions, passbook.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wallet, transactions);
    }

    @Override
    public String toString() {
        return "Passbook{" +
                "id=" + id +
                ", wallet=" + wallet +
                ", transactions=" + transactions +
                '}';
    }
}
