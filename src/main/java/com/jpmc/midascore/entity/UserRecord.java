package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UserRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private float balance;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionRecord> sentTransactions;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionRecord> receivedTransactions;

    protected UserRecord() {}

    public UserRecord(String username, float balance) {
        this.username = username;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public List<TransactionRecord> getSentTransactions() {
        return sentTransactions;
    }

    public List<TransactionRecord> getReceivedTransactions() {
        return receivedTransactions;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, username='%s', balance='%f']", id, username, balance);
    }
}