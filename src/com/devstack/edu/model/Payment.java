package com.devstack.edu.model;

import java.time.LocalDate;

public class Payment {
    private long id;
    private LocalDate date;
    private boolean isVerified;
    private double amount;
    private long registrationId;

    public Payment() {
    }

    public Payment(long id, LocalDate date, boolean isVerified, double amount, long registrationId) {
        this.id = id;
        this.date = date;
        this.isVerified = isVerified;
        this.amount = amount;
        this.registrationId = registrationId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(long registrationId) {
        this.registrationId = registrationId;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", date=" + date +
                ", isVerified=" + isVerified +
                ", amount=" + amount +
                ", registrationId=" + registrationId +
                '}';
    }
}
