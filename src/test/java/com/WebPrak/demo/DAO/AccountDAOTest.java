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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class AccountDAOTest {
    @Autowired
    private ClientDAOImplement clientDAO;

    @Autowired
    private Client_typeDAOImplement client_typeDAO;

    @Autowired
    private AccountsDAOImplement accountDAO;

    @Autowired
    private Account_typeDAOImplement acc_typeDAO;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {

        LocalDate date_bad = LocalDate.parse("12-11-2011", dateFormatter);
        LocalDate date_cool = LocalDate.parse("11-12-2010", dateFormatter);

        List<Accounts> accountListAll = (List<Accounts>) accountDAO.getAll();
        assertEquals(4, accountListAll.size());

        Accounts noname2 = accountDAO.getById(3L);
        assertEquals(3, noname2.getId());
        assertNotNull(noname2.getClient_id());
        assertNotNull(noname2.getAcc_type_id());
        assertNotNull(noname2.getBalance());
        assertNotNull(noname2.getCredit());
        assertNotNull(noname2.getPercent());
        assertNotNull(noname2.getInterval());
        assertNotNull(noname2.getPeriod());
        assertNotNull(noname2.getClients());

        Accounts accountNotExist = accountDAO.getById(666L);
        assertNull(accountNotExist);

       // AccountsDAO.Filter f = new AccountsDAO.Filter(0F, date_bad, 0F, 0, 0);
//        List<Accounts> l = accountDAO.getByFilter(f);
//        assertEquals(0, l.size());
//
//        AccountsDAO.Filter f2 = new AccountsDAO.Filter(25.0F, date_cool, 2.0F, 2, 5);
//        List<Accounts> l2 = accountDAO.getByFilter(f2);
//        assertEquals(4, l2.size());
    }

    @Test
    void testUpdate() {
        Float percent = 25F;
        Accounts updateAccounts = accountDAO.getById(2L);
        updateAccounts.setPercent(percent);
        accountDAO.update(updateAccounts);

        Accounts test = accountDAO.getById(2L);
        assertEquals(percent, test.getPercent());
    }

    @Test
    void testDelete() {
        Accounts deleteAccount = accountDAO.getById(4L);
        accountDAO.delete(deleteAccount);

        Accounts test = accountDAO.getById(4L);
        assertNull(test);
    }

    @Test
    void testClientOfAccount() {

        List<Clients> personList = new ArrayList<>();
        LocalDate birth = LocalDate.parse("12-11-2018", dateFormatter);

        Client_type person = new Client_type( "Person");
        client_typeDAO.save(person);
        Clients person_1 = new Clients("Кто-то Там 1", "Street 1", "+79200526042", "email", person, birth);

        List<Clients> cool = new ArrayList<>();
        Accounts testAcc = accountDAO.getById(1L);


        List<Clients> result = accountDAO.findClientOfAccount(testAcc);
        cool.add(person_1);
        assertEquals(cool.get(0).getFullname(), result.get(0).getFullname());

        Accounts testAcc_bad = accountDAO.getById(111L);
        List<Clients> result_bad = accountDAO.findClientOfAccount(testAcc_bad);
        assertEquals(null, result_bad);
    }

    @BeforeEach
    void beforeEach() {
        List<Accounts> accountList = new ArrayList<>();
        LocalDate date = LocalDate.parse("11-12-2010", dateFormatter);

        List<Clients> personList = new ArrayList<>();
        LocalDate birth = LocalDate.parse("12-11-2018", dateFormatter);

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
