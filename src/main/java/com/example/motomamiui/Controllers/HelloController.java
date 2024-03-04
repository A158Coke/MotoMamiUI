package com.example.motomamiui.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    protected  Button entrarButton;
    @FXML
    protected void onEntrarButtonClick() {
        try {
            // 加载新的FXML文件
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/motomamiui/Views/mode.fxml"));

            Parent root = loader.load();

            // 创建新的Stage和Scene
            Stage newStage = new Stage();
            newStage.setTitle("seleccion mode page");
            newStage.setScene(new Scene(root));



            // 获取当前按钮所在的 Stage
            Stage currentStage = (Stage) entrarButton.getScene().getWindow();

            // 关闭当前Stage
            currentStage.close();

            // 显示新的Stage
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}