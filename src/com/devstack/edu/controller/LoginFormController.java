package com.devstack.edu.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginFormController {
    public AnchorPane loginFormContext;

    public void signInOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashboardForm");
    }

    public void createAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUI("SignupForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) loginFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.setTitle("EduSmart");
        stage.centerOnScreen();
    }
}
