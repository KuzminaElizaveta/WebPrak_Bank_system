package com.WebPrak.demo.DAO.Implement;

import com.WebPrak.demo.DAO.ClientDAO;
import com.WebPrak.demo.DAO.StuffDAO;
import com.WebPrak.demo.tables.Accounts;
import com.WebPrak.demo.tables.Clients;
import com.WebPrak.demo.tables.Departments;
import com.WebPrak.demo.tables.Stuff;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StuffDAOImplement extends CommonDAOImplement<Stuff, Long> implements StuffDAO {

    public StuffDAOImplement(){
        super(Stuff.class);
    }

    @Override
    public List<Stuff> getByFilter(StuffDAO.Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Stuff> criteriaQuery = builder.createQuery(Stuff.class);
            Root<Stuff> root = criteriaQuery.from(Stuff.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getFullname() != null)
                predicates.add(builder.like(root.get("fullname"), likeExpr(filter.getFullname())));

            if (filter.getAddress() != null)
                predicates.add(builder.like(root.get("address"), likeExpr(filter.getAddress())));

            if (filter.getPhone() != null)
                predicates.add(builder.like(root.get("phone"), likeExpr(filter.getPhone())));

            if (filter.getEmail() != null)
                predicates.add(builder.like(root.get("email"), likeExpr(filter.getEmail())));

            if (filter.getLogin() != null)
                predicates.add(builder.like(root.get("login"), likeExpr(filter.getLogin())));


            if (predicates.size() != 0)
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }

    @Override
    public Stuff authorizeStuff(String login, String password) {
        try{
            Session session = sessionFactory.openSession();
            Transaction tx1 = session.beginTransaction();
            Query<Stuff> query = session.createQuery("From Stuff WHERE login = :param", Stuff.class);
            query.setParameter("param", login);
            List<Stuff> stuff = query.getResultList();
            tx1.commit();
            session.close();
            if (stuff.isEmpty())
                return null;
            try{
                Stuff st = stuff.get(0);
                if(st.getPassword().equals(password))
                {
                    return st;
                }
                else{
                    System.out.println("Failed stuff authorization");
                    return null;
                }
            }
            catch(Exception e){
                System.out.println("StuffsAuthorizeStuff with login exception thrown: " + e.getMessage());
                return null;
            }
        }
        catch(Exception ex){
            System.out.println("StuffAuthorizeStuff exception thrown: " + ex.getMessage());
            return null;
        }
    }

}
