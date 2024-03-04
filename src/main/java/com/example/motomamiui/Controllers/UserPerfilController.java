package com.example.motomamiui.Controllers;

import com.example.motomamiui.DatabaseConnector;
import com.example.motomamiui.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class UserPerfilController {
    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellidos;

    @FXML
    private DatePicker dpFechaNacimiento;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtCarnetDNI;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTipoVehiculo;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtModelo;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ComboBox<String> cbSexo;

    @FXML
    private Button btnGuardarCambios;
    private UserSession userSession; // 用户会话

    // 初始化方法，在页面加载时调用
    @FXML
    public void initialize() {
        userSession = UserSession.getInstance("UserID"); // 获取用户会话实例
        loadUserProfile(); // 加载用户个人资料
    }

    // 加载用户个人资料
    private void loadUserProfile() {
        // 从数据库查询用户信息
        try (Connection conn = DatabaseConnector.connect()) {
            String query = "SELECT * FROM User WHERE UserID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, userSession.getUserId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                // 将查询结果填充到文本框、日期选择器和组合框中
                txtNombre.setText(rs.getString("FirstName"));
                txtApellidos.setText(rs.getString("LastName"));
                dpFechaNacimiento.setValue(rs.getDate("DateOfBirth").toLocalDate());
                txtDireccion.setText(rs.getString("Address"));
                txtTelefono.setText(rs.getString("Phone"));
                txtCarnetDNI.setText(rs.getString("LicenseOrID"));
                txtEmail.setText(rs.getString("Email"));
                txtTipoVehiculo.setText(rs.getString("VehicleType"));
                txtMatricula.setText(rs.getString("LicensePlate"));
                txtMarca.setText(rs.getString("Brand"));
                txtModelo.setText(rs.getString("Model"));
                txtPassword.setText(rs.getString("Password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 处理保存按钮的点击事件
    @FXML
    private void handleGuardarCambios() {
        // 从文本框、日期选择器和组合框中获取新值
        String newFirstName = txtNombre.getText();
        String newLastName = txtApellidos.getText();
        LocalDate newDateOfBirth = dpFechaNacimiento.getValue();
        String newAddress = txtDireccion.getText();
        String newPhone = txtTelefono.getText();
        String newLicenseOrID = txtCarnetDNI.getText();
        String newEmail = txtEmail.getText();
        String newVehicleType = txtTipoVehiculo.getText();
        String newLicensePlate = txtMatricula.getText();
        String newBrand = txtMarca.getText();
        String newModel = txtModelo.getText();
        String newPassword = txtPassword.getText();
        String newGender = cbSexo.getValue();

        // 更新数据库中的用户信息
        try (Connection conn = DatabaseConnector.connect()) {
            String query = "UPDATE User SET FirstName = ?, LastName = ?, DateOfBirth = ?, Address = ?, Phone = ?, LicenseOrID = ?, Email = ?, VehicleType = ?, LicensePlate = ?, Brand = ?, Model = ?, UserPassword = ?, Gender = ? WHERE UserID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, newFirstName);
            statement.setString(2, newLastName);
            statement.setDate(3, Date.valueOf(newDateOfBirth));
            statement.setString(4, newAddress);
            statement.setString(5, newPhone);
            statement.setString(6, newLicenseOrID);
            statement.setString(7, newEmail);
            statement.setString(8, newVehicleType);
            statement.setString(9, newLicensePlate);
            statement.setString(10, newBrand);
            statement.setString(11, newModel);
            statement.setString(12, newPassword);
            statement.setString(13, newGender);
            statement.setString(14, userSession.getUserId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows > 0) {
                // 更新成功，可以进行其他操作，比如显示成功消息
                // 显示成功消息
                showAlert("Change Data", "Your Information has been updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 处理返回按钮的点击事件
    @FXML
    private void handleReturnButton(ActionEvent event) {
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

    // 显示警告对话框的辅助方法
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
