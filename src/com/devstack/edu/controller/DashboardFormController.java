package com.devstack.edu.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {

    public AnchorPane dashboardFormContext;

    public void studentOnClickAction(MouseEvent mouseEvent) throws IOException {
        setUI("StudentForm");
    }

    public void programOnClickAction(MouseEvent mouseEvent) throws IOException {
        setUI("ProgramsForm");
    }

    public void reportOnClickAction(MouseEvent mouseEvent) throws IOException {
        setUI("ReportForm");
    }

    public void registrationOnClickAction(MouseEvent mouseEvent) throws IOException {
        setUI("RegistrationForm");
    }

    public void incomeOnClickAction(MouseEvent mouseEvent) throws IOException {
        setUI("IncomeForm");
    }

    public void intakeOnClickAction(MouseEvent mouseEvent) throws IOException {
        setUI("IntakesForm");
    }

    public void trainerOnClickAction(MouseEvent mouseEvent) throws IOException {
        setUI("TrainerForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) dashboardFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.setTitle("EduSmart");
        stage.centerOnScreen();
    }
}
