package com.devstack.edu.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T, ID> extends SuperDao{
    boolean save(T t) throws SQLException, ClassNotFoundException;

    boolean update(T t);

    boolean delete(ID id) throws SQLException, ClassNotFoundException;

    public T find(ID id);

    public List<T> findAll() throws SQLException, ClassNotFoundException;

}

