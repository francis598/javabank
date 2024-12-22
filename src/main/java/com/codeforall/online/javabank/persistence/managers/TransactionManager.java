package com.codeforall.online.javabank.persistence.managers;

/**
 * Common interface for a transaction manager
 */
public interface TransactionManager {

    /**
     * Begins a session to perform a read operation
     */
    void beginRead();

    /**
     * Begins a session to perform a write operation
     */
    void beginWrite();

    /**
     * Commits a transaction
     */
    void commit();

    /**
     * Rollback a transaction
     */
    void rollback();

    /**
     * Check if a transaction is active
     * @return true if the transaction is active and false if it is not
     */
    boolean isTransactionActive();
}
