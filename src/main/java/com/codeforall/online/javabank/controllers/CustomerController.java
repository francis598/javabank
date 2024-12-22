package com.codeforall.online.javabank.controllers;

import com.codeforall.online.javabank.exceptions.AccountNotFoundException;
import com.codeforall.online.javabank.exceptions.CustomerNotFoundException;
import com.codeforall.online.javabank.exceptions.TransactionInvalidException;
import com.codeforall.online.javabank.model.Address;
import com.codeforall.online.javabank.model.Customer;
import com.codeforall.online.javabank.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller responsible for rendering {@link Customer} related views
 */
@Controller
@SessionAttributes("customerId")
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    /**
     * Initialises the value to be stored in the session for current customer
     * @return 0 to symbolize that no customer has being selected yet
     */
    @ModelAttribute("customerId")
    public int getCurrentCustomerId() {
        return 0;
    }

    /**
     * Render a view with a list of customers
     * @param model the model object
     * @return the view to render
     */
    @RequestMapping(method = RequestMethod.GET, path = {"/list", "/", ""})
    public String listCustomers(Model model) {
        try {
            model.addAttribute("customers", customerService.list());
        } catch (CustomerNotFoundException | AccountNotFoundException | TransactionInvalidException e) {
            // do nothing, just show the customers that exist
        }

        return "index";
    }

    /**
     * Add a customer
     * @param model the model object
     * @return the view to render
     */
    @RequestMapping(method = RequestMethod.GET, path = "/add")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("address", new Address());

        return "add-update";
    }

    /**
     * Render a view with customer details
     * @param id the customer id
     * @param model the model object
     * @return the view to render
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public String showCustomer(@PathVariable Integer id, Model model) {
        Customer customer = null;

        try {
            customer = customerService.get(id);
            double totalBalance = customerService.getBalance(customer.getId());

            model.addAttribute("customer", customer);
            model.addAttribute("address", customer.getAddress());
            model.addAttribute("accounts", customer.getAccounts());
            model.addAttribute("recipients", customer.getRecipients());
            model.addAttribute("totalBalance", totalBalance);

            model.addAttribute("customerId", id);

        } catch (CustomerNotFoundException e) {

            return "redirect:/customer/list";
        }

        return "profile";
    }

    /**
     * Save the customer form submission and renders a view with the customer details
     * @param customer the customer form object
     * @param redirectAttributes the redirect attributes object
     * @return the view to render
     */
    @RequestMapping(method = RequestMethod.POST, path = {"/", ""})
    public String saveCustomer(@ModelAttribute("customer") Customer customer, @ModelAttribute("address") Address address, RedirectAttributes redirectAttributes) {
        Customer savedCustomer = customerService.add(customer, address);

        redirectAttributes.addFlashAttribute("lastAction", "Added " + savedCustomer.getFirstName() + " " + savedCustomer.getLastName() + " successfully!");

        return "redirect:/customer/list";
    }

    /**
     * Set the customer service
     * @param customerService to set
     */
    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}