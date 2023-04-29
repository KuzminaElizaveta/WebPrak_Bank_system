package com.WebPrak.demo.DAO;



import com.WebPrak.demo.tables.Departments;
import lombok.AllArgsConstructor;

import lombok.Getter;


import java.util.List;
public interface DepartmentDAO extends CommonDAO<Departments, Long>{
    List<Departments> getByFilter(DepartmentDAO.Filter filter);

    @Getter
    @AllArgsConstructor
    class Filter {
        private String name;
        private String address;
        private String phone;
        private String email;
        private Integer staff_count;
    }}
