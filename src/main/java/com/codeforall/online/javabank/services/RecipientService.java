package com.codeforall.online.javabank.services;

import com.codeforall.online.javabank.exceptions.CustomerNotFoundException;
import com.codeforall.online.javabank.exceptions.TransactionInvalidException;
import com.codeforall.online.javabank.model.Recipient;
import java.util.List;

/**
 * Common interface for recipient services, provides methods to manage recipients
 */
public interface RecipientService {

    /**
     * Get the list of recipients of a customer
     * @param id of the customer
     * @return a list of recipients
     * @throws TransactionInvalidException
     * @throws CustomerNotFoundException
     */
    List<Recipient> getCustomerRecipients(int id) throws TransactionInvalidException, CustomerNotFoundException;

    /**
     * Get a recipient by the id
     * @param id of the recipient
     * @return the recipient
     */
    Recipient getRecipient(int id);
}
