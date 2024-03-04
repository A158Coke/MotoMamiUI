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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.stage.Stage;
import javafx.util.Callback;

public class AccidentReportController {

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField descriptionField;
    @FXML
    private CheckBox damageFrontCheck;
    @FXML
    private CheckBox damageSideCheck;
    @FXML
    private CheckBox damageRearCheck;

    private UserSession userSession;

    @FXML
    private void initialize() {
        userSession = UserSession.getInstance("UserID"); // 获取用户会话实例
        datePicker.setDayCellFactory(createDayCellFactory());
    }

    private Callback<DatePicker, DateCell> createDayCellFactory() {
        return new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // 禁用所有在今天之后的日期
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;"); // 可选的样式
                        }
                    }
                };
            }
        };
    }

    @FXML
    private void handleGenerateReport() {
        LocalDate date = datePicker.getValue();
        String description = descriptionField.getText();
        boolean damageFront = damageFrontCheck.isSelected();
        boolean damageSide = damageSideCheck.isSelected();
        boolean damageRear = damageRearCheck.isSelected();
        String userId = userSession.getUserId(); // 获取当前用户ID

        // 检查所有必填字段是否已填写
        if (date == null || description.isEmpty() || (!damageFront && !damageSide && !damageRear)) {
            showAlert("Completa los campos necesarios");
            return;
        }

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO AccidentReport (UserID, Date, Description, DamageFront, DamageSide, DamageRear) VALUES (?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, userId);
            pstmt.setString(2, date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            pstmt.setString(3, description);
            pstmt.setBoolean(4, damageFront);
            pstmt.setBoolean(5, damageSide);
            pstmt.setBoolean(6, damageRear);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                showAlert("El parte de accidente ha sido generado correctamente.");
            } else {
                showAlert("No se pudo generar el parte de accidente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error al acceder a la base de datos.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
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
