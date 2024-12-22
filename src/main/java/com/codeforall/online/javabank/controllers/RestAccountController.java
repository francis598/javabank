package com.codeforall.online.javabank.controllers;

import com.codeforall.online.javabank.exceptions.AccountNotFoundException;
import com.codeforall.online.javabank.exceptions.CustomerNotFoundException;
import com.codeforall.online.javabank.model.Customer;
import com.codeforall.online.javabank.model.account.Account;
import com.codeforall.online.javabank.services.AccountService;
import com.codeforall.online.javabank.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class RestAccountController {

    private AccountService accountService;
    private CustomerService customerService;

    /**
     * Retrieves all accounts belonging to a customer
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = {"/{id}/account"})
    public ResponseEntity<List<Account>> getCustomerAccounts(@PathVariable int id) {

        try {

            Customer customer = customerService.get(id);

            List<Account> accounts = new ArrayList<>(customer.getAccounts());


            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a specific account belonging to a customer
     * @param cid
     * @param aid
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = {"/{cid}/account/{aid}"})
    public ResponseEntity<Account> getSpecificAccount(@PathVariable("cid") int cid,
                                                      @PathVariable("aid") int aid) {
        try {
            Customer customer = customerService.get(cid);

            Account account = (customer.getAccounts().stream()
                    .filter(a -> a.getId() == aid)
                    .findFirst()
                    .orElseThrow(AccountNotFoundException::new));

            return new ResponseEntity<>(account, HttpStatus.OK);

        } catch (CustomerNotFoundException | AccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes an account from a customer
     * @param cid
     * @param aid
     * @return
     * @throws CustomerNotFoundException
     * @throws AccountNotFoundException
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, path = {"/{cid}/account/{aid}"})
    public ResponseEntity<String> deleteAccount(@PathVariable int cid, @PathVariable int aid) throws CustomerNotFoundException, AccountNotFoundException {
        try {
           customerService.deleteAccountFromCustomer(cid, aid);

           return new ResponseEntity<>("Account deleted", HttpStatus.NO_CONTENT);
        } catch (CustomerNotFoundException | AccountNotFoundException e) {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }
    }


    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

}
