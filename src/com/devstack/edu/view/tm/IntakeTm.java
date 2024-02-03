package com.devstack.edu.view.tm;

import javafx.scene.control.Button;

import java.time.LocalDate;

public class IntakeTm {
    private long intakeId;
    private String intakeName;
    private String programName;
    private LocalDate date;
    private Button button;

    public IntakeTm() {
    }

    public IntakeTm(long intakeId, String intakeName, String programName, LocalDate date, Button button) {
        this.intakeId = intakeId;
        this.intakeName = intakeName;
        this.programName = programName;
        this.date = date;
        this.button = button;
    }

    public long getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(long intakeId) {
        this.intakeId = intakeId;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "IntakeTm{" +
                "intakeId=" + intakeId +
                ", intakeName='" + intakeName + '\'' +
                ", programName='" + programName + '\'' +
                ", date=" + date +
                ", button=" + button +
                '}';
    }
}
