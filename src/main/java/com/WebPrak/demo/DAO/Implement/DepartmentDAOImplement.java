package com.WebPrak.demo.DAO.Implement;

import com.WebPrak.demo.DAO.DepartmentDAO;
import com.WebPrak.demo.tables.Departments;
import ch.qos.logback.core.net.server.Client;
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
import java.util.ArrayList;
import java.util.List;

@Repository
public class DepartmentDAOImplement extends CommonDAOImplement<Departments, Long> implements DepartmentDAO{

    public DepartmentDAOImplement(){
        super(Departments.class);
    }

    @Override
    public List<Departments> getByFilter(DepartmentDAO.Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Departments> criteriaQuery = builder.createQuery(Departments.class);
            Root<Departments> root = criteriaQuery.from(Departments.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getName() != null)
                predicates.add(builder.like(root.get("name"), likeExpr(filter.getName())));

            if (filter.getAddress() != null)
                predicates.add(builder.like(root.get("address"), likeExpr(filter.getAddress())));

            if (filter.getPhone() != null)
                predicates.add(builder.like(root.get("phone"), likeExpr(filter.getPhone())));

            if (filter.getEmail() != null)
                predicates.add(builder.like(root.get("email"), likeExpr(filter.getEmail())));

            if (filter.getStaff_count() != null)
                predicates.add(builder.equal(root.get("staff_count"), filter.getStaff_count()));


            if (predicates.size() != 0)
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }

}
