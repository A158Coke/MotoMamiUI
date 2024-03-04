package com.example.motomamiui.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RecoverPassController {
    @FXML
    protected TextField userEmail;
    @FXML
    protected TextField newPass;
    @FXML
    protected TextField confirmNewPass;
    @FXML
    protected Button submit;

    @FXML
    protected void recoverPassAction() {
        String email = userEmail.getText();
        String newPassword = newPass.getText();
        String confirmNewPassword = confirmNewPass.getText();

        // 检查输入是否为空
        if (email.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            showAlert("Recover Password Error", "Please fill in all fields.");
            return;
        }

        // 检查新密码和确认密码是否匹配
        if (!newPassword.equals(confirmNewPassword)) {
            showAlert("Recover Password Error", "New password and confirm password do not match.");
            return;
        }

        // 显示密码已重置消息
        showAlert("Password Reset", "Your password has been reset successfully.");

        // 清空输入字段
        userEmail.clear();
        newPass.clear();
        confirmNewPass.clear();
    }

    // 返回上一页方法
    @FXML
    protected void goBackToLoginPage() {
        // 在这里编写返回上一页的逻辑
        // 获取当前界面的Stage对象
        Stage currentStage = (Stage) submit.getScene().getWindow();

        try {
            // 创建新的Stage对象来显示登录界面
            Stage loginStage = new Stage();

            // 加载登录界面的FXML文件
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/motomamiui/Views/LogInUser.fxml"));
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
