package com.WebPrak.demo.DAO;

import com.WebPrak.demo.tables.Client_type;
import com.WebPrak.demo.tables.Operation_type;

import java.util.List;

public interface Operation_typeDAO extends CommonDAO<Operation_type, Long> {
    List<Operation_type> getAllByType(String typeName);
    Operation_type getByType(String typeName);

}
