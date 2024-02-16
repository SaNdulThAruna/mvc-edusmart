package com.devstack.edu.controller;

import com.devstack.edu.bo.BoFactory;
import com.devstack.edu.bo.custom.TrainerBo;
import com.devstack.edu.db.DBConnection;
import com.devstack.edu.util.GlobalVar;
import com.devstack.edu.view.tm.ProgramTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class ProgramsFormController {
    public AnchorPane programFormContext;
    public TextField txtContent;
    public TextField txtHours;
    public TableView<ProgramTm> tblProgram;
    public TableColumn colTrainer;
    public TableColumn colProgram;
    public TableColumn colHours;
    public TableColumn colAmount;
    public TableColumn colOperation;
    public TextField txtAmount;
    public ComboBox<String> cmbTrainer;
    public TextField txtProgram;
    public ListView lstContent;
    public TextField txtSearch;
    ObservableList<HBox> contents = FXCollections.observableArrayList();
    private String searchText = "";

    private TrainerBo trainerBo = BoFactory.getBo(BoFactory.BoType.TRAINER);

    public void initialize() {

        colTrainer.setCellValueFactory(new PropertyValueFactory<>("trainerId"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("program"));
        colHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colOperation.setCellValueFactory(new PropertyValueFactory<>("operation"));
        loadAllPrograms(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            if (newValue != null) {
                loadAllPrograms(searchText);
            }
        });

        loadAllTrainers();
    }

    private void loadAllTrainers() {
        try {
            ObservableList<String> list = FXCollections.observableArrayList(trainerBo.loadAllTrainers());
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
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.setTitle("EduSmart");
        stage.centerOnScreen();
    }

    public void btnSaveProgramOnAction(ActionEvent actionEvent) {
//        Program program = new Program(
//                0L,
//                Integer.parseInt(txtHours.getText()),
//                txtProgram.getText(),
//                Double.parseDouble(txtAmount.getText()),
//                GlobalVar.userEmail,
//                Long.parseLong(cmbTrainer.getValue().split("->")[0]),
//                contents
//        );

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "Insert Into program(hours, program_name, amount, user_email, trainer_trainer_id) VALUES (?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setObject(1, program.getHours());
//            preparedStatement.setObject(2, program.getProgramName());
//            preparedStatement.setObject(3, program.getAmount());
//            preparedStatement.setObject(4, program.getUserEmail());
//            preparedStatement.setObject(5, program.getTrainerId());

            boolean isSaved = preparedStatement.executeUpdate() > 0;

            //--------------------------------------------------------
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT program.program_id FROM program ORDER BY program_id DESC LIMIT 1");
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                long pid = resultSet.getLong(1);
                if (isSaved) {

                    for (HBox hBox:contents){
                        for (Node node:hBox.getChildren()){
                            if (node instanceof Label){
                                Label l = (Label) node;
                                String text = l.getText();
                                PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO program_content(header, program_program_id) VALUES (?,?)");
                                preparedStatement2.setObject(1, text);
                                preparedStatement2.setObject(2, pid);
                                preparedStatement2.executeUpdate();
                            }
                        }
                    }
                    new Alert(Alert.AlertType.INFORMATION, "Saved").show();
                    loadAllPrograms(searchText);
                }
            }
            //--------------------------------------------------------

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllPrograms(String searchText) {
        searchText = "%" + searchText + "%";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT * FROM program WHERE program_name LIKE ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, searchText);

            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<ProgramTm> tms = FXCollections.observableArrayList();

            while (resultSet.next()) {

                Button daleteButton = new Button("Delete");
                Button showMoreButton = new Button("Show");

                ButtonBar bar = new ButtonBar();
                bar.getButtons().addAll(daleteButton, showMoreButton);

                ProgramTm tm = new ProgramTm(
                        resultSet.getLong("program_id"),
                        resultSet.getLong("trainer_trainer_id"),
                        resultSet.getString("program_name"),
                        resultSet.getInt("hours"),
                        resultSet.getDouble("amount"),
                        bar
                );
                tms.add(tm);

                showMoreButton.setOnAction(e -> {
                    try {
                        /*Parent parent = FXMLLoader.load(getClass().getResource("../view/ProgramDetailForm.fxml"));
                        Scene scene = new Scene(parent);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();*/

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ProgramDetailForm.fxml"));
                        Parent parent = loader.load();
                        ProgramDetailFormController controller = loader.getController();
                        controller.setId(tm.getProgramId());

                        Scene scene = new Scene(parent);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                daleteButton.setOnAction(e -> {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are your sure?", ButtonType.YES, ButtonType.NO);

                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get() == ButtonType.YES) {
                        try {
                            Connection connection1 = DBConnection.getInstance().getConnection();
                            String query1 = "DELETE FROM program WHERE program_id=?";

                            PreparedStatement preparedStatement1 = connection1.prepareStatement(query1);
                            preparedStatement1.setLong(1, tm.getProgramId());

                            if (preparedStatement1.executeUpdate() > 0) {
                                new Alert(Alert.AlertType.INFORMATION, "Program was Deleted!").show();
                                loadAllPrograms("");
                            } else {
                                new Alert(Alert.AlertType.WARNING, "Try again!").show();
                            }
                        } catch (SQLException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

            }

            tblProgram.setItems(tms);

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            throw new RuntimeException(e);
        }
    }

    public void txtContentOnAction(ActionEvent actionEvent) {
//        contents.clear();
        HBox hBox = new HBox();
        Button btn = new Button("Delete");
        hBox.getChildren().addAll(btn,new Label(txtContent.getText()));
        contents.add(hBox);
        txtContent.clear();
        lstContent.setItems(contents);
    }
}
