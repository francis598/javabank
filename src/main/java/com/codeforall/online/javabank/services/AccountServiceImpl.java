package com.codeforall.online.javabank.services;

import com.codeforall.online.javabank.exceptions.AccountNotFoundException;
import com.codeforall.online.javabank.exceptions.TransactionInvalidException;
import com.codeforall.online.javabank.model.account.Account;
import com.codeforall.online.javabank.model.account.SavingsAccount;
import com.codeforall.online.javabank.persistence.daos.AccountDao;
import com.codeforall.online.javabank.persistence.managers.TransactionManager;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * An {@link AccountService} implementation
 */
@Service
public class AccountServiceImpl implements AccountService {

    private TransactionManager transactionManager;
    private AccountDao accountDao;

    /**
     * @see AccountService#add(Account)
     */
    @Override
    public Account add(Account account) throws TransactionInvalidException {

        if(!transactionManager.isTransactionActive()) {
            throw new TransactionInvalidException();
        }

        if (!account.canWithdraw() &&
                account.getBalance() < SavingsAccount.MIN_BALANCE) {
            throw new TransactionInvalidException();
        }

        return Optional.ofNullable(accountDao.saveOrUpdate(account))
                .orElseThrow(TransactionInvalidException::new);
    }

    /**
     * @see AccountService#deposit(int, double)
     */
    @Override
    public void deposit(int id, double amount) throws TransactionInvalidException, AccountNotFoundException {

        try {
            transactionManager.beginWrite();

            Account account = get(id);

            if (!account.canCredit(amount)) {
                throw new TransactionInvalidException();
            }

            account.credit(amount);

            accountDao.saveOrUpdate(account);

            transactionManager.commit();

        } catch (PersistenceException e) {
            transactionManager.rollback();
        }
    }

    /**
     * @see AccountService#withdraw (int, double)
     */
    @Override
    public void withdraw(int id, double amount) throws AccountNotFoundException, TransactionInvalidException {

        try {
            transactionManager.beginWrite();

            Account account = get(id);

            if (!account.canWithdraw()) {
                throw new TransactionInvalidException();
            }

            if (!account.canDebit(amount)) {
                throw new TransactionInvalidException();
            }

            account.debit(amount);

            accountDao.saveOrUpdate(account);

            transactionManager.commit();

        } catch (PersistenceException e) {
            transactionManager.rollback();
        }
    }

    /**
     * @see AccountService#transfer(int, int, double)
     */
    @Override
    public void transfer(int srcId, int dstId, double amount) throws TransactionInvalidException {

        try {
            transactionManager.beginWrite();

            Account srcAccount = get(srcId);
            Account dstAccount = get(dstId);

            if (!srcAccount.canDebit(amount) || !dstAccount.canCredit(amount)) {
                throw new TransactionInvalidException();
            }

            srcAccount.debit(amount);
            dstAccount.credit(amount);

            accountDao.saveOrUpdate(srcAccount);
            accountDao.saveOrUpdate(dstAccount);

            transactionManager.commit();

        } catch (PersistenceException | AccountNotFoundException e) {
            transactionManager.rollback();
        }
    }

    /**
     * @see AccountService#get(int)
     */
    @Override
    public Account get(int id) throws TransactionInvalidException, AccountNotFoundException {

        return Optional.ofNullable(accountDao.findById(id))
                .orElseThrow(AccountNotFoundException::new);
    }

    /**
     * Get the transaction manager
     * @return the transaction manager
     */
    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * Get the account data access object
     * @return the account dao
     */
    public AccountDao getAccountDao() {
        return accountDao;
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
     * Sets the account data access object
     * @param accountDao the account DAO to set
     */
    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
