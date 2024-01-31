package com.devstack.edu.controller;

import com.devstack.edu.view.tm.TrainerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class ProgramsFormController {
    public AnchorPane programFormContext;
    public TextField txtContent;
    public TextField txtHours;
    public TableView tblProgram;
    public TableColumn colTrainer;
    public TableColumn colProgram;
    public TableColumn colHours;
    public TableColumn colAmount;
    public TableColumn colOperation;
    public TextField txtAmount;
    public TextArea lstContent;
    public ComboBox<String> cmbTrainer;

    public void initialize(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/edusmart", "root", "SaNdul03282005");
            String query = "SELECT trainer_id,trainer_name FROM trainer";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<String> list = FXCollections.observableArrayList();

            while (resultSet.next()) {
                list.add(resultSet.getString(1)+"->"+resultSet.getString(2));
            }
            cmbTrainer.setItems(list);

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            throw new RuntimeException(e);
        }
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashboardForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) programFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.setTitle("EduSmart");
        stage.centerOnScreen();
    }

    public void btnSaveProgramOnAction(ActionEvent actionEvent) {
    }
}
