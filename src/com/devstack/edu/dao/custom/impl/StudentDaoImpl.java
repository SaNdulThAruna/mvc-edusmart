package com.devstack.edu.dao.custom.impl;

import com.devstack.edu.dao.CrudUtil;
import com.devstack.edu.dao.custom.StudentDao;
import com.devstack.edu.db.DBConnection;
import com.devstack.edu.entity.Student;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    @Override
    public boolean updateStudent(Student student, boolean isActive, int studentId) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("UPDATE student set student_name=?,email=?,dob=?,address=?,status=? " +
                "WHERE student_id=?",
                student.getStudentName(), student.getEmail(),java.sql.Date.valueOf(student.getDate()),student.getAddress(),
                isActive,studentId
        );
    }

    @Override
    public List<Student> findAllStudents(String searchText) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM student WHERE student_name LIKE ? OR email LIKE ?",searchText,searchText);
        ArrayList<Student> students = new ArrayList<>();
        while (rs.next()){
            students.add(
                    new Student(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            LocalDate.parse(rs.getString(4)),
                            rs.getString(5),
                            rs.getBoolean(6),
                            rs.getString(7))
            );
        }


        return students;
    }


    @Override
    public boolean save(Student student) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("INSERT INTO student(student_name, email, dob, address, status, user_email) VALUES (?,?,?,?,?,?)",
                student.getStudentName(),student.getEmail(),java.sql.Date.valueOf(student.getDate()), student.getAddress(),
                true,student.getUserEmail()
        );
    }

    @Override
    public boolean update(Student student) {
        return false;
    }

    @Override
    public boolean delete(Integer id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM student WHERE student_id=?",id);
    }

    @Override
    public Student find(Integer id) {
        return null;
    }

    @Override
    public List<Student> findAll() {
        return null;
    }
}
