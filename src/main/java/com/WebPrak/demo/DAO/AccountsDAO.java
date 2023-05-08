package com.WebPrak.demo.DAO;

import com.WebPrak.demo.tables.Account_type;
import com.WebPrak.demo.tables.Accounts;
import com.WebPrak.demo.tables.Clients;
import lombok.AllArgsConstructor;

import lombok.Getter;

import java.sql.Date;
import java.time.LocalDate;

import java.util.List;
public interface AccountsDAO extends CommonDAO<Accounts, Long>{
    List<Accounts> getByFilter(AccountsDAO.Filter filter);
    @Getter
    @AllArgsConstructor
    class Filter {
        private Clients client_id;
        private Account_type acc_type;
        private Float balance;
        private LocalDate credit;
        private Float percent;
        private Integer interval;
        private Integer period;
    }

    List<Clients> findClientOfAccount(Accounts account);}
