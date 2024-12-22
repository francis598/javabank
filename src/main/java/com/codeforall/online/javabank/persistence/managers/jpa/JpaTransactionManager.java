package com.codeforall.online.javabank.persistence.managers.jpa;

import com.codeforall.online.javabank.persistence.managers.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * An implementation of the transaction manager to work with JPA
 */
@Component
public class JpaTransactionManager implements TransactionManager {

    private JpaSessionManager sm;

    @Autowired
    public void setSm(JpaSessionManager sm) {
        this.sm = sm;
    }

    /**
     * @see TransactionManager#beginRead()
     */
    @Override
    public void beginRead() {
        sm.startSession();
    }

    /**
     * @see TransactionManager#beginWrite()
     */
    @Override
    public void beginWrite() {
        if(!sm.getCurrentSession().getTransaction().isActive()) {
            sm.getCurrentSession().getTransaction().begin();
        }
    }

    /**
     * @see TransactionManager#commit()
     */
    public void commit() {

        if(sm.getCurrentSession().getTransaction().isActive()) {
            sm.getCurrentSession().getTransaction().commit();
        }

        sm.stopSession();
    }

    /**
     * @see TransactionManager#rollback()
     */
    public void rollback() {

        if(sm.getCurrentSession().getTransaction().isActive()) {
            sm.getCurrentSession().getTransaction().rollback();
        }

        sm.stopSession();
    }

    /**
     * @see TransactionManager#isTransactionActive()
     */
    public boolean isTransactionActive() {
        return sm.getCurrentSession().getTransaction().isActive();
    }
}
