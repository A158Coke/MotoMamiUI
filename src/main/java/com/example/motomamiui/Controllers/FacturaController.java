package com.example.motomamiui.Controllers;

import com.example.motomamiui.DatabaseConnector;
import com.example.motomamiui.FacturaModel;
import com.example.motomamiui.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class FacturaController {
    @FXML
    private TableView<FacturaModel> tableView; // 修改为使用FacturaModel
    private UserSession userSession; // 用户会话

    @FXML
    private void initialize() {
        // 初始化表格列并设置cellValueFactory
        setUpTableColumns();

        // 调用加载数据方法
        loadData();
    }

    private void setUpTableColumns() {
        TableColumn<FacturaModel, Number> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<FacturaModel, String> fechaColumn = new TableColumn<>("Fecha");
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<FacturaModel, String> nombreEmpresaColumn = new TableColumn<>("Nombre Empresa");
        nombreEmpresaColumn.setCellValueFactory(new PropertyValueFactory<>("insuranceCompany"));

        TableColumn<FacturaModel, String> cifEmpresaColumn = new TableColumn<>("CIF Empresa");
        cifEmpresaColumn.setCellValueFactory(new PropertyValueFactory<>("companyCIF"));

        TableColumn<FacturaModel, String> direccionEmpresaColumn = new TableColumn<>("Dirección Empresa");
        direccionEmpresaColumn.setCellValueFactory(new PropertyValueFactory<>("companyAddress"));

        TableColumn<FacturaModel, String> nombreUsuarioColumn = new TableColumn<>("Nombre Usuario");
        nombreUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<FacturaModel, String> apellidosUsuarioColumn = new TableColumn<>("Apellidos Usuario");
        apellidosUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<FacturaModel, String> direccionUsuarioColumn = new TableColumn<>("Dirección Usuario");
        direccionUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("userAddress"));

        TableColumn<FacturaModel, String> tipoSeguroColumn = new TableColumn<>("Tipo Seguro");
        tipoSeguroColumn.setCellValueFactory(new PropertyValueFactory<>("insuranceType"));

        TableColumn<FacturaModel, String> tipoVehiculoColumn = new TableColumn<>("Tipo de Vehículo");
        tipoVehiculoColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));

        TableColumn<FacturaModel, String> fechaRegistroColumn = new TableColumn<>("Fecha de Registro");
        fechaRegistroColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn<FacturaModel, String> fechaFinContratoColumn = new TableColumn<>("Fecha de Fin de Contrato");
        fechaFinContratoColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        TableColumn<FacturaModel, Number> costeColumn = new TableColumn<>("Coste");
        costeColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        TableColumn<FacturaModel, Number> ivaColumn = new TableColumn<>("IVA");
        ivaColumn.setCellValueFactory(new PropertyValueFactory<>("vat"));

        tableView.getColumns().addAll(
                idColumn, fechaColumn, nombreEmpresaColumn, cifEmpresaColumn,
                direccionEmpresaColumn, nombreUsuarioColumn, apellidosUsuarioColumn,
                direccionUsuarioColumn, tipoSeguroColumn, tipoVehiculoColumn,
                fechaRegistroColumn, fechaFinContratoColumn, costeColumn, ivaColumn);
    }

    private void loadData() {
        userSession = UserSession.getInstance("UserID"); // 获取用户会话实例
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT \n" +
                     "    f.ID, f.Date, f.InsuranceCompany, f.VAT, f.TotalCost, \n" +
                     "    u.FirstName, u.LastName, u.Address AS UserAddress, u.VehicleType,\n" +
                     "    ip.InsuranceType, ip.Price AS Cost, ip.StartDate, ip.EndDate\n" +
                     "FROM \n" +
                     "    Factura f \n" +
                     "JOIN \n" +
                     "    User u ON f.UserID = u.UserID\n" +
                     "JOIN \n" +
                     "    InsurancePolicy ip ON f.UserID = ip.UserID\n" +
                     "WHERE u.UserID = ?")) {

            // 设置当前用户的UserID
            pstmt.setString(1, userSession.getUserId());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // 创建FacturaModel对象，并将其添加到表格视图
                FacturaModel factura = new FacturaModel(
                        rs.getInt("ID"),
                        rs.getString("Date"),
                        rs.getString("InsuranceCompany"),
                        rs.getDouble("VAT"),
                        rs.getDouble("TotalCost"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("UserAddress"),
                        rs.getString("VehicleType"),
                        rs.getString("InsuranceType"),
                        rs.getString("StartDate"),
                        rs.getString("EndDate"),
                        rs.getDouble("Cost")
                );
                tableView.getItems().add(factura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: 更好的异常处理
        }
    }

    @FXML
    protected void returnButtonClicked(ActionEvent event) {
        // 处理返回按钮点击事件的逻辑
        // 例如：返回上一个界面或执行其他操作
        try {
            // 获取当前窗口的 Stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 加载 UserDashboard.fxml 文件
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/motomamiui/Views/UserDashboard.fxml")));

            // 创建一个新的 Scene
            Scene scene = new Scene(root);

            // 将新的 Scene 设置到当前的 Stage 上
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
