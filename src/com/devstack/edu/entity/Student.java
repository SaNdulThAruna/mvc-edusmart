package com.devstack.edu.entity;

import java.time.LocalDate;

public class Student {
    private int studentId;
    private String studentName;
    private String email;
    private LocalDate date;
    private String address;
    private boolean status;
    private String userEmail;

    public Student() {
    }

    public Student(int studentId, String studentName, String email, LocalDate date, String address, boolean status, String userEmail) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.date = date;
        this.address = address;
        this.status = status;
        this.userEmail = userEmail;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
