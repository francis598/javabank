package com.codeforall.online.javabank.services;

import com.codeforall.online.javabank.exceptions.AccountNotFoundException;
import com.codeforall.online.javabank.exceptions.CustomerNotFoundException;
import com.codeforall.online.javabank.exceptions.TransactionInvalidException;
import com.codeforall.online.javabank.model.Address;
import com.codeforall.online.javabank.model.Customer;
import com.codeforall.online.javabank.model.account.Account;
import com.codeforall.online.javabank.persistence.daos.CustomerDao;
import com.codeforall.online.javabank.persistence.managers.TransactionManager;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * An {@link CustomerService} implementation
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private TransactionManager transactionManager;
    private AccountService accountService;
    private CustomerDao customerDao;
    private Account accountDao;
    private List<Customer> customers = new ArrayList<>();

    /**
     * @see CustomerService#get(int)
     */
    @Override
    public Customer get(int customerId) throws CustomerNotFoundException {

        return Optional.ofNullable(customerDao.findById(customerId))
                .orElseThrow(CustomerNotFoundException::new);
    }



    /**
     * @see CustomerService#list()
     */
    @Override
    public List<Customer> list() throws CustomerNotFoundException, TransactionInvalidException, AccountNotFoundException {

        customers = customerDao.findAll();

        for(Customer customer : customers) {

            // get the balance of each customer to show
            customer.setTotalBalance(getBalance(customer.getId()));
        }

        return customers;
    }

    /**
     * @see CustomerService#getBalance(int)
     */
    @Override
    public double getBalance(int customerId) throws CustomerNotFoundException {

        return get(customerId).getAccounts().stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }

    /**
     * @see CustomerService#add(Customer, Address)
     */
    @Override
    public Customer add(Customer customer, Address address) {
        Customer savedCustomer = null;

        try {
            transactionManager.beginWrite();

            if(customer.getPhotoURL() == null) {
                customer.setPhotoURL("profile-icon.png");
            }

            customer.setAddress(address);
            savedCustomer = customerDao.saveOrUpdate(customer);

            transactionManager.commit();

        } catch (PersistenceException e) {
            transactionManager.rollback();
        }

        return savedCustomer;
    }

    /**
     * @see CustomerService#openAccount(Integer, Account)
     */
    @Override
    public void openAccount(Integer id, Account account) throws TransactionInvalidException, CustomerNotFoundException {

        try {
            transactionManager.beginWrite();

            Customer customer = get(id);

            Account newAccount = accountService.add(account);

            customer.addAccount(newAccount);
            customerDao.saveOrUpdate(customer);

            transactionManager.commit();

        } catch (PersistenceException e) {
            transactionManager.rollback();
        }
    }

    /**
     * @see CustomerService#updateCustomer
     */
    @Override
    public void updateCustomer(Integer id, Customer customer, Address address) throws CustomerNotFoundException {
        try {
            transactionManager.beginWrite();

            Customer customerToUpdate = get(id);

            customerToUpdate.setFirstName(customer.getFirstName());
            customerToUpdate.setLastName(customer.getLastName());
            customerToUpdate.setPhone(customer.getPhone());
            customerToUpdate.setEmail(customer.getEmail());
            customerToUpdate.setAddress(address);

            customerDao.saveOrUpdate(customerToUpdate);

            transactionManager.commit();
        } catch (PersistenceException e) {
            transactionManager.rollback();
        }
    }

    /**
     * @see CustomerService#deleteCustomer
     */
    @Override
    public void deleteCustomer(Integer id) throws CustomerNotFoundException {
        try {
            transactionManager.beginWrite();

            Customer customerToDelete = get(id);

            customerDao.delete(customerToDelete.getId());

            transactionManager.commit();

        } catch (PersistenceException e) {
            transactionManager.rollback();
        }
    }

    /**
     * Deletes an account from a specific customer
     * @param customerId
     * @param accountId
     * @throws CustomerNotFoundException
     * @throws AccountNotFoundException
     */
    @Override
    public void deleteAccountFromCustomer(int customerId, int accountId) throws CustomerNotFoundException, AccountNotFoundException {
        try {
            transactionManager.beginWrite();

            Customer customer = customerDao.findById(customerId);
            Account account = accountService.get(accountId);

            if (customer == null) {
                throw new CustomerNotFoundException();
            }

            if (account == null) {
                throw new AccountNotFoundException();
            }

            customer.getAccounts().remove(account);
            account.getCustomers().remove(customer);
            customerDao.saveOrUpdate(customer);
            transactionManager.commit();
        } catch (Exception e) {
            transactionManager.rollback();
        }
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
     * Get the customer dao
     * @return the customer dao
     */
    public CustomerDao getCustomerDao() {
        return customerDao;
    }

    /**
     * Get a list of the customers
     * @return the list of customers
     */
    public List<Customer> getCustomers() {
        return customers;
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
     * @see CustomerService#setAccountService(AccountService)
     */
    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Sets the customer data access object
     * @param customerDao the customer DAO to set
     */
    @Autowired
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    /**
     * Set a list of customer
     * @param customers to set
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void setAccountDao(Account accountDao) {
        this.accountDao = accountDao;
    }
}
