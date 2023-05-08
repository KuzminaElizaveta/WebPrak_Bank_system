package com.WebPrak.demo.DAO;


import com.WebPrak.demo.tables.*;

import java.util.List;

public interface Account_typeDAO extends CommonDAO<Account_type, Long> {
    List<Account_type> getAllByType(String typeName);
    Account_type getByType(String typeName);
}
