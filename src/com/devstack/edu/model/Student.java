package com.devstack.edu.model;

import java.util.Date;

public class Student {
    private int studentId;
    private String studentName;
    private String email;
    private Date date;
    private String address;
    private boolean status;

    public Student() {
    }

    public Student(int studentId, String studentName, String email, Date date, String address, boolean status) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.date = date;
        this.address = address;
        this.status = status;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", email='" + email + '\'' +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", status=" + status +
                '}';
    }
}
