/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qlthuvien;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lttha
 */
public class BookForm extends javax.swing.JFrame {

    private BookDAO bookDAO;

    public BookForm() {
        bookDAO = new BookDAO();
        initComponents();
        design();
        loadData();
        setLocationRelativeTo(null);
        jTableBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int selectedRow = jTableBook.getSelectedRow();
                if (selectedRow != -1) {
                    int bookID = (int) jTableBook.getValueAt(selectedRow, 0);
                    String bookName = (String) jTableBook.getValueAt(selectedRow, 1);
                    String category = (String) jTableBook.getValueAt(selectedRow, 2);
                    String authorName = (String) jTableBook.getValueAt(selectedRow, 3);
                    int publishYear = (int) jTableBook.getValueAt(selectedRow, 4);
                    String publisher = (String) jTableBook.getValueAt(selectedRow, 5);
                    Float price = (Float) jTableBook.getValueAt(selectedRow, 6);
                    String description = (String) jTableBook.getValueAt(selectedRow, 7);
                    ChiTietSach chiTietSachForm = new ChiTietSach(BookForm.this, bookID, bookName, authorName, publishYear, publisher, price, category, description);
                    chiTietSachForm.setVisible(true);
                }
            }
        });

        loadData();
        loadCategories();
    }
    private void design(){
        //Title
        jLabelTitle.setHorizontalAlignment(JLabel.CENTER);
        jLabelTitle.setVerticalAlignment(JLabel.CENTER);
        jLabelTitle.setPreferredSize(new Dimension(0, 100));
        // Table
                // Create a cell renderer to add color
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Add custom row colors
                if (row % 2 == 0) {
                    c.setBackground(new Color(220, 220, 220)); // Light gray
                } else {
                    c.setBackground(Color.WHITE); // White
                }

                // Set selected row color
                if (isSelected) {
                    c.setBackground(new Color(204, 204, 255));
                }

                return c;
            }
        };

        // Apply the cell renderer to each column
        for (int i = 0; i < jTableBook.getColumnCount(); i++) {
            jTableBook.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        //Button
            buttonSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonSave.setBackground(new Color(100, 149, 237)); // Change color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonSave.setBackground(new Color(102, 153, 255)); // Reset to original color
            }
        });
            
            buttonSaveCate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonSaveCate.setBackground(new Color(255,51,51)); // Change color on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                buttonSaveCate.setBackground(new Color(255,102,102)); // Reset to original color
            }
        });
            buttonUploadImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonUploadImage.setBackground(new Color(0, 204, 51)); // Change color on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                buttonUploadImage.setBackground(new Color(102, 255, 102)); // Reset to original color
            }
        });
            jButton3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButton3.setBackground(new Color(153, 51, 255)); // Change color on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jButton3.setBackground(new Color(153, 102, 255)); // Reset to original color
            }
        });
            buttonSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonSearch.setBackground(new Color(51, 51, 255)); // Change color on hover
            }
            @Override
                public void mouseExited(MouseEvent e) {
                buttonSearch.setBackground(new Color(51, 51, 255)); // Reset to original color
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jLabelTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTacGia = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNamPhatHanh = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        txtNXB = new javax.swing.JTextField();
        buttonSave = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableBook = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        buttonUploadImage = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtMoTa = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTheLoai = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        buttonSaveCate = new javax.swing.JButton();
        cbCategory = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        buttonSearch = new javax.swing.JButton();
        jLabelImage = new javax.swing.JLabel();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(668, 650));

        jLabelTitle.setBackground(new java.awt.Color(255, 102, 102));
        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setText("Quản Lý Sách");
        jLabelTitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelTitle.setOpaque(true);
        jLabelTitle.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                jLabelTitleComponentMoved(evt);
            }
        });

        jLabel2.setText("Nhập tên sách: ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setText("Add book");

        jLabel4.setText("Nhập tên tác giả");

        jLabel5.setText("Năm phát hành:");

        jLabel6.setText("Nhà xuất bản:");

        jLabel8.setText("Giá:");

        buttonSave.setBackground(new java.awt.Color(102, 102, 255));
        buttonSave.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonSave.setForeground(new java.awt.Color(255, 255, 255));
        buttonSave.setText("Save");
        buttonSave.setBorder(null);
        buttonSave.setBorderPainted(false);
        buttonSave.setDefaultCapable(false);
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });

        jTableBook.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTableBook.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sách", "Tên Sách", "Thể loại", "Tên Tác Giả", "Năm phát hành", "NXB", "Giá", "Mô tả"
            }
        ));
        jTableBook.setFocusable(false);
        jTableBook.setGridColor(new java.awt.Color(153, 153, 153));
        jTableBook.setSelectionBackground(new java.awt.Color(204, 204, 255));
        jTableBook.setShowGrid(true);
        jTableBook.setShowVerticalLines(false);
        jScrollPane2.setViewportView(jTableBook);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setText("List Books");

        buttonUploadImage.setBackground(new java.awt.Color(102, 255, 102));
        buttonUploadImage.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonUploadImage.setForeground(new java.awt.Color(255, 255, 255));
        buttonUploadImage.setText("Upload Image");
        buttonUploadImage.setBorderPainted(false);
        buttonUploadImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUploadImageActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(153, 102, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Close");
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel7.setText("Mô tả");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setText("Add Categlory");

        jLabel11.setText("Chọn thể loại");

        buttonSaveCate.setBackground(new java.awt.Color(255, 102, 102));
        buttonSaveCate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonSaveCate.setForeground(new java.awt.Color(255, 255, 255));
        buttonSaveCate.setText("Add category");
        buttonSaveCate.setBorder(null);
        buttonSaveCate.setBorderPainted(false);
        buttonSaveCate.setFocusPainted(false);
        buttonSaveCate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveCateActionPerformed(evt);
            }
        });

        cbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoryActionPerformed(evt);
            }
        });

        buttonSearch.setBackground(new java.awt.Color(51, 153, 255));
        buttonSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonSearch.setForeground(new java.awt.Color(255, 255, 255));
        buttonSearch.setText("Search");
        buttonSearch.setBorder(null);
        buttonSearch.setBorderPainted(false);
        buttonSearch.setDefaultCapable(false);
        buttonSearch.setFocusPainted(false);
        buttonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSearchActionPerformed(evt);
            }
        });

        jLabelImage.setBackground(new java.awt.Color(204, 204, 204));
        jLabelImage.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(159, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(buttonUploadImage, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenSach)
                            .addComponent(txtTacGia)
                            .addComponent(txtNamPhatHanh)
                            .addComponent(txtNXB)
                            .addComponent(txtGia)
                            .addComponent(txtMoTa)
                            .addComponent(jLabel3)
                            .addComponent(cbCategory, 0, 184, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTheLoai)
                            .addComponent(jLabel10)
                            .addComponent(buttonSaveCate, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelImage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(221, 221, 221)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(buttonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(155, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(448, 448, 448))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonSaveCate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtNamPhatHanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabelImage, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(buttonUploadImage, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 111, Short.MAX_VALUE)))))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelTitleComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jLabelTitleComponentMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelTitleComponentMoved

    private void buttonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSearchActionPerformed
        String keyword = txtSearch.getText().trim();
        List<Book> searchResults = bookDAO.searchBooks(keyword);
        DefaultTableModel model = (DefaultTableModel) jTableBook.getModel();
        model.setRowCount(0); // Xóa các hàng hiện tại trong bảng

        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books found matching the search criteria.", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Book book : searchResults) {
                Object[] row = {
                    book.getMaSach(),
                    book.getTenSach(),
                    book.getTheLoai(),
                    book.getTenTacGia(),
                    book.getNamNXB(),
                    book.getNXB(),
                    book.getGiaSach(),
                    book.getMoTaSach()
                };
                model.addRow(row);
            }
        }
    }//GEN-LAST:event_buttonSearchActionPerformed

    private void cbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoryActionPerformed
//    initComponents();
//    loadCategories(); // Load danh sách thể loại khi mở for
    }//GEN-LAST:event_cbCategoryActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
        try {
            String tenSach = txtTenSach.getText();
            String tenTacGia = txtTacGia.getText();
            Integer namNXB = Integer.parseInt(txtNamPhatHanh.getText());
            int trangThai = 0;
            String nXB = txtNXB.getText();
            byte[] imageBytes = null;
            ImageIcon imageIcon = (ImageIcon) jLabelImage.getIcon();
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

            String moTaSach = txtMoTa.getText();
            float giaSach = Float.parseFloat(txtGia.getText());
            String theLoai = cbCategory.getSelectedItem().toString();
            Book book = new Book(tenSach, tenTacGia, namNXB, trangThai, nXB, imageBytes, moTaSach, giaSach, theLoai);
            BookDAO.addBook(book);
            JOptionPane.showMessageDialog(this, "Book added successfully!");
            loadData();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid data.");
        }
        loadData();
    }//GEN-LAST:event_buttonSaveActionPerformed

    private void buttonSaveCateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveCateActionPerformed
        String categoryName = txtTheLoai.getText().trim();

        if (categoryName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên thể loại không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BookDAO bookDAO = new BookDAO();

        // Kiểm tra thể loại đã tồn tại hay chưa
        if (bookDAO.isCategoryExists(categoryName)) {
            JOptionPane.showMessageDialog(this, "Thể loại đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Nếu chưa tồn tại, thêm thể loại mới
        if (bookDAO.addCategory(categoryName)) {
            JOptionPane.showMessageDialog(this, "Thể loại đã được lưu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            txtTheLoai.setText(""); // Xóa nội dung ô nhập
            cbCategory.addItem(categoryName); // Thêm thể loại vào JComboBox
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi lưu thể loại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        loadData(); // Tải lại dữ liệu sau khi thêm
    }//GEN-LAST:event_buttonSaveCateActionPerformed

    private void buttonUploadImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUploadImageActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage img = ImageIO.read(selectedFile);
                Image scaledImg = img.getScaledInstance(jLabelImage.getWidth(), jLabelImage.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(scaledImg);
                jLabelImage.setIcon(imageIcon);
                jLabelImage.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to load image", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_buttonUploadImageActionPerformed
    protected void loadData() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            DefaultTableModel model = (DefaultTableModel) jTableBook.getModel();
            model.setRowCount(0);
            for (Book book : books) {
                model.addRow(new Object[]{
                    book.getMaSach(),
                    book.getTenSach(),
                    book.getTheLoai(),
                    book.getTenTacGia(),
                    book.getNamNXB(),
                    book.getNXB(),
                    book.getGiaSach(),
                    book.getMoTaSach()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
        }
    }
    

    private void clearFormFields() {
        txtTenSach.setText("");
        txtTacGia.setText("");
        txtNamPhatHanh.setText("");
        txtNXB.setText("");
        txtMoTa.setText("");
        txtGia.setText("");
        cbCategory.setSelectedIndex(0);
        jLabelImage.setIcon(null); // Reset image display
    }

    private void loadCategories() {
        cbCategory.removeAllItems(); // Clear existing items
        List<String> categories = bookDAO.getAllCategories(); // Fetch categories from the database
        for (String category : categories) {
            cbCategory.addItem(category); // Add each category to the combo box
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new BookForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonSave;
    private javax.swing.JButton buttonSaveCate;
    private javax.swing.JButton buttonSearch;
    private javax.swing.JButton buttonUploadImage;
    private javax.swing.JComboBox<String> cbCategory;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableBook;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMoTa;
    private javax.swing.JTextField txtNXB;
    private javax.swing.JTextField txtNamPhatHanh;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTacGia;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtTheLoai;
    // End of variables declaration//GEN-END:variables
}
