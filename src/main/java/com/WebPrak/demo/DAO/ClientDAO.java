package com.WebPrak.demo.DAO;

import com.WebPrak.demo.tables.Accounts;
import com.WebPrak.demo.tables.Clients;
import lombok.AllArgsConstructor;

import lombok.Getter;


import java.util.List;

import java.sql.Date;


public interface ClientDAO extends CommonDAO<Clients, Long>  {

    List<Accounts> findAllAccountOfClient(Clients client);
    List<Clients> getByFilter(Filter filter);
    @Getter
    @AllArgsConstructor
    class Filter {
        private String fullname;
        private String address;
        private String phone;
        private String email;
        private Date birthday;
    }

}