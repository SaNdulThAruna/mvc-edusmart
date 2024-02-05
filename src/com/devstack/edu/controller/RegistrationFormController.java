package com.devstack.edu.controller;

import com.devstack.edu.db.DBConnection;
import com.devstack.edu.model.Payment;
import com.devstack.edu.model.Registration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class RegistrationFormController {
    public AnchorPane registrationFormContext;
    public TextField txtSearchIntakeId;
    public TableView tblRegistration;
    public TableColumn colIntakeId;
    public TableColumn colProgram;
    public TableColumn colStudentName;

    public TableColumn colRegisterDate;
    public TableColumn colOperation;
    public ComboBox<String> cmbProgram;
    public ComboBox<String> cmbIntake;
    public TextField txtStudent;

    public void initialize() {
        loadAllProgram();

        cmbProgram.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadAllIntakes(Long.parseLong(newValue.split("->")[0]));
            }
        });

        manageAutoComplete();

    }

    private void manageAutoComplete() {
        ArrayList<String> data = new ArrayList<>();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT email from student";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                data.add(resultSet.getString(1));
            }

            TextFields.bindAutoCompletion(txtStudent, data);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllIntakes(long id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT i.intake_id,i.intake_name From intake i WHERE i.program_program_id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList obList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                obList.add(resultSet.getLong(1) + "->" + resultSet.getString(2));
            }

            cmbIntake.setItems(obList);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllProgram() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT program.program_id,program.program_name FROM program";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList obList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                obList.add(resultSet.getLong(1) + "->" + resultSet.getString(2));
            }

            cmbProgram.setItems(obList);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashboardForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) registrationFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.setTitle("EduSmart");
        stage.centerOnScreen();
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {

        double amount = getProgramAmount(Long.parseLong(cmbProgram.getValue().split("->")[0]));

        if (amount == 0) {
            new Alert(Alert.AlertType.WARNING, "Program not found!").show();
            return;
        }

        Long studentId = getSelectedStudent(txtStudent.getText());
        if (studentId == 0) {
            new Alert(Alert.AlertType.WARNING, "Student not found!").show();
            return;
        }

        Registration registration = new Registration(
                0, LocalDate.now(), amount,
                Long.parseLong(cmbIntake.getValue().split("->")[0]), studentId
        );

        boolean isSaved = saveRegistration(registration);
        if (isSaved) {
            Long registerId = findRegistration(registration);
            if (registerId == 0) {
                new Alert(Alert.AlertType.WARNING, "Registration is not found!").show();
                return;
            }
            Payment payment = new Payment(
                    0, LocalDate.now(), true, amount, registerId
            );
            boolean isPaymentSaved = savePayment(payment);
            if (isPaymentSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Registration was success!").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Registration wasn't success!").show();
            }
        }
    }

    private boolean savePayment(Payment payment) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "INSERT INTO payment(date, is_verified, amount, registraion_registration_id) " +
                    "VALUES (?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, payment.getDate());
            preparedStatement.setObject(2, payment.isVerified());
            preparedStatement.setObject(3, payment.getAmount());
            preparedStatement.setObject(4, payment.getRegistrationId());

            return preparedStatement.executeUpdate() > 0;

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Long findRegistration(Registration reg) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT registraion.registration_id FROM registraion WHERE intake_intake_id=? AND student_student_id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, reg.getIntake());
            preparedStatement.setObject(2, reg.getStudent());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return 0L;
    }

    private boolean saveRegistration(Registration reg) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "INSERT INTO registraion(register_date, program_amount, intake_intake_id, student_student_id) " +
                    "VALUES (?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, reg.getDate());
            preparedStatement.setObject(2, reg.getAmount());
            preparedStatement.setObject(3, reg.getIntake());
            preparedStatement.setObject(4, reg.getStudent());

            return preparedStatement.executeUpdate() > 0;

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private double getProgramAmount(long programId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT amount FROM program WHERE program_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, programId);

            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList obList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                return resultSet.getDouble(1);
            }

            cmbProgram.setItems(obList);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    private Long getSelectedStudent(String email) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT student_id FROM student WHERE email = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList obList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                return resultSet.getLong(1);
            }

            cmbProgram.setItems(obList);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return 0L;
    }
}

