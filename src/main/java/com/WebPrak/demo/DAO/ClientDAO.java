package com.WebPrak.demo.DAO;

import com.WebPrak.demo.tables.Accounts;
import com.WebPrak.demo.tables.Client_type;
import com.WebPrak.demo.tables.Clients;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Getter;


import java.util.List;

import java.time.LocalDate;


public interface ClientDAO extends CommonDAO<Clients, Long>  {

    List<Clients> getAllByName(String Name);
    Clients getByName(String Name);

    List<Accounts> findAllAccountOfClient(Clients client);
    List<Clients> getByFilter(Filter filter);
    @Getter
    @AllArgsConstructor
    @Builder
    class Filter {
        private String fullname;
        private String address;
        private String phone;
        private String email;
        private List<Client_type> type_id;
        private LocalDate birthday;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

}