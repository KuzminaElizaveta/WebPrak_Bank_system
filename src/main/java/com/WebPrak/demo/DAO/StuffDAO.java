package com.WebPrak.demo.DAO;


import com.WebPrak.demo.tables.Stuff;
import lombok.AllArgsConstructor;

import lombok.Getter;


import java.util.List;

public interface StuffDAO extends CommonDAO<Stuff, Long> {
    List<Stuff> getByFilter(StuffDAO.Filter filter);

    @Getter
    @AllArgsConstructor
    class Filter {
        private String fullname;
        private String address;
        private String phone;
        private String email;
        private String login;
    }
    Stuff authorizeStuff(String login, String password);
}
