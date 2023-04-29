package com.WebPrak.demo.DAO.Implement;

import com.WebPrak.demo.DAO.DepartmentDAO;
import com.WebPrak.demo.tables.Departments;
import ch.qos.logback.core.net.server.Client;
import com.WebPrak.demo.DAO.ClientDAO;
import com.WebPrak.demo.tables.Accounts;
import com.WebPrak.demo.tables.Clients;
import com.WebPrak.demo.tables.Operations;
import com.WebPrak.demo.DAO.OperationsDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OperationsDAOImplement extends CommonDAOImplement<Operations, Long> implements OperationsDAO{

    public OperationsDAOImplement(){
        super(Operations.class);
    }

    @Override
    public List<Operations> getByFilter(OperationsDAO.Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Operations> criteriaQuery = builder.createQuery(Operations.class);
            Root<Operations> root = criteriaQuery.from(Operations.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getDate() != null)
                predicates.add(builder.equal(root.get("date"), filter.getDate()));

            if (filter.getAmount() != null)
                predicates.add(builder.equal(root.get("amount"), filter.getAmount()));

            if (predicates.size() != 0)
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

}
