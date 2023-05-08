package com.WebPrak.demo.DAO.Implement;

import com.WebPrak.demo.DAO.Account_typeDAO;
import com.WebPrak.demo.tables.*;
import com.WebPrak.demo.tables.Client_type;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Account_typeDAOImplement extends CommonDAOImplement<Account_type, Long> implements Account_typeDAO{

    public Account_typeDAOImplement(){
        super(Account_type.class);
    }


    @Override
    public List<Account_type> getAllByType(String typeName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Account_type> query = session.createQuery("FROM Account_type WHERE type LIKE :type", Account_type.class)
                    .setParameter("type", likeExpr(typeName));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public Account_type getByType(String type) {
        List<Account_type> types = this.getAllByType(type);
        return types == null ? null :
                types.size() == 1 ? types.get(0) : null;
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }

}
