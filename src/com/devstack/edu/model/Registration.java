package com.devstack.edu.model;

import java.time.LocalDate;

public class Registration {
    private long registerId;
    private LocalDate date;
    private double amount;
    private long intake;
    private long student;

    public Registration() {
    }

    public Registration(long registerId, LocalDate date, double amount, long intake, long student) {
        this.registerId = registerId;
        this.date = date;
        this.amount = amount;
        this.intake = intake;
        this.student = student;
    }

    public long getRegisterId() {
        return registerId;
    }

    public void setRegisterId(long registerId) {
        this.registerId = registerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getIntake() {
        return intake;
    }

    public void setIntake(long intake) {
        this.intake = intake;
    }

    public long getStudent() {
        return student;
    }

    public void setStudent(long student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "registerId=" + registerId +
                ", date=" + date +
                ", amount=" + amount +
                ", intake=" + intake +
                ", student=" + student +
                '}';
    }
}
