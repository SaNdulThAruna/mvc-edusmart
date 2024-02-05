package com.devstack.edu.controller;

import com.devstack.edu.db.DBConnection;
import com.devstack.edu.view.tm.RegistrationChartTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReportFormController {
    public AnchorPane reportFormContext;
    public AnchorPane anchorPane;

    public void initialize(){
        loadChart();
    }

    private void loadChart() {
        ObservableList<RegistrationChartTm> obList = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT register_date, COUNT(registration_id) AS registrations FROM registraion GROUP BY register_date";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                obList.add(new RegistrationChartTm(LocalDate.parse(resultSet.getString(1)),
                        resultSet.getInt(2)));
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<String,Number> lineChart = new LineChart<>(xAxis,yAxis);
        XYChart.Series<String,Number> series = new XYChart.Series<>();

        obList.forEach(e->{
            series.getData().add(new XYChart.Data<>(e.getDate().toString(),e.getCount()));
        });

        lineChart.getData().add(series);

        AnchorPane.setTopAnchor(lineChart,0.0);
        AnchorPane.setRightAnchor(lineChart,0.0);
        AnchorPane.setLeftAnchor(lineChart,0.0);
        AnchorPane.setBottomAnchor(lineChart,0.0);

        anchorPane.getChildren().add(lineChart);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashboardForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) reportFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.setTitle("EduSmart");
        stage.centerOnScreen();
    }
}
