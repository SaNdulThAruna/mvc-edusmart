package com.devstack.edu.entity;

public class Trainer {
    private Long trainerId;
    private String trainerName;
    private String email;
    private String nic;
    private String address;
    private boolean status;

    public Trainer() {
    }

    public Trainer(Long trainerId, String trainerName, String email, String nic, String address, boolean status) {
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.email = email;
        this.nic = nic;
        this.address = address;
        this.status = status;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "trainerId=" + trainerId +
                ", trainerName='" + trainerName + '\'' +
                ", email='" + email + '\'' +
                ", nic='" + nic + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                '}';
    }
}
