package com.codeforall.online.javabank.services;

import com.codeforall.online.javabank.exceptions.AccountNotFoundException;
import com.codeforall.online.javabank.exceptions.CustomerNotFoundException;
import com.codeforall.online.javabank.exceptions.TransactionInvalidException;
import com.codeforall.online.javabank.model.Address;
import com.codeforall.online.javabank.model.Customer;
import com.codeforall.online.javabank.model.account.Account;

import java.util.List;

/**
 * Common interface for account services, provides methods to manage accounts
 */
public interface CustomerService {

    /**
     * Get the customer with the given id
     * @param customerId the customer id
     * @return the customer
     * @throws CustomerNotFoundException
     */
    Customer get(int customerId) throws CustomerNotFoundException;

    /**
     * List all customers
     * @return a list of customers
     * @throws CustomerNotFoundException
     * @throws TransactionInvalidException
     * @throws AccountNotFoundException
     */
    List<Customer> list() throws CustomerNotFoundException, TransactionInvalidException, AccountNotFoundException;

    /**
     * Get the total balance of a given customer accounts
     * @param customerId the customer id
     * @return the balance of all accounts of the given customer
     * @throws CustomerNotFoundException
     */
    double getBalance(int customerId) throws CustomerNotFoundException;

    /**
     * Add a given customer to customer list
     * @param customer the customer to add
     * @param address to add to the customer
     * @return the customer
     */
    Customer add(Customer customer, Address address);

    /**
     * Add given account to a customer and account service lists
     * @param id the customer id to associate the account with
     * @param account the account to be added
     * @throws TransactionInvalidException
     * @throws CustomerNotFoundException
     */
    void openAccount(Integer id, Account account) throws TransactionInvalidException, CustomerNotFoundException;

    /**
     * updates an existing customer
     * @param id
     * @param customer
     * @param address
     * @throws TransactionInvalidException
     * @throws CustomerNotFoundException
     */
    void updateCustomer(Integer id, Customer customer, Address address) throws CustomerNotFoundException;

    /**
     * deletes a customer
     * @param id
     * @throws CustomerNotFoundException
     */
    void deleteCustomer(Integer id) throws CustomerNotFoundException;

    void deleteAccountFromCustomer(int customerId, int accountId) throws CustomerNotFoundException, AccountNotFoundException;

    /**
     * Sets the account service
     * @param accountService
     */
    public void setAccountService(AccountService accountService);


}