package com.codeforall.online.javabank.persistence.daos.jpa;

import com.codeforall.online.javabank.model.account.Account;
import com.codeforall.online.javabank.persistence.daos.AccountDao;
import org.springframework.stereotype.Repository;

/**
 * A JPA {@link AccountDao} implementation
 */
@Repository
public class JpaAccountDao extends JpaGenericDao<Account> implements AccountDao {

    /**
     * @see JpaGenericDao#JpaGenericDao(Class)
     */
    public JpaAccountDao() {
        super(Account.class);
    }
}
