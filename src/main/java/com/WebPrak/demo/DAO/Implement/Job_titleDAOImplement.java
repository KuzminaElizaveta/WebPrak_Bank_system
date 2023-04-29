package com.WebPrak.demo.DAO.Implement;

import com.WebPrak.demo.DAO.Job_titleDAO;
import com.WebPrak.demo.tables.Job_titles;
import org.springframework.stereotype.Repository;

@Repository
public class Job_titleDAOImplement extends CommonDAOImplement<Job_titles, Long> implements Job_titleDAO{

    public Job_titleDAOImplement(){
        super(Job_titles.class);
    }
}
