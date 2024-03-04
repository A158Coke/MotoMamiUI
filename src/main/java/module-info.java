module com.example.motomamiui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.motomamiui to javafx.fxml;
    exports com.example.motomamiui;
    exports com.example.motomamiui.Controllers;
    opens com.example.motomamiui.Controllers to javafx.fxml;
}