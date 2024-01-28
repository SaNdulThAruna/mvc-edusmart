package com.devstack.edu.view.tm;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;

public class StudentTm {
    private int id;
    private String name;
    private String email;
    private String dob;
    private String address;
    private String status;
    private ButtonBar buttonBar;

    public StudentTm() {
    }

    public StudentTm(int id, String name, String email, String dob, String address, String status, ButtonBar buttonBar) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.address = address;
        this.status = status;
        this.buttonBar = buttonBar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ButtonBar getButtonBar() {
        return buttonBar;
    }

    public void setButtonBar(ButtonBar buttonBar) {
        this.buttonBar = buttonBar;
    }

    @Override
    public String toString() {
        return "StudentTm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", buttonBar=" + buttonBar +
                '}';
    }
}
