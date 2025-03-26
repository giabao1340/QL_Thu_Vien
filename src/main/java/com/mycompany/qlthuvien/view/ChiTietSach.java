/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qlthuvien.view;

import com.mycompany.qlthuvien.DatabaseConnection;
import com.mycompany.qlthuvien.command.CommandManager;
import com.mycompany.qlthuvien.command.DeleteBookCommand;
import com.mycompany.qlthuvien.dao.BookDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author luong
 */
public class ChiTietSach extends javax.swing.JFrame {

    private int bookID;
    private BookForm sachForm; // Reference to BookForm
    private BookDAO bookDAO;

    public ChiTietSach(BookForm sachForm, int bookID, String bookName, String authorName,
            int publishYear, String publisher, float price, String category, String description) {
        initComponents();
        design();
        setLocationRelativeTo(null);
        this.sachForm = sachForm;
        this.bookID = bookID;

        // Set information to UI components
        txtMaSach.setText(String.valueOf(bookID));
        txtTenSach.setText(bookName);
        txtTacGia.setText(authorName);
        txtNamPH.setText(String.valueOf(publishYear));
        txtNXB.setText(publisher);
        txtGia.setText(String.format("%.2f", price));
        cbCategory.setSelectedItem(category);
        txtMoTa.setText(description);

        // Load book image
        byte[] imageBytes = getBookImage(bookID);
        loadCategories();
        setSelectedCategory(category);
        displayBookImage(imageBytes);
        sachForm.loadData();
    }
//        public ChiTietSach(HomePage homePage, int bookID, String bookName, String authorName,
//            int publishYear, String publisher, float price, String category, String description) {
//        initComponents();
//        design();
//        setLocationRelativeTo(null);
//        this.sachForm = sachForm;
//        this.bookID = bookID;
//
//        // Set information to UI components
//        txtMaSach.setText(String.valueOf(bookID));
//        txtTenSach.setText(bookName);
//        txtTacGia.setText(authorName);
//        txtNamPH.setText(String.valueOf(publishYear));
//        txtNXB.setText(publisher);
//        txtGia.setText(String.format("%.2f", price));
//        cbCategory.setSelectedItem(category);
//        txtMoTa.setText(description);
//
//        // Load book image
//        byte[] imageBytes = getBookImage(bookID);
//        loadCategories();
//        setSelectedCategory(category);
//        displayBookImage(imageBytes);
//        homePage.loadData();
//    }
    private void design(){
        //Title
        jLabelTitle.setHorizontalAlignment(JLabel.CENTER);
        jLabelTitle.setVerticalAlignment(JLabel.CENTER);
        jLabelTitle.setPreferredSize(new Dimension(0, 100));
        
        //Button
            buttonUpdate.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                buttonUpdate.setBackground(new Color(100, 149, 237)); // Change color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonUpdate.setBackground(new Color(102, 153, 255)); // Reset to original color
            }
        });
            
            buttonDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonDelete.setBackground(new Color(205, 102, 102)); // Change color on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                buttonDelete.setBackground(new Color(255, 153, 153)); // Reset to original color
            }
        });
            buttonUpload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonUpload.setBackground(new Color(0, 204, 51)); // Change color on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                buttonUpload.setBackground(new Color(102, 255, 102)); // Reset to original color
            }
        });
            jButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButton1.setBackground(new Color(153, 51, 255)); // Change color on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jButton1.setBackground(new Color(153, 102, 255)); // Reset to original color
            }
        });
           
    }

    private byte[] getBookImage(int bookID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        byte[] imageBytes = null;

        try {
            // Establish database connection
            DatabaseConnection db = DatabaseConnection.getInstance();
            conn = db.getConnection();

            // SQL query to get the image of the book
            String sql = "SELECT Hinh FROM Sach WHERE MaSach = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                imageBytes = rs.getBytes("Hinh");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return imageBytes;
    }

    private void displayBookImage(byte[] imageBytes) {
        if (imageBytes != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                jLabelImage.setIcon(imageIcon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            jLabelImage.setIcon(null);
        }
    }

    private void loadCategories() {
        try {
            // Establish database connection
            DatabaseConnection db = DatabaseConnection.getInstance();
            try (Connection conn = db.getConnection()) {
                String sql = "SELECT TenTheLoai FROM TheLoai";
                try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

                    cbCategory.removeAllItems(); // Clear existing items

                    // Add each category to the combobox
                    while (rs.next()) {
                        String category = rs.getString("TenTheLoai");
                        cbCategory.addItem(category);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load categories", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setSelectedCategory(String selectedCategory) {
        for (int i = 0; i < cbCategory.getItemCount(); i++) {
            if (cbCategory.getItemAt(i).equals(selectedCategory)) {
                cbCategory.setSelectedIndex(i);
                break;
            }
        }
    }
//
//    private int checkOrInsertCategory(Connection conn, String category) throws SQLException {
//        String checkQuery = "SELECT MaTheLoai FROM LoaiSach WHERE TenTheLoai = ?";
//        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
//            checkStmt.setString(1, category);
//            ResultSet rs = checkStmt.executeQuery();
//            if (rs.next()) {
//                return rs.getInt("MaTheLoai");
//            }
//        }
//
//        String insertQuery = "INSERT INTO LoaiSach (TenTheLoai) VALUES (?)";
//        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
//            insertStmt.setString(1, category);
//            insertStmt.executeUpdate();
//            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                return generatedKeys.getInt(1);
//            }
//        }
//        return -1; // Indicates failure
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        image1 = new jimag.Image();
        image2 = new jimag.Image();
        image3 = new jimag.Image();
        image4 = new jimag.Image();
        image5 = new jimag.Image();
        image6 = new jimag.Image();
        image7 = new jimag.Image();
        image8 = new jimag.Image();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        txtNamPH = new javax.swing.JTextField();
        txtTenSach = new javax.swing.JTextField();
        txtTacGia = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        buttonUpdate = new javax.swing.JButton();
        buttonDelete = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtNXB = new javax.swing.JTextField();
        buttonUpload = new javax.swing.JButton();
        jLabelImage = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMoTa = new javax.swing.JTextField();
        cbCategory = new javax.swing.JComboBox<>();
        jLabelTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Tên sách:");

        jLabel3.setText("Mã sách:");

        jLabel4.setText("Tác giả:");

        jLabel5.setText("Năm phát hành:");

        jLabel6.setText("NXB:");

        txtNamPH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamPHActionPerformed(evt);
            }
        });

        txtTenSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSachActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(153, 102, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Close");
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        buttonUpdate.setBackground(new java.awt.Color(102, 102, 255));
        buttonUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        buttonUpdate.setForeground(new java.awt.Color(255, 255, 255));
        buttonUpdate.setText("Update");
        buttonUpdate.setBorder(null);
        buttonUpdate.setBorderPainted(false);
        buttonUpdate.setDefaultCapable(false);
        buttonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUpdateActionPerformed(evt);
            }
        });

        buttonDelete.setBackground(new java.awt.Color(255, 102, 102));
        buttonDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        buttonDelete.setForeground(new java.awt.Color(255, 255, 255));
        buttonDelete.setText("Delete");
        buttonDelete.setBorderPainted(false);
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });

        jLabel7.setText("Giá");

        buttonUpload.setBackground(new java.awt.Color(102, 255, 102));
        buttonUpload.setText("Upload New Image");
        buttonUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUploadActionPerformed(evt);
            }
        });

        jLabelImage.setBackground(new java.awt.Color(204, 204, 204));
        jLabelImage.setForeground(new java.awt.Color(102, 102, 102));
        jLabelImage.setOpaque(true);

        jLabel8.setText("Thể loại");

        jLabel9.setText("Mô tả");

        cbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoryActionPerformed(evt);
            }
        });

        jLabelTitle.setBackground(new java.awt.Color(255, 153, 153));
        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setText("CHI TIẾT SÁCH");
        jLabelTitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabelTitle.setDisplayedMnemonicIndex(0);
        jLabelTitle.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtMoTa)
                                .addComponent(txtGia)
                                .addComponent(txtMaSach)
                                .addComponent(txtTenSach)
                                .addComponent(txtTacGia)
                                .addComponent(txtNamPH)
                                .addComponent(txtNXB)
                                .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(buttonUpload))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(buttonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonDelete)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelTitle)
                .addGap(27, 27, 27)
                .addComponent(jLabelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonUpload)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNamPH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTenSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSachActionPerformed

    private void buttonUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUploadActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage img = ImageIO.read(selectedFile);
                // Resize the image to fit the label
                Image scaledImg = img.getScaledInstance(jLabelImage.getWidth(), jLabelImage.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(scaledImg);
                jLabelImage.setIcon(imageIcon);
                jLabelImage.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to load image", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_buttonUploadActionPerformed

    private void txtNamPHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamPHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamPHActionPerformed

    private void cbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoryActionPerformed

    }//GEN-LAST:event_cbCategoryActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void buttonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUpdateActionPerformed
        // Lấy thông tin từ các trường nhập liệu
        String bookName = txtTenSach.getText();
        String authorName = txtTacGia.getText();

        int publishYear;
        try {
            publishYear = Integer.parseInt(txtNamPH.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid publish year format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String publisher = txtNXB.getText();
        String priceText = txtGia.getText();
        String desBook = txtMoTa.getText();
        double price;

        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy mã thể loại từ combobox
        String selectedCategory = (String) cbCategory.getSelectedItem();
        int categoryId = getCategoryId(selectedCategory);
        if (categoryId == -1) {
            JOptionPane.showMessageDialog(this, "Selected category not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Xử lý hình ảnh
        ImageIcon imageIcon = (ImageIcon) jLabelImage.getIcon();
        byte[] imageBytes = null;

        if (imageIcon != null) {
            BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bufferedImage.createGraphics();
            g2.drawImage(imageIcon.getImage(), 0, 0, null);
            g2.dispose();

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                ImageIO.write(bufferedImage, "jpg", baos);
                baos.flush();
                imageBytes = baos.toByteArray();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // Cập nhật thông tin sách vào cơ sở dữ liệu
        DatabaseConnection db = DatabaseConnection.getInstance();;
        try (Connection conn = db.getConnection()) {
            String sql = "UPDATE Sach SET TenSach = ?, TenTacGia = ?, NamNXB = ?, NXB = ?, GiaSach = ?, Hinh = ?, MoTaSach = ?, MaTheLoai = ? WHERE MaSach = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, bookName);
                pstmt.setString(2, authorName);
                pstmt.setInt(3, publishYear);
                pstmt.setString(4, publisher);
                pstmt.setDouble(5, price);
                pstmt.setBytes(6, imageBytes);
                pstmt.setString(7, desBook);
                pstmt.setInt(8, categoryId); // Cập nhật MaTheLoai
                pstmt.setInt(9, bookID); // Cập nhật MaSach

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Book updated successfully!");
                    dispose(); // Đóng form
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update book", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update book", "Error", JOptionPane.ERROR_MESSAGE);
        }
        sachForm.loadData(); // Refresh the table after deletion
        }

        // Phương thức lấy ID thể loại từ tên thể loại
        private int getCategoryId(String categoryName) {
            int categoryId = -1;
            DatabaseConnection db = DatabaseConnection.getInstance();;
            try (Connection conn = db.getConnection()) {
                String sql = "SELECT MaTheLoai FROM TheLoai WHERE TenTheLoai = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, categoryName);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            categoryId = rs.getInt("MaTheLoai");
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return categoryId;
    }//GEN-LAST:event_buttonUpdateActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        BookDAO bookDAO = new BookDAO();

        // Xác nhận trước khi xóa
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sách này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        // Thực hiện lệnh xóa
        DeleteBookCommand command = new DeleteBookCommand(bookDAO, bookID);
        CommandManager.getInstance().executeCommand(command);

        JOptionPane.showMessageDialog(this, "Sách đã bị xóa!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        this.dispose(); // Đóng ChiTietSach
        sachForm.loadData();

    }//GEN-LAST:event_buttonDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // Set the Nimbus look and feel
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonUpdate;
    private javax.swing.JButton buttonUpload;
    private javax.swing.JComboBox<String> cbCategory;
    private jimag.Image image1;
    private jimag.Image image2;
    private jimag.Image image3;
    private jimag.Image image4;
    private jimag.Image image5;
    private jimag.Image image6;
    private jimag.Image image7;
    private jimag.Image image8;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtMoTa;
    private javax.swing.JTextField txtNXB;
    private javax.swing.JTextField txtNamPH;
    private javax.swing.JTextField txtTacGia;
    private javax.swing.JTextField txtTenSach;
    // End of variables declaration//GEN-END:variables

//private int checkOrInsertCategory(Connection conn, String category) throws SQLException {
//    int categoryId = -1;
//    
//    // Check if the category already exists
//    String checkSql = "SELECT MaTheLoai FROM LoaiSach WHERE TenTheLoai = ?";
//    try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
//        checkStmt.setString(1, category);
//        try (ResultSet rs = checkStmt.executeQuery()) {
//            if (rs.next()) {
//                // Category exists
//                categoryId = rs.getInt("MaTheLoai");
//            } else {
//                // Category does not exist, insert it
//                String insertSql = "INSERT INTO LoaiSach (TenTheLoai) VALUES (?)";
//                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
//                    insertStmt.setString(1, category);
//                    insertStmt.executeUpdate();
//
//                    // Get the generated category ID
//                    try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
//                        if (generatedKeys.next()) {
//                            categoryId = generatedKeys.getInt(1);
//                        }
//                    }
//                }
//            }
//        }
//    }
//    return categoryId;
//}
}
