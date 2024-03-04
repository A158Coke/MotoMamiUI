package com.example.motomamiui.Controllers;

import com.example.motomamiui.DatabaseConnector;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDashboardController {
    @FXML
    private TextField txtSearch;
    @FXML
    private TableView<ObservableList<String>> userTable;
    @FXML
    private TableColumn<ObservableList<String>, String> columnUserID;
    @FXML
    private TableColumn<ObservableList<String>, String> columnFirstName;
    @FXML
    private TableColumn<ObservableList<String>, String> columnLastName;
    @FXML
    private TableColumn<ObservableList<String>, String> columnDateOfBirth;
    @FXML
    private TableColumn<ObservableList<String>, String> columnAddress;
    @FXML
    private TableColumn<ObservableList<String>, String> columnPhone;
    @FXML
    private TableColumn<ObservableList<String>, String> columnLicenseOrID;
    @FXML
    private TableColumn<ObservableList<String>, String> columnLicenseType;
    @FXML
    private TableColumn<ObservableList<String>, String> columnEmail;

    public void initialize() {
        columnUserID.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        columnFirstName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        columnLastName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        columnDateOfBirth.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        columnAddress.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
        columnPhone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(5)));
        columnLicenseOrID.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(6)));
        columnLicenseType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(7)));
        columnEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(8)));
    }

    @FXML
    private void handleSearch() {
        String searchTerm = txtSearch.getText();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        String sql = "SELECT * FROM User WHERE LastName LIKE ? OR LicenseOrID LIKE ?";


        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + searchTerm + "%");
            pstmt.setString(2, "%" + searchTerm + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(String.valueOf(rs.getInt("UserID")));
                row.add(rs.getString("FirstName"));
                row.add(rs.getString("LastName"));
                row.add(rs.getString("DateOfBirth") != null ? rs.getString("DateOfBirth") : "");
                row.add(rs.getString("Address"));
                row.add(rs.getString("Phone"));
                row.add(rs.getString("LicenseOrID"));
                row.add(rs.getString("LicenseType"));
                row.add(rs.getString("Email"));
                data.add(row);
            }

            userTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
            // 处理异常
        }
    }
}
