package com.WebPrak.demo.DAO;


import com.WebPrak.demo.DAO.Implement.DepartmentDAOImplement;
import com.WebPrak.demo.tables.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class DepartmentDAOTest {

    @Autowired
    private DepartmentDAOImplement departmentDAO;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {

        List<Departments> depListAll = (List<Departments>) departmentDAO.getAll();
        assertEquals(4, depListAll.size());

        Departments noname2 = departmentDAO.getById(3L);
        assertEquals(3, noname2.getId());
        assertNotNull(noname2.getName());
        assertNotNull(noname2.getAddress());
        assertNotNull(noname2.getPhone());
        assertNotNull(noname2.getEmail());
        assertNotNull(noname2.getStaff_count());
        assertEquals(0, noname2.getStuff_in_departments().size());
        assertEquals(0, noname2.getOperations().size());

        Departments depNotExist = departmentDAO.getById(666L);
        assertNull(depNotExist);

        DepartmentDAO.Filter f = new DepartmentDAO.Filter("not", "not", "not", "not", 200);
        List<Departments> l = departmentDAO.getByFilter(f);
        assertEquals(0, l.size());

        DepartmentDAO.Filter f2 = new DepartmentDAO.Filter( "Dep_1", "adress", "phone", "email", 50);
        List<Departments> l2 = departmentDAO.getByFilter(f2);
        assertEquals(1, l2.size());

        Departments Dep_11  = new Departments( "Dep_11", "adress", "phone", "email", 50);
        Departments Dep_12  = new Departments( "Dep_11", "adress", "phone", "email", 50);

        assertEquals(Boolean.TRUE, Dep_11.equals(Dep_12));
    }

    @Test
    void testUpdate() {
        Integer count = 100500;
        Departments dep = departmentDAO.getById(2L);
        dep.setStaff_count(count);
        departmentDAO.update(dep);

        Departments test = departmentDAO.getById(2L);
        assertEquals(count, test.getStaff_count());
    }

    @Test
    void testDelete() {
        Departments deleteDep = departmentDAO.getById(4L);
        departmentDAO.delete(deleteDep);

        Departments test = departmentDAO.getById(4L);
        assertNull(test);

        departmentDAO.deleteById(3L);
        Departments deleteDepId = departmentDAO.getById(3L);
        assertNull(deleteDepId);

    }


    @BeforeEach
    void beforeEach() {
        List<Departments> departmentList = new ArrayList<>();

        Departments dep_1 = new Departments("Dep_1", "adress", "phone", "email", 50);
        departmentList.add(dep_1);
        Departments dep_2 = new Departments("Dep_2", "adress", "phone", "email", 50);
        departmentList.add(dep_2);
        Departments dep_3 = new Departments("Dep_3", "adress", "phone", "email", 50);
        departmentList.add(dep_3);
        Departments dep_4 = new Departments("Dep_4", "adress", "phone", "email", 50);
        departmentList.add(dep_4);
        departmentDAO.saveCollection(departmentList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            NativeQuery query1 = session.createNativeQuery("TRUNCATE department RESTART IDENTITY CASCADE;");
            query1.executeUpdate();
            NativeQuery query2 = session.createNativeQuery("ALTER SEQUENCE department_department_id_seq RESTART WITH 1;");
            query2.executeUpdate();
            session.getTransaction().commit();
        }
    }
}
