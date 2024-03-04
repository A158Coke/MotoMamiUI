package com.example.motomamiui.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Mode_Controller {

    @FXML
    private void handleUserMode(ActionEvent event) throws IOException {
        // 加载用户登入页面
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/motomamiui/Views/LogInUser.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        // 获取当前舞台
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // 设置新的场景
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleCompanyMode(ActionEvent event) throws IOException {
        // 加载公司登入页面
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/motomamiui/Views/LogInCompany.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // 获取当前舞台
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // 设置新的场景
        stage.setScene(scene);
        stage.show();
    }
}
