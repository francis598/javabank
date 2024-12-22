package com.codeforall.online.javabank.controllers;

import com.codeforall.online.javabank.exceptions.AccountNotFoundException;
import com.codeforall.online.javabank.exceptions.TransactionInvalidException;
import com.codeforall.online.javabank.services.TransactionService;
import com.codeforall.online.javabank.model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for rendering {@link Account} related views
 */
@Controller
@SessionAttributes("customerId")
@RequestMapping("/account")
public class AccountController {

    private TransactionService transactionService;

    /**
     * Render a view with a list of transactions connected to an account
     * @param id the account id
     * @param model the model object
     * @return the view to render
     */
    @RequestMapping(method = RequestMethod.GET, path = {"/{id}"})
    public String listTransactions(@PathVariable int id, @ModelAttribute("customerId") int customerId, Model model) {
        try {
            model.addAttribute("transactions", transactionService.getAccountStatement(id));

        } catch (TransactionInvalidException | AccountNotFoundException e) {
            return "redirect:/customer/" + customerId;
        }

        return "account-statement";
    }

    /**
     * Set the transaction service
     * @param transactionService to set
     */
    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
