package com.WebPrak.demo.DAO.Implement;

import com.WebPrak.demo.DAO.Operation_typeDAO;
import com.WebPrak.demo.tables.Operation_type;
import org.springframework.stereotype.Repository;

@Repository
public class Operation_typeDAOImplement extends CommonDAOImplement<Operation_type, Long> implements Operation_typeDAO{

    public Operation_typeDAOImplement(){
        super(Operation_type.class);
    }
}
