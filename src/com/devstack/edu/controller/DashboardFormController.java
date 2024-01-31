package com.devstack.edu.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
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
}
