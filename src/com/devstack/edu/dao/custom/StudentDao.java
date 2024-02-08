package com.devstack.edu.dao.custom;

import com.devstack.edu.dao.CrudDao;
import com.devstack.edu.entity.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDao extends CrudDao<Student, Integer> {
    public boolean updateStudent(Student student, boolean isActive, int studentId) throws SQLException, ClassNotFoundException;
    public List<Student> findAllStudents(String searchText) throws SQLException, ClassNotFoundException;
}
