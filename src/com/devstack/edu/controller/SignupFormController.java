package com.devstack.edu.controller;

import com.devstack.edu.model.User;
import com.devstack.edu.util.GlobalVar;
import com.devstack.edu.util.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupFormController {

    public void initialize(){
        txtFirstName.requestFocus();
    }

    public AnchorPane registerFormContext;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public TextField txtFirstName;
    public TextField txtLastName;
    public Button btnSignup;

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {


        if(txtFirstName.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Enter your First Name").show();
            txtFirstName.requestFocus();
        } else if (txtLastName.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Please Enter your Last Name").show();
            txtLastName.requestFocus();
        } else if (txtEmail.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Please Enter your Email").show();
            txtEmail.requestFocus();
        } else if (txtPassword.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Please Enter Password").show();
            txtPassword.requestFocus();
        }else{

            User user = new User(
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    txtEmail.getText(),
                    txtPassword.getText()
            );

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/edusmart","root","SaNdul03282005");
                String query = "INSERT INTO user VALUES (?,?,?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,user.getRootEmail());
                preparedStatement.setString(2,user.getFirstName());
                preparedStatement.setString(3,user.getLastName());
                preparedStatement.setString(4, PasswordManager.encrypt(user.getPassword()));
                preparedStatement.setBoolean(5,true);

                if(preparedStatement.executeUpdate()>0){
                    new Alert(Alert.AlertType.INFORMATION,"User was Saved!").show();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Try again!").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
                throw new RuntimeException(e);
            }

            GlobalVar.userEmail = user.getRootEmail();

            setUI("DashboardForm");
        }


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

    public void emailNextOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void passwordNextOnAction(ActionEvent actionEvent) {
        btnSignup.requestFocus();
    }

    public void fNameNextOnAction(ActionEvent actionEvent) {
        txtLastName.requestFocus();
    }

    public void lNameNextOnAction(ActionEvent actionEvent) {
        txtEmail.requestFocus();
    }
}
