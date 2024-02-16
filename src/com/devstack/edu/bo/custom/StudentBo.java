package com.devstack.edu.bo.custom;

import com.devstack.edu.bo.SuperBo;
import com.devstack.edu.dao.custom.StudentDao;
    import com.devstack.edu.dto.StudentDto;

import java.sql.SQLException;
import java.util.List;

public interface StudentBo extends SuperBo {
    public boolean saveStudent(StudentDto dto) throws SQLException, ClassNotFoundException;
    public boolean updateStudent(StudentDto student, boolean isActive, int studentId) throws SQLException, ClassNotFoundException;
    public boolean deleteStudent(int studentId) throws SQLException, ClassNotFoundException;
    public List<StudentDto> findAllStudents(String searchText) throws SQLException, ClassNotFoundException;
}
