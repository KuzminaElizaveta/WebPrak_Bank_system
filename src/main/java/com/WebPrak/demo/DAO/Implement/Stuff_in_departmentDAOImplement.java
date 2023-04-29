package com.WebPrak.demo.DAO.Implement;

import com.WebPrak.demo.DAO.Stuff_in_departmentDAO;
import com.WebPrak.demo.tables.Stuff_in_department;
import org.springframework.stereotype.Repository;

@Repository
public class Stuff_in_departmentDAOImplement extends CommonDAOImplement<Stuff_in_department, Long> implements Stuff_in_departmentDAO{

    public Stuff_in_departmentDAOImplement(){
        super(Stuff_in_department.class);
    }
}
