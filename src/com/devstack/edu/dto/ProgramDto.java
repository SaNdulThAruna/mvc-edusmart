package com.devstack.edu.dto;

import java.util.List;

public class ProgramDto {
    private long programId;
    private int hours;
    private String programName;
    private double amount;
    private String userEmail;
    private long trainerId;
    private List<String> content;

    public ProgramDto() {
    }

    public ProgramDto(long programId, int hours, String programName, double amount, String userEmail, long trainerId, List<String> content) {
        this.programId = programId;
        this.hours = hours;
        this.programName = programName;
        this.amount = amount;
        this.userEmail = userEmail;
        this.trainerId = trainerId;
        this.content = content;
    }

    public long getProgramId() {
        return programId;
    }

    public void setProgramId(long programId) {
        this.programId = programId;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(long trainerId) {
        this.trainerId = trainerId;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Program{" +
                "programId=" + programId +
                ", hours=" + hours +
                ", programName='" + programName + '\'' +
                ", amount=" + amount +
                ", userEmail='" + userEmail + '\'' +
                ", trainerId=" + trainerId +
                ", content=" + content +
                '}';
    }
}
