package com.WebPrak.demo.DAO;


import com.WebPrak.demo.tables.Accounts;
import com.WebPrak.demo.tables.Departments;
import com.WebPrak.demo.tables.Operation_type;
import com.WebPrak.demo.tables.Operations;
import lombok.AllArgsConstructor;

import lombok.Getter;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
public interface OperationsDAO extends CommonDAO<Operations, Long>{
    List<Operations> getByFilter(OperationsDAO.Filter filter);

    @Getter
    @AllArgsConstructor
    class Filter {
        private Accounts account;
        private Departments department;
        private Operation_type type;
        private LocalDate date;
        private Float amount;
    }

}
