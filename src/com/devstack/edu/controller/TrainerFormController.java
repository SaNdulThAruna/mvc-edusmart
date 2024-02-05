package com.devstack.edu.controller;

import com.devstack.edu.db.DBConnection;
import com.devstack.edu.model.Student;
import com.devstack.edu.model.Trainer;
import com.devstack.edu.util.GlobalVar;
import com.devstack.edu.view.tm.TrainerTm;
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
import java.util.Optional;

public class TrainerFormController {
    public AnchorPane trainerFormController;
    public TextField txtTrainerName;
    public TextField txtAddress;
    public TextField txtSearch;
    public TextField txtEmail;
    public TableView<TrainerTm> tblTrainer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colNic;
    public TableColumn colAddress;
    public TableColumn colStatus;
    public TableColumn colOption;
    public Button btnSaveUpdate;
    public TextField txtNic;
    private String searchText = "";
    private long selectedTrainerId = 0;

    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("buttonBar"));

        loadTrainers(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            if (newValue != null) {
                loadTrainers(searchText);
            }
        });

    }

    private void loadTrainers(String searchText) {
        searchText = "%" + searchText + "%";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT * FROM trainer WHERE trainer.trainer_name LIKE ? OR trainer.trainer_email LIKE ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, searchText);
            preparedStatement.setString(2, searchText);

            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<TrainerTm> tms = FXCollections.observableArrayList();

            while (resultSet.next()) {

                Button daleteButton = new Button("Delete");
                Button updateButton = new Button("Update");

                ButtonBar bar = new ButtonBar();
                bar.getButtons().addAll(daleteButton, updateButton);

                TrainerTm tm = new TrainerTm(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getBoolean(6)?"Active":"InActive",
                        bar
                );
                tms.add(tm);

                updateButton.setOnAction(e -> {
                    txtTrainerName.setText(tm.getName());
                    txtEmail.setText(tm.getEmail());
                    txtNic.setText(tm.getNic());
                    txtAddress.setText(tm.getAddress());
                    selectedTrainerId = tm.getId();
                    btnSaveUpdate.setText("Update Trainer");
                });

                daleteButton.setOnAction(e -> {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are your sure?", ButtonType.YES, ButtonType.NO);

                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get() == ButtonType.YES) {
                        try {
                            Connection connection1 = DBConnection.getInstance().getConnection();
                            String query1 = "DELETE FROM trainer WHERE trainer_id=?";

                            PreparedStatement preparedStatement1 = connection1.prepareStatement(query1);
                            preparedStatement1.setLong(1, tm.getId());

                            if (preparedStatement1.executeUpdate() > 0) {
                                new Alert(Alert.AlertType.INFORMATION, "Trainer was Deleted!").show();
                                loadTrainers("");
                            } else {
                                new Alert(Alert.AlertType.WARNING, "Try again!").show();
                            }
                        } catch (SQLException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

            }

            tblTrainer.setItems(tms);

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            throw new RuntimeException(e);
        }
    }

    public void btnNewTrainerOnAction(ActionEvent actionEvent) {
        clearFields();
        selectedTrainerId = 0;
        btnSaveUpdate.setText("Save Trainer");
    }

    private void clearFields() {
        txtAddress.clear();
        txtTrainerName.clear();
        txtEmail.clear();
        txtNic.clear();
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashboardForm");
    }

    public void btnSaveUpdateOnAction(ActionEvent actionEvent) {
        Trainer trainer = new Trainer(0L, txtTrainerName.getText(),
                txtEmail.getText(), txtNic.getText(), txtAddress.getText(), true);
        if (btnSaveUpdate.getText().equalsIgnoreCase("Save Trainer")) {

            try {
                Connection connection = DBConnection.getInstance().getConnection();
                String query = "INSERT INTO trainer(trainer_name, trainer_email, nic, address, trainer_status) " + "VALUES (?,?,?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, trainer.getTrainerName());
                preparedStatement.setString(2, trainer.getEmail());
                preparedStatement.setString(3, trainer.getNic());
                preparedStatement.setString(4, trainer.getAddress());
                preparedStatement.setBoolean(5, trainer.isStatus());

                if (preparedStatement.executeUpdate() > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Trainer was Saved!").show();
                    clearFields();
                    loadTrainers(searchText);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try again!").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                throw new RuntimeException(e);
            }
        } else {

            if (selectedTrainerId == 0) {
                new Alert(Alert.AlertType.ERROR, "Please verify the Trainer ID").show();
                return;
            }
            try {
                Connection connection = DBConnection.getInstance().getConnection();
                String query = "UPDATE trainer set trainer_name=?,trainer_email=?,nic=?,address=? " +
                        "WHERE trainer_id=?";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, trainer.getTrainerName());
                preparedStatement.setString(2, trainer.getEmail());
                preparedStatement.setString(3, trainer.getNic());
                preparedStatement.setString(4, trainer.getAddress());
                preparedStatement.setLong(5, selectedTrainerId);

                if (preparedStatement.executeUpdate() > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Trainer was Updated!").show();
                    clearFields();
                    loadTrainers(searchText);
                    btnSaveUpdate.setText("Save Trainer");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try again!").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                throw new RuntimeException(e);
            }
        }
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) trainerFormController.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.setTitle("EduSmart");
        stage.centerOnScreen();
    }
}
