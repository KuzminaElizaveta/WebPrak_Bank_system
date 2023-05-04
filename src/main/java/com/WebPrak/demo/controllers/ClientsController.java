package com.WebPrak.demo.controllers;

import com.WebPrak.demo.DAO.ClientDAO;
import com.WebPrak.demo.DAO.Client_typeDAO;
import com.WebPrak.demo.DAO.Implement.ClientDAOImplement;
import com.WebPrak.demo.DAO.Implement.Client_typeDAOImplement;
import com.WebPrak.demo.tables.Client_type;
import com.WebPrak.demo.tables.Clients;
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
public class ClientsController {
    @Autowired
    private final ClientDAOImplement clientDAO = new ClientDAOImplement();

    @Autowired
    private final Client_typeDAOImplement client_typeDAO = new Client_typeDAOImplement();

    @GetMapping("/client")
    public String clientPage(@RequestParam(name="client_id") Long client_id, Model model) {
        Clients client = clientDAO.getById(client_id);
        if (client == null) {
            model.addAttribute("error_msg", "Not found by ID  = " + client_id);
            return "errors";
        }

        model.addAttribute("client", client);
        model.addAttribute("clientDAO", clientDAO);
        return "client";
    }

    @GetMapping("/clients")
    public String filterClients(@RequestParam(name="fullname", required = false) String fullname,
                               @RequestParam(name="address", required = false) String address,
                               @RequestParam(name="phone", required = false) String phone,
                               @RequestParam(name="email", required = false) String email,
                               @RequestParam(name="type", required = false) String type,
                               @RequestParam(name="birthday", required = false) LocalDate birthday,
                               Model model) {
        Client_typeDAO.Filter k = new Client_typeDAO.Filter(type);
        Client_type types = client_typeDAO.getByFilter(k);
        ClientDAO.Filter f = new ClientDAO.Filter(fullname, address, phone, email, types, birthday);
        Collection<Clients> clients = clientDAO.getByFilter(f);

        model.addAttribute("clients", clients);
        model.addAttribute("clientDAO", clientDAO);
        return "clients";
    }

    @RequestMapping(value="/add_new_client", method = RequestMethod.POST)
    public String addEvent(@RequestParam(name="fullname", required = false) String fullname,
                           @RequestParam(name="address", required = false) String address,
                           @RequestParam(name="phone", required = false) String phone,
                           @RequestParam(name="email", required = false) String email,
                           @RequestParam(name="type", required = false) Client_type type,
                           @RequestParam(name="birthday", required = false) LocalDate birthday,
                           Model model) {
        Clients newClient = new Clients(fullname, address, phone, email, type, birthday);
        clientDAO.save(newClient);
        model.addAttribute("clients", clientDAO.getAll());
        model.addAttribute("clientDAO", clientDAO);
        return "redirect:/client?clientID=" + newClient.getId().toString();
    }

    @RequestMapping(value="/delete_client", method = RequestMethod.POST)
    public String addClient(@RequestParam(name="client_id", required = true) Long clientID,
                           Model model) {
        clientDAO.deleteById(clientID);
        model.addAttribute("clients", clientDAO.getAll());
        model.addAttribute("clientDAO", clientDAO);
        return "redirect:/clients";
    }

    @RequestMapping(value = "addclient")
    public String addclient() {
        return "addclient";
    }
}


