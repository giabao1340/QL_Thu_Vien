/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends JFrame {

    private JTextField emailText;
    private JPasswordField passwordText;

    public LoginForm() {
        // Thiết lập tiêu đề và kích thước cho form login
        setTitle("Login Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Hiển thị form ở giữa màn hình

        // Tạo panel và thêm các thành phần giao diện
        JPanel panel = new GradientPanel();
        panel.setLayout(null);
        add(panel);

        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        // Tạo tiêu đề
        JLabel titleLabel1 = new JLabel("HUFLIT - LIBRARY");
        titleLabel1.setBounds(100, 10, 200, 30);
        titleLabel1.setForeground(new Color(33, 150, 243)); // Blue color
        titleLabel1.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel1);

        JLabel titleLabel2 = new JLabel("ĐĂNG NHẬP");
        titleLabel2.setBounds(150, 40, 200, 25);
        titleLabel2.setForeground(new Color(96, 125, 139)); // Darker gray
        titleLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(titleLabel2);

        // Tạo JLabel và JTextField cho Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 80, 80, 25);
        emailLabel.setForeground(new Color(33, 33, 33)); // Dark gray
        panel.add(emailLabel);

        emailText = new JTextField(20);
        emailText.setBounds(150, 80, 200, 25);
        emailText.setBackground(Color.WHITE);
        emailText.setForeground(new Color(33, 33, 33)); // Dark gray
        emailText.setBorder(new LineBorder(new Color(224, 224, 224), 1));
        panel.add(emailText);

        // Tạo JLabel và JPasswordField cho Mật khẩu
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 120, 80, 25);
        passwordLabel.setForeground(new Color(33, 33, 33)); // Dark gray
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 120, 200, 25);
        passwordText.setBackground(Color.WHITE);
        passwordText.setForeground(new Color(33, 33, 33)); // Dark gray
        passwordText.setBorder(new LineBorder(new Color(224, 224, 224), 1));
        panel.add(passwordText);

        // Tạo nút Login
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 170, 100, 35);
        loginButton.setBackground(new Color(33, 150, 243)); // Blue color
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(new LineBorder(new Color(33, 150, 243), 1));
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // TODO add your handling code here:
                JOptionPane panel = new JOptionPane();
                String email = emailText.getText();
                String password = new String(passwordText.getPassword());

                // Xử lý đăng nhập
                if (login(email, password)) {
                    JOptionPane.showMessageDialog(panel, "Login successful!");
                    // Điều hướng tới trang chủ
                    HomePage homePage = new HomePage();
                    homePage.setVisible(true);
                    dispose(); // Đóng form login
                } else {
                    JOptionPane.showMessageDialog(panel, "Invalid email or password.");
                }
            }
        });
        panel.add(loginButton);

        // Tạo đường viền cho panel
        panel.setBorder(new LineBorder(new Color(224, 224, 224), 1));
    }

    public boolean login(String username, String password) {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "SELECT COUNT(1) FROM TaiKhoan WHERE Email = ? AND MatKhau = ?";

        try {
            if (connection != null) {
                // Tạo PreparedStatement
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                // Thực thi truy vấn
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally { 
            // Đóng các tài nguyên
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
        } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static void main(String[] args) {
        // Hiển thị form login
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });
    }

    // Panel with gradient background
    static class GradientPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(255, 255, 255), // Top color
                    0, getHeight(), new Color(230, 240, 255) // Bottom color
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
