package com.devstack.edu.controller;

import com.devstack.edu.util.GlobalVar;
import com.devstack.edu.util.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class LoginFormController {
    public AnchorPane loginFormContext;
    public TextField txtEmail;
    public PasswordField txtPassword;

    public void initialize(){
        txtEmail.setText("sandul@gmail.com");
        txtPassword.setText("1234");
    }

    public void signInOnAction(ActionEvent actionEvent) throws IOException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/edusmart", "root", "SaNdul03282005");
            //language=MySQL
            String query = "SELECT email,password FROM user WHERE email=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, txtEmail.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                if(PasswordManager.checkPw(txtPassword.getText(),
                        resultSet.getString(2))){
                    GlobalVar.userEmail = resultSet.getString(1);
                    setUI("DashboardForm");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Password is Wrong!").show();
                }

            }else{
                new Alert(Alert.AlertType.WARNING, "User not found!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            throw new RuntimeException(e);
        }
    }

    public void createAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUI("SignupForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) loginFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/" + location + ".fxml")))));
        stage.setTitle("EduSmart");
        stage.centerOnScreen();
    }
}
