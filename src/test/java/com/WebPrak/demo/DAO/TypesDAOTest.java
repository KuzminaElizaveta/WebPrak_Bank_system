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
public class TypesDAOTest {
    @Autowired
    private Job_titleDAOImplement job_titleDAO;

    @Autowired
    private Operation_typeDAOImplement op_typeDAO;

    @Autowired
    private Client_typeDAOImplement client_typeDAO;

    @Autowired
    private Account_typeDAOImplement ac_typeDAO;

    @Autowired
    private Stuff_in_departmentDAOImplement st_typeDAO;

    @Autowired
    private DepartmentDAOImplement departmentDAO;

    @Autowired
    private StuffDAOImplement stuffDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulationsJob_titles() {

        List<Job_titles> ListAll = (List<Job_titles>) job_titleDAO.getAll();
        assertEquals(2, ListAll.size());

        Job_titles noname2 = job_titleDAO.getById(1L);
        assertEquals(1, noname2.getId());
        assertNotNull(noname2.getJob_title());
        assertNotNull(noname2.getStuff());

        String title = "qwe";
        Job_titles updateTitle = job_titleDAO.getById(1L);
        updateTitle.setJob_title(title);
        job_titleDAO.update(updateTitle);

        Job_titles test = job_titleDAO.getById(1L);
        assertEquals(title, test.getJob_title());

    }

    @Test
    void testSimpleManipulationsOp_types() {

        List<Operation_type> ListAll = (List<Operation_type>) op_typeDAO.getAll();
        assertEquals(2, ListAll.size());

        Operation_type noname2 = op_typeDAO.getById(1L);
        Operation_type noname3 = op_typeDAO.getById(1L);
        assertEquals(1, noname2.getId());
        assertNotNull(noname2.getOperations());
        assertNotNull(noname2.getType());
        assertNotNull(noname2.equals(noname3));

        String type = "qwe";
        Operation_type updateType = op_typeDAO.getById(1L);
        updateType.setType(type);
        op_typeDAO.update(updateType);

        Operation_type test = op_typeDAO.getById(1L);
        assertEquals(type, test.getType());
    }


    @Test
    void testSimpleManipulationsClient_types() {

        List<Client_type> ListAll = (List<Client_type>) client_typeDAO.getAll();
        assertEquals(2, ListAll.size());

        Client_type noname2 = client_typeDAO.getById(1L);
        assertEquals(1, noname2.getId());
        assertNotNull(noname2.getClients());
        assertNotNull(noname2.getType());

        String type = "qwe";
        Client_type updateType = client_typeDAO.getById(1L);
        updateType.setType(type);
        client_typeDAO.update(updateType);

        Client_type test = client_typeDAO.getById(1L);
        assertEquals(type, test.getType());
    }

    @Test
    void testSimpleManipulationsAcc_types() {

        List<Account_type> ListAll = (List<Account_type>) ac_typeDAO.getAll();
        assertEquals(2, ListAll.size());

        Account_type noname2 = ac_typeDAO.getById(1L);
        assertEquals(1, noname2.getId());
        assertNotNull(noname2.getAccounts());
        assertNotNull(noname2.getType());

        String type = "qwe";
        Account_type updateType = ac_typeDAO.getById(1L);
        updateType.setType(type);
        ac_typeDAO.update(updateType);

        Account_type test = ac_typeDAO.getById(1L);
        assertEquals(type, test.getType());
    }

    @Test
    void testSimpleManipulationsStuff_in_department() {

        List<Stuff_in_department> ListAll = (List<Stuff_in_department>) st_typeDAO.getAll();
        assertEquals(2, ListAll.size());

        Stuff_in_department noname2 = st_typeDAO.getById(1L);
        Stuff_in_department noname3 = st_typeDAO.getById(1L);
        assertEquals(1, noname2.getId());
        assertNotNull(noname2.getStuff_id());
        assertNotNull(noname2.getDepartment_id());
        assertNotNull(noname2.equals(noname3));

        Departments dep_4 = new Departments("Dep_4", "adress", "phone", "email", 50);
        departmentDAO.save(dep_4);

        Stuff_in_department updateSt = st_typeDAO.getById(1L);
        updateSt.setDepartment_id(dep_4);
        st_typeDAO.update(updateSt);

        Stuff_in_department test = st_typeDAO.getById(1L);
        assertEquals(dep_4, test.getDepartment_id());
    }


    @BeforeEach
    void beforeEach() {
        Job_titles senior = new Job_titles("senior");
        job_titleDAO.save(senior);
        Job_titles junior = new Job_titles("junior");
        job_titleDAO.save(junior);

        Operation_type credit = new Operation_type("credit");
        op_typeDAO.save(credit);
        Operation_type debit = new Operation_type("debit");
        op_typeDAO.save(debit);

        Client_type person = new Client_type("person");
        client_typeDAO.save(person);
        Client_type entity = new Client_type("entity");
        client_typeDAO.save(entity);

        Account_type ac1 = new Account_type("ac1");
        ac_typeDAO.save(ac1);
        Account_type ac2 = new Account_type("ac2");
        ac_typeDAO.save(ac2);

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

        Stuff stuff_1 = new Stuff("Кто-то Там 1", "Street 1", "+79200526042", "email", senior, "login_1", "password");
        stuffDAO.save(stuff_1);
        Stuff stuff_2 = new Stuff("Кто-то Там 2", "Street 1", "+79200526042", "email", junior, "login_2", "password");
        stuffDAO.save(stuff_2);

        Stuff_in_department st1 = new Stuff_in_department(dep_1, stuff_1);
        st_typeDAO.save(st1);
        Stuff_in_department st2 = new Stuff_in_department(dep_1, stuff_2);
        st_typeDAO.save(st2);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            NativeQuery query1 = session.createNativeQuery("TRUNCATE job_title RESTART IDENTITY CASCADE;");
            query1.executeUpdate();
            NativeQuery query2 = session.createNativeQuery("ALTER SEQUENCE job_title_job_title_id_seq RESTART WITH 1;");
            query2.executeUpdate();
            NativeQuery query3 = session.createNativeQuery("TRUNCATE op_type RESTART IDENTITY CASCADE;");
            query3.executeUpdate();
            NativeQuery query4 = session.createNativeQuery("ALTER SEQUENCE op_type_op_type_id_seq RESTART WITH 1;");
            query4.executeUpdate();
            NativeQuery query5 = session.createNativeQuery("TRUNCATE type RESTART IDENTITY CASCADE;");
            query5.executeUpdate();
            NativeQuery query6 = session.createNativeQuery("ALTER SEQUENCE type_type_id_seq RESTART WITH 1;");
            query6.executeUpdate();
            NativeQuery query7 = session.createNativeQuery("TRUNCATE acc_type RESTART IDENTITY CASCADE;");
            query7.executeUpdate();
            NativeQuery query8 = session.createNativeQuery("ALTER SEQUENCE acc_type_acc_type_id_seq RESTART WITH 1;");
            query8.executeUpdate();
            NativeQuery query9 = session.createNativeQuery("TRUNCATE stuff_in_department RESTART IDENTITY CASCADE;");
            query9.executeUpdate();
            NativeQuery query10 = session.createNativeQuery("ALTER SEQUENCE stuff_in_department_id_seq RESTART WITH 1;");
            query10.executeUpdate();
            session.getTransaction().commit();
        }
    }
}
