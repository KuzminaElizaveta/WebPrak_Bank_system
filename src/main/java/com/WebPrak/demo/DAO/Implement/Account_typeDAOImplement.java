package com.WebPrak.demo.DAO.Implement;

import com.WebPrak.demo.DAO.Account_typeDAO;
import com.WebPrak.demo.tables.Account_type;
import org.springframework.stereotype.Repository;

@Repository
public class Account_typeDAOImplement extends CommonDAOImplement<Account_type, Long> implements Account_typeDAO{

    public Account_typeDAOImplement(){
        super(Account_type.class);
    }
}
