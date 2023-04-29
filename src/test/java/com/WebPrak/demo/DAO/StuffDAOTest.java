package com.WebPrak.demo.DAO;

import com.WebPrak.demo.DAO.Implement.*;
import com.WebPrak.demo.tables.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;



import java.util.ArrayList;
import java.util.List;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class StuffDAOTest {
    @Autowired
    private Job_titleDAOImplement job_titleDAO;

    @Autowired
    private StuffDAOImplement stuffDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {

        List<Stuff> stuffListAll = (List<Stuff>) stuffDAO.getAll();
        assertEquals(4, stuffListAll.size());

        Stuff noname2 = stuffDAO.getById(3L);
        assertEquals(3, noname2.getId());
        assertNotNull(noname2.getFullname());
        assertNotNull(noname2.getAddress());
        assertNotNull(noname2.getPhone());
        assertNotNull(noname2.getEmail());
        assertNotNull(noname2.getLogin());
        assertNotNull(noname2.getPassword());
        assertNotNull(noname2.getJob_title_id());
        assertNull(noname2.getStuff_in_department());

        Stuff stuffNotExist = stuffDAO.getById(666L);
        assertNull(stuffNotExist);

        StuffDAO.Filter f = new StuffDAO.Filter("not","not", "not", "not","not");
        List<Stuff> l = stuffDAO.getByFilter(f);
        assertEquals(0, l.size());

        StuffDAO.Filter f2 = new StuffDAO.Filter("Кто-то Там 1", "Street 1", "+79200526042", "email",  "login_1");
        List<Stuff> l2 = stuffDAO.getByFilter(f2);
        assertEquals(1, l2.size());

        Stuff stuff1 = stuffDAO.getById(1L);
        Stuff stuff2 = stuffDAO.getById(1L);
        assertNotNull(stuff1.equals(stuff2));
    }

    @Test
    void testUpdate() {
        String phone = "+78000000000";
        Stuff updateStuff = stuffDAO.getById(2L);
        updateStuff.setPhone(phone);
        stuffDAO.update(updateStuff);

        Stuff test = stuffDAO.getById(2L);
        assertEquals(phone, test.getPhone());
    }

    @Test
    void testDelete() {
        Stuff deleteStuff = stuffDAO.getById(4L);
        stuffDAO.delete(deleteStuff);

        Stuff test = stuffDAO.getById(4L);
        assertNull(test);

        stuffDAO.deleteById(3L);
        Stuff deleteStuffId = stuffDAO.getById(3L);
        assertNull(deleteStuffId);

    }

    @Test
    void testAutorize() {
        Stuff st = stuffDAO.authorizeStuff("login_1", "password");
        assertNotNull(st);
    }


    @BeforeEach
    void beforeEach() {
        List<Stuff> staffList = new ArrayList<>();

        Job_titles senior = new Job_titles("senior");
        job_titleDAO.save(senior);
        Job_titles junior = new Job_titles("junior");
        job_titleDAO.save(junior);
        Stuff stuff_1 = new Stuff("Кто-то Там 1", "Street 1", "+79200526042", "email", senior, "login_1", "password");
        staffList.add(stuff_1);
        Stuff stuff_2 = new Stuff("Кто-то Там 2", "Street 1", "+79200526042", "email", junior, "login_2", "password");
        staffList.add(stuff_2);
        Stuff stuff_3 = new Stuff("Кто-то Там 3", "Street 1", "+79200526042", "email", senior, "login_3", "password");
        staffList.add(stuff_3);
        Stuff stuff_4 = new Stuff("Кто-то Там 4", "Street 1", "+79200526042", "email", senior, "login_4", "password");
        staffList.add(stuff_4);
        stuffDAO.saveCollection(staffList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            NativeQuery query1 = session.createNativeQuery("TRUNCATE stuff RESTART IDENTITY CASCADE;");
            query1.executeUpdate();
            NativeQuery query2 = session.createNativeQuery("ALTER SEQUENCE stuff_stuff_id_seq RESTART WITH 1;");
            query2.executeUpdate();
            session.getTransaction().commit();
        }
    }

}
