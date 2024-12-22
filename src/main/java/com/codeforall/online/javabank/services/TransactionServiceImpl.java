package com.codeforall.online.javabank.services;

import com.codeforall.online.javabank.exceptions.AccountNotFoundException;
import com.codeforall.online.javabank.exceptions.TransactionInvalidException;
import com.codeforall.online.javabank.model.account.Account;
import com.codeforall.online.javabank.model.transaction.Transaction;
import com.codeforall.online.javabank.persistence.managers.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link TransactionService} implementation
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionManager transactionManager;
    private AccountService accountService;

    /**
     * @see TransactionService#getAccountStatement(int)
     */
    @Override
    public List<Transaction> getAccountStatement(int accountId) throws TransactionInvalidException, AccountNotFoundException {
        Account account = accountService.get(accountId);

        return new ArrayList<>(account.getTransactions());
    }

    /**
     * Get the transaction manager
     * @return the transaction manager
     */
    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * Get the account service
     * @return the account service
     */
    public AccountService getAccountService() {
        return accountService;
    }

    /**
     * Set the transaction manager
     * @param transactionManager
     */
    @Autowired
    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * Set the account service
     * @param accountService
     */
    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
