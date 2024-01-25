package com.devstack.edu.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupFormController {
    public AnchorPane registerFormContext;

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashboardForm");
    }

    public void alreadyHaveAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUI("LoginForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) registerFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.setTitle("EduSmart");
        stage.centerOnScreen();
    }
}
