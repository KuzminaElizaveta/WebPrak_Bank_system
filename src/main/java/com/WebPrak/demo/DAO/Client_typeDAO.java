package com.WebPrak.demo.DAO;


import com.WebPrak.demo.tables.Client_type;
import com.WebPrak.demo.tables.Clients;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Date;
import java.util.List;

public interface Client_typeDAO extends CommonDAO<Client_type, Long> {

    Client_type getByFilter(Client_typeDAO.Filter filter);
    @Getter
    @AllArgsConstructor
    @Builder
    class Filter {
        private String type;
    }

    static Client_typeDAO.Filter.FilterBuilder getFilterBuilder() {
        return Client_typeDAO.Filter.builder();
    }
}
