package com.devstack.edu.view.tm;

import javafx.scene.control.ButtonBar;

public class TrainerTm {
    private long id;
    private String name;
    private String email;
    private String nic;
    private String address;
    private String status;
    private ButtonBar buttonBar;

    public TrainerTm() {
    }

    public TrainerTm(long id, String name, String email, String nic, String address, String status, ButtonBar buttonBar) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nic = nic;
        this.address = address;
        this.status = status;
        this.buttonBar = buttonBar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
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
        return "TrainerTm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", nic='" + nic + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", buttonBar=" + buttonBar +
                '}';
    }
}
