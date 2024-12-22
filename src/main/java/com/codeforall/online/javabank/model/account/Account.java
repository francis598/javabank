package com.codeforall.online.javabank.model.account;

import com.codeforall.online.javabank.model.AbstractModel;
import com.codeforall.online.javabank.model.Customer;
import com.codeforall.online.javabank.model.transaction.Transaction;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * A generic account domain entity to be used as a base for concrete types of accounts
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type")
@Table(name = "accounts")
public abstract class Account extends AbstractModel {

    private double balance = 0;

    @ManyToMany(mappedBy = "accounts")
    private Set<Customer> customers = new HashSet<>();

    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            mappedBy = "account",
            fetch = FetchType.LAZY
    )
    private Set<Transaction> transactions = new HashSet<>();

    /**
     * Remove all customer associations
     */
    @PreRemove  // This annotation indicates that this method is invoked before an entity is removed from the database
    private void removeCustomerAssociations() {
        for (Customer customer: customers) {
            customer.getAccounts().remove(this);
        }
    }

    /**
     * Get the account type
     * @return the account type
     */
    public abstract AccountType getAccountType();

    /**
     * Credit account if possible
     * @param amount the amount to credit
     *
     */
    public void credit(double amount) {
        if (canCredit(amount)) {
            balance += amount;
        }
    }

    /**
     * Debit the account if possible
     * @param amount the amount to debit
     */
    public void debit(double amount) {
        if (canDebit(amount)) {
            balance -= amount;
        }
    }

    /**
     * Check if a specific amount can be credited on the account
     * @param amount the amount to check
     * @return {@code true} if the account can be credited
     */
    public boolean canCredit(double amount) {
        return amount > 0;
    }

    /**
     * Check if a specific amount can be debited from the account
     * @param amount the amount to check
     * @return {@code true} if the account can be debited
     */
    public boolean canDebit(double amount) {
        return amount > 0 && amount <= balance;
    }

    /**
     * Check if the account can be withdrawn
     * @return {@code true} if withdraw can be done
     */
    public boolean canWithdraw() {
        return true;
    }

    /**
     * Get the account balance
     * @return the account balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Add transaction
     * @param transaction the transaction to be added
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setAccount(this);
    }

    /**
     * Remove transaction
     * @param transaction the transaction to be removed
     */
    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
        transaction.setAccount(null);
    }

    /**
     * Get customers associated with this account
     * @return customers
     */
    public Set<Customer> getCustomers() {
        return customers;
    }

    /**
     * Get the transactions associated with this account
     * @return transactions
     */
    public Set<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Set the customers to the account
     * @param customers to set
     */
    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Set the transactions to the account
     * @param transactions list
     */
    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}