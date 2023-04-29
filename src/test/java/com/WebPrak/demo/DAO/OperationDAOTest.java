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


import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class OperationDAOTest {
    @Autowired
    private ClientDAOImplement clientDAO;

    @Autowired
    private Client_typeDAOImplement client_typeDAO;

    @Autowired
    private DepartmentDAOImplement departmentDAO;

    @Autowired
    private AccountsDAOImplement accountDAO;

    @Autowired
    private Account_typeDAOImplement acc_typeDAO;

    @Autowired
    private OperationsDAOImplement operationDAO;

    @Autowired
    private Operation_typeDAOImplement operation_typeDAO;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {

        List<Operations> opListAll = (List<Operations>) operationDAO.getAll();
        assertEquals(4, opListAll.size());
        Timestamp date_cool = Timestamp.valueOf(LocalDateTime.parse("12-11-2018 15:22:35", dateTimeFormatter));
        Timestamp date_bad = Timestamp.valueOf(LocalDateTime.parse("12-11-2010 15:22:35", dateTimeFormatter));

        Operations noname2 = operationDAO.getById(3L);
        assertEquals(3, noname2.getId());
        assertNotNull(noname2.getAccount_id());
        assertNotNull(noname2.getDepartment_id());
        assertNotNull(noname2.getOp_type_id());
        assertNotNull(noname2.getDate());
        assertNotNull(noname2.getAmount());


        Operations opNotExist = operationDAO.getById(666L);
        assertNull(opNotExist);

        OperationsDAO.Filter f = new OperationsDAO.Filter(date_bad, 12F);
        List<Operations> l = operationDAO.getByFilter(f);
        assertEquals(0, l.size());

        OperationsDAO.Filter f2 = new OperationsDAO.Filter( date_cool, 42.5F);
        List<Operations> l2 = operationDAO.getByFilter(f2);
        assertEquals(3, l2.size());
    }

    @Test
    void testUpdate() {
        Float amount = 123456F;
        Operations op = operationDAO.getById(2L);
        op.setAmount(amount);
        operationDAO.update(op);

        Operations test = operationDAO.getById(2L);
        assertEquals(amount, test.getAmount());
    }

    @Test
    void testDelete() {
        Operations deleteOp = operationDAO.getById(4L);
        operationDAO.delete(deleteOp);

        Operations test = operationDAO.getById(4L);
        assertNull(test);

        operationDAO.deleteById(3L);
        Operations deleteOpId = operationDAO.getById(3L);
        assertNull(deleteOpId);

    }


    @BeforeEach
    void beforeEach() {

        List<Accounts> accountList = new ArrayList<>();
        Date date = Date.valueOf(LocalDate.parse("11-12-2010", dateFormatter));

        List<Clients> personList = new ArrayList<>();
        Date birth = Date.valueOf(LocalDate.parse("12-11-2018", dateFormatter));

        Client_type person = new Client_type( "Person");
        client_typeDAO.save(person);
        Client_type entity= new Client_type( "Entity");
        client_typeDAO.save(entity);
        Clients person_1 = new Clients("Кто-то Там 1", "Street 1", "+79200526042", "email", person, birth);
        personList.add(person_1);
        Clients person_2 = new Clients( "Кто-то Там 2", "Street 2", "+79200526042", "email", person, birth);
        personList.add(person_2);
        Clients person_3 = new Clients("Кто-то Там 3", "Street 3", "+79200526042", "email", entity, birth);
        personList.add(person_3);
        clientDAO.saveCollection(personList);

        Account_type credit = new Account_type( "Credit");
        acc_typeDAO.save(credit);
        Account_type checking = new Account_type( "Checking");
        acc_typeDAO.save(checking);
        Accounts account_1 = new Accounts(person_1, credit, 25.0F, date, 2.0F, 2, 5);
        accountList.add(account_1);
        Accounts account_2 = new Accounts(person_2, credit, 25.0F, date, 2.0F, 2, 5);
        accountList.add(account_2);
        Accounts account_3 = new Accounts(person_3, credit, 25.0F, date, 2.0F, 2, 5);
        accountList.add(account_3);
        Accounts account_4 = new Accounts(person_1, checking, 25.0F, date, 2.0F, 2, 5);
        accountList.add(account_4);
        accountDAO.saveCollection(accountList);

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

        List<Operations> operationsList = new ArrayList<>();
        List<Operation_type> operation_typeList = new ArrayList<>();
        Timestamp date_op = Timestamp.valueOf(LocalDateTime.parse("12-11-2018 15:22:35", dateTimeFormatter));

        Operation_type debit_type = new Operation_type( "Debit");
        operation_typeDAO.save(debit_type);
        Operation_type credit_type= new Operation_type( "Credit");
        operation_typeDAO.save(credit_type);
        Operations op_1 = new Operations(account_1, dep_1, debit_type, date_op, 42.5F);
        operationsList.add(op_1);
        Operations op_2 = new Operations(account_1, dep_1, credit_type, date_op, 45.5F);
        operationsList.add(op_2);
        Operations op_3 = new Operations(account_2, dep_1, debit_type, date_op, 42.5F);
        operationsList.add(op_3);
        Operations op_4 = new Operations(account_3, dep_1, debit_type, date_op, 42.5F);
        operationsList.add(op_4);
        operationDAO.saveCollection(operationsList);


    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            NativeQuery query1 = session.createNativeQuery("TRUNCATE client RESTART IDENTITY CASCADE;");
            query1.executeUpdate();
            NativeQuery query2 = session.createNativeQuery("ALTER SEQUENCE client_client_id_seq RESTART WITH 1;");
            query2.executeUpdate();
            NativeQuery query3 = session.createNativeQuery("TRUNCATE account RESTART IDENTITY CASCADE;");
            query3.executeUpdate();
            NativeQuery query4 = session.createNativeQuery("ALTER SEQUENCE account_account_id_seq RESTART WITH 1;");
            query4.executeUpdate();
            NativeQuery query5 = session.createNativeQuery("TRUNCATE department RESTART IDENTITY CASCADE;");
            query5.executeUpdate();
            NativeQuery query6 = session.createNativeQuery("ALTER SEQUENCE department_department_id_seq RESTART WITH 1;");
            query6.executeUpdate();
            session.getTransaction().commit();
        }
    }
}
