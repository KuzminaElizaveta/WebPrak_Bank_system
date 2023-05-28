package com.WebPrak.demo.controllers;

import com.WebPrak.demo.DAO.*;
import com.WebPrak.demo.DAO.Client_typeDAO;
import com.WebPrak.demo.DAO.Implement.ClientDAOImplement;
import com.WebPrak.demo.DAO.Implement.Client_typeDAOImplement;
import com.WebPrak.demo.DAO.Implement.Account_typeDAOImplement;
import com.WebPrak.demo.DAO.Implement.AccountsDAOImplement;
import com.WebPrak.demo.tables.Client_type;
import com.WebPrak.demo.tables.*;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.sql.Date;

import java.time.Instant;
import java.time.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
public class AccountsController {
    @Autowired
    private final ClientDAOImplement clientDAO = new ClientDAOImplement();

    @Autowired
    private final Client_typeDAOImplement client_typeDAO = new Client_typeDAOImplement();

    @Autowired
    private final AccountsDAOImplement accountDAO = new AccountsDAOImplement();

    @Autowired
    private final Account_typeDAOImplement account_typeDAO = new Account_typeDAOImplement();


    @GetMapping("/account")final
    public String accountPage(@RequestParam(name="account_id") Long account_id, Model model) {
        Accounts account = accountDAO.getById(account_id);
        if (account == null) {
            model.addAttribute("error_msg", "Not found by ID  = " + account_id);
            return "errors";
        }

        Collection<Operations> t_op = account.getOperations();
        model.addAttribute("t_op", t_op);

        model.addAttribute("account", account);
        model.addAttribute("accountDAO", accountDAO);
        return "account";
    }

    @GetMapping("/accounts")
    public String filterAccounts(@RequestParam(name="client", required = false) String client,
                                @RequestParam(name="type", required = false) String type,
                                @RequestParam(name="balance", required = false) Float balance,
                                @RequestParam(name="credit", required = false) LocalDate credit,
                                @RequestParam(name="percent", required = false) Float percent,
                                @RequestParam(name="interval", required = false) Integer interval,
                                @RequestParam(name="period", required = false) Integer period,
                                Model model) {
        Account_type types;
        Collection<Accounts> accounts;
        types = account_typeDAO.getByType(type);
        Clients clients = clientDAO.getByName(client);
        AccountsDAO.Filter f = new AccountsDAO.Filter(clients, types, balance, credit, percent, interval, period);
        accounts = accountDAO.getByFilter(f);


        model.addAttribute("accounts", accounts);
        model.addAttribute("accountDAO", accountDAO);
        return "accounts";
    }

    @RequestMapping(value="/add_new_account", method = RequestMethod.POST)
    public String addAccount(@RequestParam(name="client", required = false) String client,
                           @RequestParam(name="type", required = false) String type,
                           @RequestParam(name="balance", required = false) Float balance,
                           @RequestParam(name="credit", required = false) LocalDate credit,
                           @RequestParam(name="percent", required = false) Float percent,
                           @RequestParam(name="interval", required = false) Integer interval,
                           @RequestParam(name="period", required = false) Integer period,
                           Model model)  {
        Account_type types = account_typeDAO.getByType(type);
        Clients clients = clientDAO.getByName(client);
        Accounts newAccount = new Accounts(clients, types, balance, credit ,percent, interval, period);
        clientDAO.update(clients);
        accountDAO.save(newAccount);
        model.addAttribute("accounts", accountDAO.getAll());
        model.addAttribute("accountDAO", accountDAO);
        return "redirect:/account?account_id=" + newAccount.getId().toString();
    }

    @RequestMapping(value="/edit_account", method = RequestMethod.POST)
    public String editAccount(@RequestParam(name="account_id", required = true) Long account_id,
//                             @RequestParam(name="client", required = false) String client,
//                             @RequestParam(name="type", required = false) String type,
                             @RequestParam(name="balance", required = false) Float balance,
                             @RequestParam(name="credit", required = false) LocalDate credit,
                             @RequestParam(name="percent", required = false) Float percent,
                             @RequestParam(name="interval", required = false) Integer interval,
                             @RequestParam(name="period", required = false) Integer period,
                             Model model) {

        Accounts account = accountDAO.getById(account_id);

////        Account_type types = account_typeDAO.getByType(type);
//        Clients clients = clientDAO.getByName(client);
//        clients.getAccounts().add(account);
//        account.setClient_id(clients);
////        account.setAcc_type_id(types);
        account.setBalance(balance);
        account.setCredit(credit);
        account.setPercent(percent);
        account.setInterval(interval);
        account.setPeriod(period);

        accountDAO.update(account);


        model.addAttribute("accounts", accountDAO.getAll());
        model.addAttribute("accountDAO", accountDAO);
        return "redirect:/account?account_id=" + account.getId().toString();
    }

    @RequestMapping(value="/delete_account", method = RequestMethod.POST)
    public String deleteAccount(@RequestParam(name="account_id", required = true) Long account_id,
                            Model model) {
        accountDAO.deleteById(account_id);
        model.addAttribute("accounts", accountDAO.getAll());
        model.addAttribute("accountDAO", accountDAO);
        return "redirect:/accounts";
    }

    @RequestMapping(value = "editaccount")
    public String editaccount(@RequestParam(name="account_id") Long account_id, Model model) {
        Accounts account = accountDAO.getById(account_id);
        if (account == null) {
            model.addAttribute("error_msg", "Not found by ID  = " + account_id);
            return "error";
        }

        model.addAttribute("account", account);
        model.addAttribute("accountDAO", accountDAO);
        return "editaccount";
    }

    @RequestMapping(value = "addaccount")
    public String addaccount() {
        return "addaccount";
    }
}
