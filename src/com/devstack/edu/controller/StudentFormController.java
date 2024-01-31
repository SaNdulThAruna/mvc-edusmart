package com.devstack.edu.controller;

import com.devstack.edu.model.Student;
import com.devstack.edu.util.GlobalVar;
import com.devstack.edu.view.tm.StudentTm;
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

public class StudentFormController {
    public AnchorPane studentFormContext;
    public TextField txtStudentName;
    public TextField txtAddress;
    public TextField txtEmail;
    public DatePicker dob;
    public Button btnSaveUpdate;
    public TableView<StudentTm> tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colDob;
    public TableColumn colAddress;
    public TableColumn colStatus;
    public TableColumn colOption;
    public TextField txtSearch;
    public RadioButton rBtnActive;
    public ToggleGroup status;
    public Label lblStatus;
    public RadioButton rBtnInactive;

    private String searchText = "";
    private int selectedStudentId = 0;

    public void initialize() {

        manageStatusVisibility(false);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("buttonBar"));

        loadStudent(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            if (newValue != null) {
                loadStudent(searchText);
            }
        });
    }

    private void manageStatusVisibility(boolean status) {
        lblStatus.setVisible(status);
        rBtnActive.setVisible(status);
        rBtnInactive.setVisible(status);
    }


    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashboardForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) studentFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.setTitle("EduSmart");
        stage.centerOnScreen();
    }

    public void btnSaveUpdateOnAction(ActionEvent actionEvent) {
        Student student = new Student(0, txtStudentName.getText(), txtEmail.getText(), dob.getValue(), txtAddress.getText(), true);
        if (btnSaveUpdate.getText().equalsIgnoreCase("Save Student")) {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/edusmart", "root", "SaNdul03282005");
                String query = "INSERT INTO student(student_name, email, dob, address, status, user_email) " + "VALUES (?,?,?,?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, student.getStudentName());
                preparedStatement.setString(2, student.getEmail());
                preparedStatement.setDate(3, java.sql.Date.valueOf(student.getDate()));
                preparedStatement.setString(4, student.getAddress());
                preparedStatement.setBoolean(5, true);
                preparedStatement.setString(6, GlobalVar.userEmail);

                if (preparedStatement.executeUpdate() > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Student was Saved!").show();
                    clearFields();
                    loadStudent(searchText);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try again!").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                throw new RuntimeException(e);
            }
        } else {

            if (selectedStudentId == 0) {
                new Alert(Alert.AlertType.ERROR, "Please verify the Student ID").show();
                return;
            }
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/edusmart", "root", "SaNdul03282005");
                String query = "UPDATE student set student_name=?,email=?,dob=?,address=?,status=? " +
                        "WHERE student_id=?";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, student.getStudentName());
                preparedStatement.setString(2, student.getEmail());
                preparedStatement.setDate(3, java.sql.Date.valueOf(student.getDate()));
                preparedStatement.setString(4, student.getAddress());
                preparedStatement.setBoolean(5, rBtnActive.isSelected());
                preparedStatement.setInt(6, selectedStudentId);

                if (preparedStatement.executeUpdate() > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Student was Updated!").show();
                    clearFields();
                    loadStudent(searchText);
                    manageStatusVisibility(false);
                    btnSaveUpdate.setText("Save Student");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try again!").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                throw new RuntimeException(e);
            }
        }
    }

    private void clearFields() {
        txtAddress.clear();
        txtStudentName.clear();
        txtEmail.clear();
        dob.setValue(null);
    }

    private void loadStudent(String searchText) {

        searchText = "%" + searchText + "%";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/edusmart", "root", "SaNdul03282005");
            String query = "SELECT * FROM student WHERE student_name LIKE ? OR email LIKE ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, searchText);
            preparedStatement.setString(2, searchText);

            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<StudentTm> tms = FXCollections.observableArrayList();

            while (resultSet.next()) {

                Button daleteButton = new Button("Delete");
                Button updateButton = new Button("Update");

                ButtonBar bar = new ButtonBar();
                bar.getButtons().addAll(daleteButton, updateButton);

                StudentTm tm = new StudentTm(
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
                    txtStudentName.setText(tm.getName());
                    txtEmail.setText(tm.getEmail());
                    dob.setValue(LocalDate.parse(tm.getDob()));
                    txtAddress.setText(tm.getAddress());
                    selectedStudentId = tm.getId();
                    //===========================================
                    if(tm.isStatus().equals("Active")){
                        rBtnActive.setSelected(true);
                    }else{
                        rBtnInactive.setSelected(true);
                    }
                    manageStatusVisibility(true);
                    //===========================================
                    btnSaveUpdate.setText("Update Student");
                });

                daleteButton.setOnAction(e -> {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are your sure?", ButtonType.YES, ButtonType.NO);

                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get() == ButtonType.YES) {
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection connection1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/edusmart", "root", "SaNdul03282005");
                            String query1 = "DELETE FROM student WHERE student_id=?";

                            PreparedStatement preparedStatement1 = connection1.prepareStatement(query1);
                            preparedStatement1.setInt(1, tm.getId());

                            if (preparedStatement1.executeUpdate() > 0) {
                                new Alert(Alert.AlertType.INFORMATION, "Student was Deleted!").show();
                                loadStudent("");
                            } else {
                                new Alert(Alert.AlertType.WARNING, "Try again!").show();
                            }
                        } catch (SQLException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

            }

            tblStudent.setItems(tms);

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            throw new RuntimeException(e);
        }
    }

    public void btnNewStudentOnAction(ActionEvent actionEvent) {
        clearFields();
        manageStatusVisibility(false);
        selectedStudentId = 0;
        btnSaveUpdate.setText("Save Student");
    }
}
