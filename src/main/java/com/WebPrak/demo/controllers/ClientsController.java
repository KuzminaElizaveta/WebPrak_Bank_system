package com.WebPrak.demo.controllers;

import com.WebPrak.demo.DAO.ClientDAO;
import com.WebPrak.demo.DAO.Implement.ClientDAOImplement;
import com.WebPrak.demo.tables.Clients;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.sql.Date;
@Controller
public class ClientsController {
    @Autowired
    private final ClientDAOImplement clientDAO = new ClientDAOImplement();

    @GetMapping("/client")
    public String clientPage(@RequestParam(name="client_id") Long client_id, Model model) {
        Clients client = clientDAO.getById(client_id);
        if (client == null) {
            model.addAttribute("error_msg", "Not found by ID  = " + client_id);
            return "errors";
        }

        model.addAttribute("client", client);
        model.addAttribute("clientDAO", clientDAO);
        return "clients";
    }

    @GetMapping("/clients")
    public String filterClients(@RequestParam(name="ФИО", required = false) String fullname,
                               @RequestParam(name="Адрес", required = false) String address,
                               @RequestParam(name="Телефон", required = false) String phone,
                               @RequestParam(name="Почта", required = false) String email,
                               @RequestParam(name="Дата рождения", required = false) Date birthday,
                               Model model) {
        ClientDAO.Filter f = new ClientDAO.Filter(fullname, address, phone, email, birthday);
        Collection<Clients> clients = clientDAO.getByFilter(f);

        model.addAttribute("clients", clients);
        model.addAttribute("clientDAO", clientDAO);
        return "clients";
    }
}


