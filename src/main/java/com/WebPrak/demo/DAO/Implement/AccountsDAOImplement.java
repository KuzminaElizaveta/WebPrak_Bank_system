package com.WebPrak.demo.DAO.Implement;

import ch.qos.logback.core.net.server.Client;
import com.WebPrak.demo.DAO.AccountsDAO;
import com.WebPrak.demo.DAO.ClientDAO;
import com.WebPrak.demo.tables.Accounts;
import com.WebPrak.demo.tables.Clients;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountsDAOImplement extends CommonDAOImplement<Accounts, Long> implements AccountsDAO {

    public AccountsDAOImplement(){
        super(Accounts.class);
    }

    @Override
    public List<Accounts> getByFilter(AccountsDAO.Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Accounts> criteriaQuery = builder.createQuery(Accounts.class);
            Root<Accounts> root = criteriaQuery.from(Accounts.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getBalance() != null)
                predicates.add(builder.equal(root.get("balance"), filter.getBalance()));

            if (filter.getCredit() != null)
                predicates.add(builder.equal(root.get("credit"), filter.getCredit()));

            if (filter.getPercent() != null)
                predicates.add(builder.equal(root.get("percent"), filter.getPercent()));

            if (filter.getInterval() != null)
                predicates.add(builder.equal(root.get("interval"), filter.getInterval()));

            if (filter.getPeriod() != null)
                predicates.add(builder.equal(root.get("period"), filter.getPeriod()));

            if (predicates.size() != 0)
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
    public List<Clients> findClientOfAccount(Accounts account){
        try {
            Session session = sessionFactory.openSession();
            Transaction tx1 = session.beginTransaction();
            Query<Clients> query = session.createQuery
                    ("from Clients as client" + " where client.id = :client", Clients.class);
            query.setParameter("client", account.getClient_id().getId());
            List<Clients> client = query.getResultList();
            tx1.commit();
            session.close();
            return client;
        }
        catch (Exception e){
            System.out.println("findAllAccountOfClient exception thrown: " + e.getMessage());
            return null;
        }
    }
}
