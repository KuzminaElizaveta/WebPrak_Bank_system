package com.WebPrak.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class IndexController {
    @RequestMapping(value = {"/index"})
    public String index() {
        return "index";
    }

//    @RequestMapping(value = "/clients")
//    public String allPersons() {
//        return "clients";
//    }

    @RequestMapping(value = "/accounts")
    public String allAccounts() {
        return "accounts";
    }

    @RequestMapping(value = "/operations")
    public String allOperations() {
        return "operations";
    }

}