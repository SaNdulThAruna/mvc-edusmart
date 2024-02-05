package com.devstack.edu.controller;

import com.devstack.edu.db.DBConnection;
import com.devstack.edu.model.Intake;
import com.devstack.edu.view.tm.IntakeTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class IntakesFormController {
    public AnchorPane intakeFormContext;
    public TextField txtIntakeName;
    public TableView<IntakeTm> tblIntake;
    public TableColumn colId;
    public TableColumn colProgram;
    public TableColumn colIntakeName;
    public TableColumn colStartDate;
    public TableColumn colOperation;
    public Button btnSaveIntake;
    public DatePicker dpStartDate;
    public ComboBox<String> cmbProgram;

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("intakeId"));
        colIntakeName.setCellValueFactory(new PropertyValueFactory<>("intakeName"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colOperation.setCellValueFactory(new PropertyValueFactory<>("button"));

        loadAllPrograms();
        loadAllIntakes();
    }

    private void loadAllPrograms() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT program_id,program_name FROM program";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<String> list = FXCollections.observableArrayList();

            while (resultSet.next()) {
                list.add(resultSet.getString(1) + "->" + resultSet.getString(2));
            }
            cmbProgram.setItems(list);

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            throw new RuntimeException(e);
        }
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashboardForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) intakeFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.setTitle("EduSmart");
        stage.centerOnScreen();
    }

    public void txtSearHere(ActionEvent actionEvent) {
    }

    public void btnNewIntakeOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveIntakeOnAction(ActionEvent actionEvent) {
        Intake intake = new Intake(0, txtIntakeName.getText(), dpStartDate.getValue(), Long.parseLong(cmbProgram.getValue().split("->")[0]));

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "INSERT INTO intake(intake_name, start_date, program_program_id) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, intake.getIntakeName());
            preparedStatement.setObject(2, intake.getStartDate());
            preparedStatement.setObject(3, intake.getProgramId());

            boolean isSaved = preparedStatement.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Intake Saved").show();
                clearFields();
                loadAllIntakes();
            }

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            throw new RuntimeException(e);
        }
    }

    private void loadAllIntakes() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT p.program_name,i.intake_id,i.intake_name,i.start_date FROM intake i INNER JOIN program p " +
                    "ON i.program_program_id = p.program_id";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<IntakeTm> obList = FXCollections.observableArrayList();
            while (resultSet.next()) {

                Button btn = new Button("Delete");

                IntakeTm tm = new IntakeTm(
                        resultSet.getLong(2),
                        resultSet.getString(3),
                        resultSet.getString(1),
                        LocalDate.parse(resultSet.getString(4)),
                        btn
                );
                obList.add(tm);
            }

            tblIntake.setItems(obList);

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtIntakeName.clear();
        dpStartDate.setValue(null);
        cmbProgram.setValue(null);
    }
}
