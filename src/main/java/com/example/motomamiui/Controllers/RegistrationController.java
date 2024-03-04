package com.example.motomamiui.Controllers;

import com.example.motomamiui.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class RegistrationController {
    @FXML
    private Button backButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private DatePicker dateOfBirthPicker;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField licenseOrIDField;
    @FXML
    private TextField drivingLicenseNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private ComboBox<String> vehicleTypeComboBox;
    @FXML
    private TextField licensePlateField;
    @FXML
    private TextField vehicleBrandField;
    @FXML
    private TextField vehicleModelField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> genderComboBox;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");



    // 方法来处理注册


    @FXML
    private void initialize() {
        // 设置日期选择器的日期格式
        dateOfBirthPicker.setConverter(new StringConverter<>() {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                }
                return null;
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                }
                return null;
            }
        });
    }

    @FXML
    private void registro() {
        // 检查所有必填字段是否为空
        if (!checkRequiredFields()) {
            showAlert("Error", "Missing Information", "Please fill in all required fields.");
            return;
        }

        // 检查邮箱格式是否正确
        if (!isValidEmail(emailField.getText())) {
            showAlert("Error", "Invalid Email", "Please enter a valid email address.");
            return;
        }

        // 检查用户年龄和车辆类型
        if (!checkAgeAndVehicleType()) {
            showAlert("Error", "Invalid Age or Vehicle Type", "Please make sure you meet the age requirements for the selected vehicle type.");
            return;
        }

        // 检查用户性别
        String selectedGender = genderComboBox.getValue();
        if (!"Mujer".equals(selectedGender)) {
            showAlert("Error", "Invalid Gender", "This app is only for women.");
            return;
        }

        // 检查驾驶执照类型
        String drivingLicenseType = drivingLicenseNumberField.getText().isEmpty() ? "" : "Type obtained from license number"; // 获取驾驶执照类型的逻辑需要根据实际情况实现

        String sql = "INSERT INTO User (FirstName, LastName, DateOfBirth, Address, Phone, LicenseOrID, LicenseType, Email, VehicleType, LicensePlate, Brand, Model, Password, Gender) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nameField.getText());
            ps.setString(2, surnameField.getText());
            ps.setDate(3, java.sql.Date.valueOf(dateOfBirthPicker.getValue()));
            ps.setString(4, addressField.getText());
            ps.setString(5, phoneNumberField.getText());
            ps.setString(6, licenseOrIDField.getText());
            ps.setString(7, drivingLicenseType); // 添加驾驶执照类型
            ps.setString(8, emailField.getText());
            ps.setString(9, vehicleTypeComboBox.getValue() != null ? vehicleTypeComboBox.getValue() : "");
            ps.setString(10, licensePlateField.getText().isEmpty() ? null : licensePlateField.getText());
            ps.setString(11, vehicleBrandField.getText().isEmpty() ? null : vehicleBrandField.getText());
            ps.setString(12, vehicleModelField.getText().isEmpty() ? null : vehicleModelField.getText());
            ps.setString(13, passwordField.getText()); // 注意：实际应用中应该对密码进行加密处理
            ps.setString(14, selectedGender);

            ps.executeUpdate();

            showAlert("Success", "Registration Successful", "You have successfully registered.");
            backToLogIn();

        } catch (SQLException e) {
            e.printStackTrace();
            // 处理异常，可能是数据库连接问题或SQL语句问题
            showAlert("Error", "Registration Failed", "An error occurred while registering. Please try again later.");
        }
    }

    // 检查所有必填字段是否为空
    private boolean checkRequiredFields() {
        return !nameField.getText().isEmpty() &&
                !surnameField.getText().isEmpty() &&
                dateOfBirthPicker.getValue() != null &&
                !addressField.getText().isEmpty() &&
                !phoneNumberField.getText().isEmpty() &&
                !licenseOrIDField.getText().isEmpty() &&
                !emailField.getText().isEmpty() &&
                genderComboBox.getValue() != null &&
                !passwordField.getText().isEmpty();
    }

    // 检查用户年龄和车辆类型是否满足要求
    private boolean checkAgeAndVehicleType() {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = dateOfBirthPicker.getValue();
        int age = currentDate.getYear() - birthDate.getYear();

        // 根据车辆类型验证年龄
        String selectedVehicleType = vehicleTypeComboBox.getValue();
        if ("Coche".equals(selectedVehicleType) || "Moto".equals(selectedVehicleType) || "Camion".equals(selectedVehicleType)) {
            return age >= 18; // 18岁以上可注册汽车、摩托车和货车
        } else if ("Bici".equals(selectedVehicleType) || "patinete".equals(selectedVehicleType)) {
            return age >= 16; // 16岁以上可注册自行车和电动滑板车
        }
        return false;
    }

    // 检查邮箱格式是否正确
    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    // 显示警报对话框
    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    protected void backToLogIn() {
        // 获取当前界面的Stage对象
        Stage currentStage = (Stage) backButton.getScene().getWindow();

        try {
            // 创建新的Stage对象来显示登录界面
            Stage loginStage = new Stage();

            // 加载登录界面的FXML文件
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/motomamiui/Views/logInUser.fxml"));
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
}
