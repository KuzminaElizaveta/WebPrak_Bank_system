package com.WebPrak.demo.DAO.Implement;

import com.WebPrak.demo.DAO.Client_typeDAO;
import com.WebPrak.demo.tables.Client_type;
import org.springframework.stereotype.Repository;

@Repository
public class Client_typeDAOImplement extends CommonDAOImplement<Client_type, Long> implements Client_typeDAO{

    public Client_typeDAOImplement(){
        super(Client_type.class);
    }
}
