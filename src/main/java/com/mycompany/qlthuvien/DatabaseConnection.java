package com.mycompany.qlthuvien;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QLThuVien;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";

    private static DatabaseConnection instance; // Thể hiện duy nhất của lớp
    private Connection connection;

    // Private constructor để ngăn việc tạo đối tượng bên ngoài
    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối thành công!");
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại!");
            connection = null;
        }
    }

    // Phương thức getInstance() đảm bảo chỉ có một thể hiện duy nhất
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                instance = null; // Giải phóng thể hiện Singleton
                System.out.println("Đã đóng kết nối!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
