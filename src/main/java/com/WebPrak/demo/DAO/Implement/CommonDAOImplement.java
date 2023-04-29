package com.WebPrak.demo.DAO.Implement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import com.WebPrak.demo.DAO.CommonDAO;
import com.WebPrak.demo.tables.CommonEntity;

import jakarta.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.Collection;

@Repository
public abstract class CommonDAOImplement <T extends CommonEntity<ID>, ID extends Serializable> implements CommonDAO<T, ID> {
    protected SessionFactory sessionFactory;

    protected Class<T> persistentClass;

    public CommonDAOImplement(Class<T> entityClass) {
        this.persistentClass = entityClass;
    }

    @Autowired
    public void setSessionFactory(LocalSessionFactoryBean sessionFactory) {
        this.sessionFactory = sessionFactory.getObject();
    }

    @Override
    public T getById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(persistentClass, id);
        }
        catch (Exception e) {
            System.out.println("getById Exception thrown: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Collection<T> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<T> criteriaQuery = session.getCriteriaBuilder().createQuery(persistentClass);
            criteriaQuery.from(persistentClass);
            return session.createQuery(criteriaQuery).getResultList();
        }
        catch (Exception e) {
            System.out.println("getAll Exception thrown: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void save(T entity) {
        try (Session session = sessionFactory.openSession()) {
            if (entity.getId() != null) {
                entity.setId(null);
            }
            session.beginTransaction();
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
            session.close();
        }
        catch (Exception e) {
            System.out.println("save Exception thrown: " + e.getMessage());
        }
    }

    @Override
    public void saveCollection(Collection<T> entities) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (T entity : entities) {
                this.save(entity);
            }
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("saveCollection Exception thrown: " + e.getMessage());
        }
    }

    @Override
    public void update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("update Exception thrown: " + e.getMessage());
        }
    }

    @Override
    public void delete(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("delete Exception thrown: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            T entity = getById(id);
            session.delete(entity);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("deleteById Exception thrown: " + e.getMessage());
        }
    }



}

