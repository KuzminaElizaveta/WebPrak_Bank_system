package com.WebPrak.demo.DAO.Implement;

import com.WebPrak.demo.DAO.Operation_typeDAO;
import com.WebPrak.demo.tables.Client_type;
import com.WebPrak.demo.tables.Operation_type;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Operation_typeDAOImplement extends CommonDAOImplement<Operation_type, Long> implements Operation_typeDAO{

    public Operation_typeDAOImplement(){
        super(Operation_type.class);
    }

    @Override
    public List<Operation_type> getAllByType(String typeName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Operation_type> query = session.createQuery("FROM Operation_type WHERE type LIKE :type", Operation_type.class)
                    .setParameter("type", likeExpr(typeName));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public Operation_type getByType(String type) {
        List<Operation_type> types = this.getAllByType(type);
        return types == null ? null :
                types.size() == 1 ? types.get(0) : null;
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}
