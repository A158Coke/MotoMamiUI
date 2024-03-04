package com.example.motomamiui.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SeguroValorController {

    @FXML
    private void handleVolverAlMenu(ActionEvent event) throws IOException {
        // 获取当前窗口的 Stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // 加载 UserDashboard.fxml 文件
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/motomamiui/Views/UserDashboard.fxml"));

        // 创建一个新的 Scene
        Scene scene = new Scene(root);

        // 将新的 Scene 设置到 Stage 上
        stage.setScene(scene);

        // 显示 Stage
        stage.show();
    }
}
