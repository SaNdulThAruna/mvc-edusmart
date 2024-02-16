package com.devstack.edu.controller;

import com.devstack.edu.bo.BoFactory;
import com.devstack.edu.bo.custom.StudentBo;
import com.devstack.edu.dao.DaoFactory;
import com.devstack.edu.dao.custom.StudentDao;
import com.devstack.edu.dao.custom.impl.StudentDaoImpl;
import com.devstack.edu.db.DBConnection;
import com.devstack.edu.dto.StudentDto;
import com.devstack.edu.entity.Student;
import com.devstack.edu.util.GlobalVar;
import com.devstack.edu.validators.SimpleTextValidator;
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
    public Button btnNewStudent;

    private String searchText = "";
    private int selectedStudentId = 0;

    private StudentBo studentBo = BoFactory.getBo(BoFactory.BoType.STUDENT);

    public void initialize() {

        btnNewStudent.getStyleClass().add("button-style");

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

        txtStudentName.getStyleClass().removeAll("error");

        if (!SimpleTextValidator.validateName(txtStudentName.getText())){
            txtStudentName.getStyleClass().add("error");
            new Alert(Alert.AlertType.ERROR, "Wrong Student name").show();
            return;
        }
        if (!SimpleTextValidator.validateDob(dob.getValue().toString())){
            new Alert(Alert.AlertType.ERROR, "Wrong DOB").show();
            return;
        }
        if (!SimpleTextValidator.validateAddress(txtAddress.getText())){
            new Alert(Alert.AlertType.ERROR, "Wrong Address").show();
            return;
        }

        StudentDto student = new StudentDto(0, txtStudentName.getText(), txtEmail.getText(), dob.getValue(),
                txtAddress.getText(), true
        );
        if (btnSaveUpdate.getText().equalsIgnoreCase("Save Student")) {

            try {

                if (studentBo.saveStudent(student)) {
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

                if (studentBo.updateStudent(student,rBtnActive.isSelected(),selectedStudentId)) {
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

            ObservableList<StudentTm> tms = FXCollections.observableArrayList();

            for (StudentDto student:studentBo.findAllStudents(searchText)){
                Button deleteButton = new Button("Delete");
                Button updateButton = new Button("Update");

                deleteButton.getStyleClass().add("delete-button");
                updateButton.getStyleClass().add("update-button");

                ButtonBar bar = new ButtonBar();
                bar.getButtons().addAll(deleteButton, updateButton);

                StudentTm tm = new StudentTm(
                        student.getStudentId(),
                        student.getStudentName(),
                        student.getEmail(),
                        student.getDate().toString(),
                        student.getAddress(),
                        student.isStatus()?"Active":"InActive",
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

                deleteButton.setOnAction(e -> {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are your sure?", ButtonType.YES, ButtonType.NO);

                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get() == ButtonType.YES) {
                        try {

                            if (studentBo.deleteStudent(tm.getId())) {
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
