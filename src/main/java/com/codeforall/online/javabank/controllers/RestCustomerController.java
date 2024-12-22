package com.codeforall.online.javabank.controllers;

import com.codeforall.online.javabank.exceptions.AccountNotFoundException;
import com.codeforall.online.javabank.exceptions.CustomerNotFoundException;
import com.codeforall.online.javabank.exceptions.TransactionInvalidException;
import com.codeforall.online.javabank.model.Address;
import com.codeforall.online.javabank.model.Customer;
import com.codeforall.online.javabank.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class RestCustomerController {

    private CustomerService customerService;

    /**
     * lists all customers
     * @return
     * @throws CustomerNotFoundException
     * @throws TransactionInvalidException
     * @throws AccountNotFoundException
     */
    //why do I get the same client multiple times?
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = {"/", ""})
    public ResponseEntity<List<Customer>> listCustomers() throws CustomerNotFoundException, TransactionInvalidException, AccountNotFoundException {

        List<Customer> customers = customerService.list();

        if (customers.isEmpty()) {
            return new ResponseEntity<>(customers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(customers, HttpStatus.OK);
        }
    }


    /**
     * creates a new customer
     *
     * @param customer
     * @return
     */
    //What is wrong with this? id and address null???
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = {"/", ""})
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {

        try {
            Customer newCustomer = customerService.add(customer, customer.getAddress());

            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Reads a specific client
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = {"/{id}"})
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        Customer customer = null;
        try {
            customer = customerService.get(id);

            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch(CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, path = { "/{id}"})
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id,
                                                   @RequestBody Customer customer, Address address) throws CustomerNotFoundException {
        try {
            Customer existingCustomer = customerService.get(id);

            customerService.updateCustomer(id, existingCustomer, address);

            return new ResponseEntity<>(existingCustomer, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, path = {"/{id}"})
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) throws CustomerNotFoundException {
        try {
            customerService.deleteCustomer(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
