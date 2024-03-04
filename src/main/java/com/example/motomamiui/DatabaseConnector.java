package com.example.motomamiui;

import java.sql.*;

public class DatabaseConnector {

    // 数据库URL，用户名和密码
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/motoui";
    private static final String DATABASE_USER = "Program";
    private static final String DATABASE_PASSWORD = "Cide2020";

    static {
        try {
            // 加载MySQL JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // 处理异常，可能是驱动未找到
        }
    }

    // 获取数据库连接
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    // 关闭数据库连接
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // 处理异常，可能是关闭连接失败
            }
        }
    }
    public static void getUserInfo(String usernameOrEmail) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connect();
            String sql = "SELECT * FROM users WHERE username = ? OR email = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, usernameOrEmail);
            statement.setString(2, usernameOrEmail);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

            }
        } finally {
            // 关闭连接和资源
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

}

