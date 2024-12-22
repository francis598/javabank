package com.codeforall.online.javabank.services;

import com.codeforall.online.javabank.exceptions.AccountNotFoundException;
import com.codeforall.online.javabank.exceptions.TransactionInvalidException;
import com.codeforall.online.javabank.model.account.Account;

/**
 * Common interface for account services, provides methods to manage accounts
 */
public interface AccountService {

    /**
     * Add given account to AccountMap
     * @param account the account to add
     * @throws TransactionInvalidException
     */
    Account add(Account account) throws TransactionInvalidException;

    /**
     * Deposit amount in account with the given id
     * @param id the account id
     * @param amount the amount to deposit
     * @throws TransactionInvalidException
     * @throws AccountNotFoundException
     */
    void deposit(int id, double amount) throws TransactionInvalidException, AccountNotFoundException;

    /**
     * Withdraw amount from account with the given id
     * @param id the account id
     * @param amount the amount to withdraw
     * @throws AccountNotFoundException
     * @throws TransactionInvalidException
     */
    void withdraw(int id, double amount) throws AccountNotFoundException, TransactionInvalidException;

    /**
     * Transfer amount between two accounts
     * @param srcId the source account id
     * @param dstId the destiny account id
     * @param amount the amount to transfer
     * @throws TransactionInvalidException
     */
    void transfer(int srcId, int dstId, double amount) throws TransactionInvalidException;

    /**
     * Get the account with the given id
     * @param id the account id
     * @return the account
     * @throws TransactionInvalidException
     * @throws AccountNotFoundException
     */
    Account get(int id) throws TransactionInvalidException, AccountNotFoundException;
}