package com.example.motomamiui.Controllers;

import com.example.motomamiui.DatabaseConnector;
import com.example.motomamiui.UserSession;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaReportController {

    @FXML
    private TableView<ObservableList<String>> reportTableView;
    @FXML
    private TableColumn<ObservableList<String>, String> reportIdColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> dateColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> userNameColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> userLastNameColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> descriptionColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> damageFrontColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> damageSideColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> damageRearColumn;

    @FXML
    private void initialize() {
        initializeTableColumns();
        populateTable();
    }

    private void initializeTableColumns() {
        reportIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        userNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        userLastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
        damageFrontColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));
        damageSideColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(6)));
        damageRearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(7)));
    }
    private void populateTable() {
        UserSession userSession = UserSession.getInstance("UserID");
        String userID = userSession.getUserId();
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT AccidentReport.ReportID, AccidentReport.Date, User.FirstName, User.LastName, AccidentReport.Description, AccidentReport.DamageFront, AccidentReport.DamageSide, AccidentReport.DamageRear FROM AccidentReport JOIN User ON AccidentReport.UserID = User.UserID WHERE AccidentReport.UserID = ?");
        ) {
            pstmt.setString(1, userID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ObservableList<String> rowData = FXCollections.observableArrayList(
                            String.valueOf(rs.getInt("ReportID")),
                            rs.getString("Date"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("Description"),
                            String.valueOf(rs.getBoolean("DamageFront")),
                            String.valueOf(rs.getBoolean("DamageSide")),
                            String.valueOf(rs.getBoolean("DamageRear"))
                    );
                    reportTableView.getItems().add(rowData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error al acceder a la base de datos.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    @FXML
    private void handleBackToMenu(ActionEvent event) {
        try {
            // 获取当前窗口的 Stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 加载 UserDashboard.fxml 文件
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/motomamiui/Views/UserDashboard.fxml"));

            // 创建一个新的 Scene
            Scene scene = new Scene(root);

            // 将新的 Scene 设置到当前的 Stage 上
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
