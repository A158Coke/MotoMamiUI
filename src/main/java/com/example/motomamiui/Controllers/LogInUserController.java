package com.example.motomamiui.Controllers;

import com.example.motomamiui.DatabaseConnector;
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
import com.example.motomamiui.UserSession;


public class LogInUserController {
    // 属性名和FXML中的元素ID匹配
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Button forgotPasswordButton;
    @FXML
    private Button backButton;

    @FXML
    private void logInMethod() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Login Error", "Email or Password cannot be empty.");
            return;
        }

        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password); // 注意：实际应用中应使用加密后的密码

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // 登录成功
                    String userId = rs.getString("userId"); // 从数据库获取用户ID
                    UserSession.getInstance(userId); // 创建或更新用户会话
                    gotoMenu();
                } else {
                    // 登录失败
                    showAlert("Login Failed", "Invalid email or password.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error accessing the database: " + e.getMessage());
        }
    }
    // 转到注册页面方法
    @FXML
    protected void goSignUpPage() {
        // 获取当前界面的Stage对象
        Stage currentStage = (Stage) loginButton.getScene().getWindow();

        try {
            // 创建新的Stage对象来显示注册界面
            Stage signUpStage = new Stage();

            // 加载注册界面的FXML文件
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/motomamiui/Views/SignUpUser.fxml"));
            Parent root = loader.load();

            // 设置新窗口的Scene
            Scene scene = new Scene(root);
            signUpStage.setScene(scene);

            // 显示新窗口
            signUpStage.show();

            // 关闭当前窗口
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected  void gotoMenu(){
        // 获取当前界面的Stage对象
        Stage currentStage = (Stage) loginButton.getScene().getWindow();

        try {
            // 创建新的Stage对象来显示MENU界面
            Stage signUpStage = new Stage();

            // 加载界面的FXML文件
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/motomamiui/Views/UserDashboard.fxml"));
            Parent root = loader.load();

            // 设置新窗口的Scene
            Scene scene = new Scene(root);
            signUpStage.setScene(scene);

            // 显示新窗口
            signUpStage.show();

            // 关闭当前窗口
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 恢复密码方法
    @FXML
    protected void recoverPassword() {
        // 获取当前界面的Stage对象
        Stage currentStage = (Stage) loginButton.getScene().getWindow();

        try {
            // 创建新的Stage对象来显示注册界面
            Stage signUpStage = new Stage();

            // 加载注册界面的FXML文件
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/motomamiui/Views/RecoverPass.fxml"));
            Parent root = loader.load();

            // 设置新窗口的Scene
            Scene scene = new Scene(root);
            signUpStage.setScene(scene);

            // 显示新窗口
            signUpStage.show();

            // 关闭当前窗口
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 返回上一页方法
    @FXML
    protected void moveBackToLastPage() {
        System.out.println("moveBackToLastPage method called"); // 确保方法被调用
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        try {
            // 创建新的Stage对象来显示登录界面
            Stage loginStage = new Stage();

            // 加载界面的FXML文件
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

    // 显示警告对话框的辅助方法
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
