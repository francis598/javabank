package com.codeforall.online.javabank.persistence.daos.jpa;

import com.codeforall.online.javabank.model.Customer;
import com.codeforall.online.javabank.persistence.daos.CustomerDao;
import org.springframework.stereotype.Repository;

/**
 * A JPA {@link CustomerDao} implementation
 */
@Repository
public class JpaCustomerDao extends JpaGenericDao<Customer> implements CustomerDao {

    /**
     * @see JpaGenericDao#JpaGenericDao(Class)
     */
    public JpaCustomerDao() {
        super(Customer.class);
    }
}
