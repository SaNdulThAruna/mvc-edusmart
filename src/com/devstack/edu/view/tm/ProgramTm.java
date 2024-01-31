package com.devstack.edu.view.tm;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;

public class ProgramTm {
    private long programId;
    private long trainerId;
    private String program;
    private int hours;
    private double amount;
    private ButtonBar operation;

    public ProgramTm() {
    }

    public ProgramTm(long programId, long trainerId, String program, int hours, double amount, ButtonBar operation) {
        this.programId = programId;
        this.trainerId = trainerId;
        this.program = program;
        this.hours = hours;
        this.amount = amount;
        this.operation = operation;
    }

    public long getProgramId() {
        return programId;
    }

    public void setProgramId(long programId) {
        this.programId = programId;
    }

    public long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(long trainerId) {
        this.trainerId = trainerId;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ButtonBar getOperation() {
        return operation;
    }

    public void setOperation(ButtonBar operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "ProgramTm{" +
                "programId=" + programId +
                ", trainerId=" + trainerId +
                ", program='" + program + '\'' +
                ", hours=" + hours +
                ", amount=" + amount +
                ", operation=" + operation +
                '}';
    }
}
