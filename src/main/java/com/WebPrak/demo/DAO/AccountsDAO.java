package com.WebPrak.demo.DAO;

import com.WebPrak.demo.tables.Accounts;
import com.WebPrak.demo.tables.Clients;
import lombok.AllArgsConstructor;

import lombok.Getter;

import java.sql.Date;

import java.util.List;
public interface AccountsDAO extends CommonDAO<Accounts, Long>{
    List<Accounts> getByFilter(AccountsDAO.Filter filter);
    @Getter
    @AllArgsConstructor
    class Filter {
        private Float balance;
        private Date credit;
        private Float percent;
        private Integer interval;
        private Integer period;
    }

    List<Clients> findClientOfAccount(Accounts account);}
