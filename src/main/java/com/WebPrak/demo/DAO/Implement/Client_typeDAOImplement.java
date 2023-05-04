package com.WebPrak.demo.DAO.Implement;

import com.WebPrak.demo.DAO.ClientDAO;
import com.WebPrak.demo.DAO.Client_typeDAO;
import com.WebPrak.demo.tables.Client_type;
import com.WebPrak.demo.tables.Clients;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class Client_typeDAOImplement extends CommonDAOImplement<Client_type, Long> implements Client_typeDAO{

    public Client_typeDAOImplement(){
        super(Client_type.class);
    }

    @Override
    public Client_type getByFilter(Client_typeDAO.Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Client_type> criteriaQuery = builder.createQuery(Client_type.class);
            Root<Client_type> root = criteriaQuery.from(Client_type.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getType() != null)
                predicates.add(builder.like(root.get("type"), likeExpr(filter.getType())));

            if (predicates.size() != 0)
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList().get(0);
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}

