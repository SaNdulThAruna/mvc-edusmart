package com.devstack.edu.bo.custom.impl;

import com.devstack.edu.bo.custom.StudentBo;
import com.devstack.edu.dao.DaoFactory;
import com.devstack.edu.dao.custom.StudentDao;
import com.devstack.edu.dto.StudentDto;
import com.devstack.edu.entity.Student;
import com.devstack.edu.util.GlobalVar;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBoImpl implements StudentBo {

    private final StudentDao studentDao = DaoFactory.getDao(DaoFactory.DaoType.STUDENT);

    @Override
    public boolean saveStudent(StudentDto dto) throws SQLException, ClassNotFoundException {
        return studentDao.save(
                new Student(dto.getStudentId(), dto.getStudentName(), dto.getEmail(),
                        dto.getDate(), dto.getAddress(), dto.isStatus(), GlobalVar.userEmail
                )
        );
    }

    @Override
    public boolean updateStudent(StudentDto student, boolean isActive, int studentId) throws SQLException, ClassNotFoundException {
        return studentDao.updateStudent(
                new Student(student.getStudentId(), student.getStudentName(), student.getEmail(),
                        student.getDate(), student.getAddress(), student.isStatus(), GlobalVar.userEmail
                ), isActive, studentId
        );
    }

    @Override
    public boolean deleteStudent(int studentId) throws SQLException, ClassNotFoundException {
        return studentDao.delete(studentId);
    }

    @Override
    public List<StudentDto> findAllStudents(String searchText) throws SQLException, ClassNotFoundException {
        List<Student> allStudents = studentDao.findAllStudents(searchText);
        List<StudentDto> dtos = new ArrayList<>();
        for(Student student:allStudents){
            dtos.add(new StudentDto(student.getStudentId(), student.getStudentName(), student.getEmail(),
                    student.getDate(),student.getAddress(),student.isStatus()));
        }
        return dtos;
    }
}
