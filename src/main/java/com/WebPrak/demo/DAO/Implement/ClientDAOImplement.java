package com.WebPrak.demo.DAO.Implement;


import ch.qos.logback.core.net.server.Client;
import com.WebPrak.demo.DAO.ClientDAO;
import com.WebPrak.demo.tables.Accounts;
import com.WebPrak.demo.tables.Client_type;
import com.WebPrak.demo.tables.Clients;
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
public class ClientDAOImplement extends CommonDAOImplement<Clients, Long> implements ClientDAO {

    public ClientDAOImplement(){
        super(Clients.class);
    }

    @Override
    public List<Clients> getAllByName(String Name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Clients> query = session.createQuery("FROM Clients WHERE fullname LIKE :name", Clients.class)
                    .setParameter("name", likeExpr(Name));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public Clients getByName(String name) {
        List<Clients> types = this.getAllByName(name);
        return types == null ? null :
                types.size() == 1 ? types.get(0) : null;
    }





    public List<Accounts> findAllAccountOfClient(Clients client){
        try {
            Session session = sessionFactory.openSession();
            Transaction tx1 = session.beginTransaction();
            Query<Accounts> query = session.createQuery
                    ("from Accounts as account" +
                            " where account.client_id.id = :client",
                            Accounts.class);
            query.setParameter("client", client.getId());
            List<Accounts> accounts = query.getResultList();
            tx1.commit();
            session.close();
            return accounts;
        }
        catch (Exception e){
            System.out.println("findAllAccountOfClient exception thrown: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Clients> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Clients> criteriaQuery = builder.createQuery(Clients.class);
            Root<Clients> root = criteriaQuery.from(Clients.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getFullname() != null)
                predicates.add(builder.like(root.get("fullname"), likeExpr(filter.getFullname())));

            if (filter.getAddress() != null)
                predicates.add(builder.like(root.get("address"), likeExpr(filter.getAddress())));

            if (filter.getPhone() != null)
                predicates.add(builder.like(root.get("phone"), likeExpr(filter.getPhone())));

            if (filter.getEmail() != null)
                predicates.add(builder.like(root.get("email"), likeExpr(filter.getEmail())));

            if (filter.getType_id() != null)
                predicates.add(root.get("type_id").in(filter.getType_id()));


            if (filter.getBirthday() != null)
                predicates.add(builder.equal(root.get("birthday"), filter.getBirthday()));


            if (predicates.size() != 0)
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}
