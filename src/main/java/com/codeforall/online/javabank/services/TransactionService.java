package com.codeforall.online.javabank.services;

import com.codeforall.online.javabank.exceptions.AccountNotFoundException;
import com.codeforall.online.javabank.exceptions.TransactionInvalidException;
import com.codeforall.online.javabank.model.transaction.Transaction;
import java.util.List;

/**
 * Common interface for transaction services, provides methods to manage account transactions
 */
public interface TransactionService {

    /**
     * Gets the account statement
     * @param accountId of the account related to the transaction
     * @return a list of transactions
     * @throws TransactionInvalidException
     * @throws AccountNotFoundException
     */
    List<Transaction> getAccountStatement(int accountId) throws TransactionInvalidException, AccountNotFoundException;
}
