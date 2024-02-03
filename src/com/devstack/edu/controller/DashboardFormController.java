package com.devstack.edu.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardFormController {

    public AnchorPane dashboardFormContext;
    public Label lblTime;
    public Label lblDate;

    public void initialize(){
        loadDateAndTime();
    }

    private void loadDateAndTime() {
        Timeline timeline =new Timeline(
                new KeyFrame(
                        Duration.seconds(1),event -> {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    String currentTime = simpleDateFormat.format(new Date());
                    lblTime.setText(currentTime);
                }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        //---------------------------------------------
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(simpleDateFormat.format(date));
    }

    public void studentOnClickAction(MouseEvent mouseEvent) throws IOException {
        setUI("StudentForm");
    }

    public void programOnClickAction(MouseEvent mouseEvent) throws IOException {
        setUI("ProgramsForm");
    }

    public void reportOnClickAction(MouseEvent mouseEvent) throws IOException {
        setUI("ReportForm");
    }

    public void registrationOnClickAction(MouseEvent mouseEvent) throws IOException {
        setUI("RegistrationForm");
    }

    public void incomeOnClickAction(MouseEvent mouseEvent) throws IOException {
        setUI("IncomeForm");
    }

    public void intakeOnClickAction(MouseEvent mouseEvent) throws IOException {
        setUI("IntakesForm");
    }

    public void trainerOnClickAction(MouseEvent mouseEvent) throws IOException {
        setUI("TrainerForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) dashboardFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.setTitle("EduSmart");
        stage.centerOnScreen();
    }

    public void makeBackupOnAction(ActionEvent actionEvent) {
        String userName = "root";
        String password = "SaNdul03282005";
        String database = "edusmart";

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String timeStamp = simpleDateFormat.format(new Date());
            String fileName = "backup_" + timeStamp + ".sql"; // backup_3234.sql

            // Provide the full path to mysqldump executable
            String mysqldumpPath = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe"; // Replace with the actual path
            String sqlCommand = mysqldumpPath + " --user=" + userName + " --password=" + password +
                    " --host=localhost " + database + " --result-file=" + fileName;

            Process process = Runtime.getRuntime().exec(sqlCommand);
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                File backup = new File(fileName);

                if (backup.exists()) {
                    FileInputStream fileInputStream = new FileInputStream(backup);
                    FileOutputStream fileOutputStream = new FileOutputStream("src/" + fileName);

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }

                    new Alert(Alert.AlertType.INFORMATION, "Backup File was Created!").show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
