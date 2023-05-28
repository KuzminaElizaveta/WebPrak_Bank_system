package com.WebPrak.demo.controllers;

import com.WebPrak.demo.DAO.*;
import com.WebPrak.demo.DAO.Implement.*;
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
public class OperationsController {
    @Autowired
    private final OperationsDAOImplement operationDAO = new OperationsDAOImplement();

    @Autowired
    private final Operation_typeDAOImplement opp_typeDAO = new Operation_typeDAOImplement();

    @Autowired
    private final AccountsDAOImplement accountDAO = new AccountsDAOImplement();

    @Autowired
    private final DepartmentDAOImplement departmentDAO = new DepartmentDAOImplement();

    @GetMapping("/operation")final
    public String clientPage(@RequestParam(name="operation_id") Long operation_id, Model model) {
        Operations operation = operationDAO.getById(operation_id);
        if (operation == null) {
            model.addAttribute("error_msg", "Not found by ID  = " + operation_id);
            return "errors";
        }

        model.addAttribute("operation", operation);
        model.addAttribute("operationDAO", operationDAO);
        return "operation";
    }

    @GetMapping("/operations")
    public String filterOperations(@RequestParam(name="account", required = false) Long account,
                                @RequestParam(name="department", required = false) Long department,
                                @RequestParam(name="type", required = false) String type,
                                @RequestParam(name="date", required = false) LocalDate date,
                                @RequestParam(name="amount", required = false) Float amount,
                                Model model) {
        Operation_type types = opp_typeDAO.getByType(type);
        Collection<Operations> operations;
        Accounts accounts = accountDAO.getById(account);
        Departments departments = departmentDAO.getById(department);

        OperationsDAO.Filter f = new OperationsDAO.Filter(accounts, departments, types, date, amount);
        operations = operationDAO.getByFilter(f);


        model.addAttribute("operations", operations);
        model.addAttribute("operationDAO", operationDAO);
        return "operations";
    }

    @RequestMapping(value="/add_new_operation", method = RequestMethod.POST)
    public String addOperation(@RequestParam(name="account", required = false) Long account,
                            @RequestParam(name="department", required = false) Long department,
                            @RequestParam(name="type", required = false) String type,
                            @RequestParam(name="date", required = false) LocalDate date,
                            @RequestParam(name="amount", required = false) Float amount,
                            Model model) {
        Operation_type types = opp_typeDAO.getByType(type);
        Accounts accounts = accountDAO.getById(account);
        Departments departments = departmentDAO.getById(department);

        Operations newOperation  = new Operations(accounts, departments, types, date, amount);
        operationDAO.save(newOperation);
        model.addAttribute("operations", operationDAO.getAll());
        model.addAttribute("operationDAO", operationDAO);
        return "redirect:/operation?operation_id=" + newOperation.getId().toString();
    }

    @RequestMapping(value = "addoperation")
    public String addoperation() {
        return "addoperation";
    }
}


