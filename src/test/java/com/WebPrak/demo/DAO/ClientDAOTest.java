package com.WebPrak.demo.DAO;

import com.WebPrak.demo.DAO.Implement.Account_typeDAOImplement;
import com.WebPrak.demo.DAO.Implement.AccountsDAOImplement;
import com.WebPrak.demo.DAO.Implement.ClientDAOImplement;
import com.WebPrak.demo.DAO.Implement.Client_typeDAOImplement;
import com.WebPrak.demo.tables.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class ClientDAOTest {

    @Autowired
    private ClientDAOImplement clientDAO;

    @Autowired
    private Client_typeDAOImplement client_typeDAO;

    @Autowired
    private Account_typeDAOImplement acc_typeDAO;

    @Autowired
    private AccountsDAOImplement accountDAO;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {

        Date date = Date.valueOf(LocalDate.parse("12-11-2011", dateFormatter));
        Date date_cool = Date.valueOf(LocalDate.parse("12-11-2018", dateFormatter));

        List<Clients> personListAll = (List<Clients>) clientDAO.getAll();
        assertEquals(4, personListAll.size());

        Clients noname2 = clientDAO.getById(3L);
        assertEquals(3, noname2.getId());
        assertNotNull(noname2.getFullname());
        assertNotNull(noname2.getAddress());
        assertNotNull(noname2.getPhone());
        assertNotNull(noname2.getEmail());
        assertNotNull(noname2.getType_id());
        assertNotNull(noname2.getBirthday());
        assertNotNull(noname2.getAccounts());

        Clients personNotExist = clientDAO.getById(666L);
        assertNull(personNotExist);

        ClientDAO.Filter f = new ClientDAO.Filter("not", "not", "not", "not", date);
        List<Clients> l = clientDAO.getByFilter(f);
        assertEquals(0, l.size());

        ClientDAO.Filter f2 = new ClientDAO.Filter( "Кто-то Там 1", "Street 1", "+79200526042", "email", date_cool);
        List<Clients> l2 = clientDAO.getByFilter(f2);
        assertEquals(1, l2.size());


    }

    @Test
    void testUpdate() {
        String phone = "+78000000000";
        Clients updatePerson = clientDAO.getById(2L);
        updatePerson.setPhone(phone);
        clientDAO.update(updatePerson);

        Clients test = clientDAO.getById(2L);
        assertEquals(phone, test.getPhone());
    }

    @Test
    void testDelete() {
        Clients deletePerson = clientDAO.getById(4L);
        clientDAO.delete(deletePerson);

        Clients test = clientDAO.getById(4L);
        assertNull(test);

        clientDAO.deleteById(3L);
        Clients deletePersonId = clientDAO.getById(3L);
        assertNull(deletePersonId);

    }

    @Test
    void testAccountsOfClients() {
        Clients testClient = clientDAO.getById(1L);

        List<Accounts> result = clientDAO.findAllAccountOfClient(testClient);
        assertEquals(2, result.size());

        Clients testClient_bad = clientDAO.getById(111L);
        List<Accounts> result_bad = clientDAO.findAllAccountOfClient(testClient_bad);
        assertEquals(null, result_bad);
    }

    @BeforeEach
    void beforeEach() {
        List<Clients> personList = new ArrayList<>();
        Date birth = Date.valueOf(LocalDate.parse("12-11-2018", dateFormatter));
        List<Accounts> accountList = new ArrayList<>();
        Date date = Date.valueOf(LocalDate.parse("11-12-2010", dateFormatter));

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
        Clients person_4 = new Clients( "Кто-то Там 4", "Street 4", "+79200526042", "email", entity, birth);
        personList.add(person_4);
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
            session.getTransaction().commit();
        }
    }
}
