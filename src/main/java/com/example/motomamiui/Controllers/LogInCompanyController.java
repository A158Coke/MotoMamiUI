package com.example.motomamiui.Controllers;

import com.example.motomamiui.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInCompanyController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    protected Button backButton;

    // 返回上一页的功能
    @FXML
    protected void handleBack() {
        System.out.println("move back");
        // 在这里编写返回上一页的逻辑
        // 获取当前界面的Stage对象
        Stage currentStage = (Stage) loginButton.getScene().getWindow();

        try {
            // 创建新的Stage对象来显示登录界面
            Stage loginStage = new Stage();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/motomamiui/Views/mode.fxml"));
            Parent root = loader.load();

            // 设置新窗口的Scene
            Scene scene = new Scene(root);
            loginStage.setScene(scene);

            // 显示新窗口
            loginStage.show();

            // 关闭当前窗口
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (validateLogin(email, password)) {
            // 登录成功，导航到 CompanyDashboard.fxml 页面
            // 加载公司登入页面
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/motomamiui/Views/CompanyDashboard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // 获取当前舞台
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // 设置新的场景
            stage.setScene(scene);
            stage.show();
            // 你需要编写导航的代码
        } else {
            // 登录失败，可以添加一些提醒或者其他逻辑
            showAlert(Alert.AlertType.ERROR, "Error", "Error al acceder a la cuenta");
        }
    }

    private boolean validateLogin(String email, String password) {
        String sql = "SELECT * FROM Administrator WHERE Username = ? AND Password = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
