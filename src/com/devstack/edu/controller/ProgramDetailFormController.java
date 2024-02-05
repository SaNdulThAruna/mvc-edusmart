package com.devstack.edu.controller;

import com.devstack.edu.db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.sql.*;

public class ProgramDetailFormController {
    public ListView<String> lstData;

    public void setId(Long id){

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT * FROM program_content WHERE program_program_id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList obList = FXCollections.observableArrayList();
            while(resultSet.next()){
                obList.add(resultSet.getString(2));
            }

            lstData.setItems(obList);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
