package com.codeforall.online.javabank.services;

import com.codeforall.online.javabank.exceptions.CustomerNotFoundException;
import com.codeforall.online.javabank.model.Customer;
import com.codeforall.online.javabank.model.Recipient;
import com.codeforall.online.javabank.persistence.daos.RecipientDao;
import com.codeforall.online.javabank.persistence.managers.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link RecipientService} implementation
 */
@Service
public class RecipientServiceImpl implements RecipientService {

    private TransactionManager transactionManager;
    private CustomerService customerService;
    private RecipientDao recipientDao;

    /**
     * @see RecipientService#getCustomerRecipients(int)
     */
    @Override
    public List<Recipient> getCustomerRecipients(int id) throws CustomerNotFoundException {
        Customer customer = customerService.get(id);

        return new ArrayList<>(customer.getRecipients());
    }

    /**
     * @see RecipientService#getRecipient(int)
     */
    @Override
    public Recipient getRecipient(int id) {
        return recipientDao.findById(id);
    }


    /**
     * Get the transaction manager
     * @return the transaction manager
     */
    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * Get the customer service
     * @return the customer service
     */
    public CustomerService getCustomerService() {
        return customerService;
    }

    /**
     * Get the recipient data access object
     * @return the recipient dao
     */
    public RecipientDao getRecipientDao() {
        return recipientDao;
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
     * Set the customer service
     * @param customerService to set
     */
    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Set the recipient data access object
     * @param recipientDao to set
     */
    @Autowired
    public void setRecipientDao(RecipientDao recipientDao) {
        this.recipientDao = recipientDao;
    }
}
