package com.example.motomamiui.Controllers;

import com.example.motomamiui.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class UserDashboardController {
    @FXML
    private Button btnUserProfile;

    public void handleVerSeguro() {
        loadFXML("SeguroValor.fxml", (Stage) btnUserProfile.getScene().getWindow());
    }

    public void handleRenovarSeguro() {
        // 实现逻辑以显示续保页面
    }

    public void handleVerInformes() {
        // 实现逻辑以显示事故报告页面
        loadFXML("ConsultaReport.fxml",(Stage) btnUserProfile.getScene().getWindow());
    }

    public void handleCrearInforme() {
        // 实现逻辑以显示创建事故报告页面
        loadFXML("AccidentReport.fxml",(Stage) btnUserProfile.getScene().getWindow());
    }

    public void handleVerFacturas() {
        // 实现逻辑以显示发票历史页面
        loadFXML("Factura.fxml",(Stage) btnUserProfile.getScene().getWindow());
    }

    public void handleCerrarSesion() {
        // 实现逻辑以关闭会话或返回登录页面
        UserSession.clearInstance();
        loadFXML("mode.fxml",(Stage) btnUserProfile.getScene().getWindow());
    }

    public void goToPerfil(){
        loadFXML("UserPerfil.fxml", (Stage) btnUserProfile.getScene().getWindow());
    }

    private void loadFXML(String fxmlFileName, Stage currentStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/motomamiui/Views/" + fxmlFileName));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // 关闭当前窗口
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
