package com.WebPrak.demo.DAO;


import com.WebPrak.demo.tables.Operations;
import lombok.AllArgsConstructor;

import lombok.Getter;


import java.sql.Timestamp;
import java.util.List;
public interface OperationsDAO extends CommonDAO<Operations, Long>{
    List<Operations> getByFilter(OperationsDAO.Filter filter);

    @Getter
    @AllArgsConstructor
    class Filter {
        private Timestamp date;
        private Float amount;
    }

}
