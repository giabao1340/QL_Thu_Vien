/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qlthuvien.view;

import com.mycompany.qlthuvien.DatabaseConnection;
import com.mycompany.qlthuvien.dao.MemberDao;
import java.awt.Color;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import memberState.ActiveMemberState;
import memberState.MemberContext;

/**
 *
 * @author minh9
 */
public class UpdateMem extends javax.swing.JFrame {

    /**
     * Creates new form UpdateMem
     */
    String fileName = null;
    byte[] memImage = null;
    private JTable jMemTable;
    private byte[] img;

    public UpdateMem(JTable jMemTable) {
        this.jMemTable = jMemTable;
        initComponents();
        upCreateDob.setDate(new Date());
        getContentPane().setBackground(Color.decode("#FFF7FC"));
        int selectRow = jMemTable.getSelectedRow();
        upMaDocGia.setText(jMemTable.getValueAt(selectRow, 0).toString());
        upNameTXT.setText(jMemTable.getValueAt(selectRow, 1).toString());
        upBirthDob.setDate((Date) jMemTable.getValueAt(selectRow, 2));
        upEmailTXT.setText(jMemTable.getValueAt(selectRow, 3).toString());
        upCreateDob.setDate((Date) jMemTable.getValueAt(selectRow, 4));
        upExpiryDob.setDate((Date) jMemTable.getValueAt(selectRow, 5));
        upCbStatus.setSelectedItem(jMemTable.getValueAt(selectRow, 6).toString());
        upCbGender.setSelectedItem(jMemTable.getValueAt(selectRow, 7).toString());
        String maDocGia = jMemTable.getValueAt(selectRow, 0).toString();
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        String sql = "SELECT Hinh FROM DocGia WHERE MaDocGia = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maDocGia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                img = rs.getBytes("Hinh");
                if (img != null) {
                    ImageIcon image = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(upMemAvatar.getWidth(), upMemAvatar.getHeight(), Image.SCALE_SMOOTH));
                    upMemAvatar.setIcon(image);
                    System.out.println("Image loaded for MaDocGia: " + maDocGia);
                } else {
                    upMemAvatar.setIcon(null);
                    System.out.println("No image found for MaDocGia: " + maDocGia);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setLocationRelativeTo(null); // Hiển thị form ở giữa màn hình

    }

    public boolean validateDob() {
        int selectRow = jMemTable.getSelectedRow();
        Date selectedCreDate = (Date) upCreateDob.getDate();
        Date selectedExDate = (Date) upExpiryDob.getDate();
        LocalDate currentDate = LocalDate.now();
        ///CreDate
        Instant instantCre = selectedCreDate.toInstant();
        ZonedDateTime zonedDateTime = instantCre.atZone(ZoneId.systemDefault());
        LocalDate creLocal = zonedDateTime.toLocalDate();
        ///ExDate
        Instant instantEx = selectedExDate.toInstant();
        ZonedDateTime exTime = instantEx.atZone(ZoneId.systemDefault());
        LocalDate exLocal = exTime.toLocalDate();
        //////
        if (creLocal.isAfter(currentDate)) {
            JOptionPane.showMessageDialog(null, "Ngày tạo thẻ không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            upCreateDob.setDate(null);
            return false;
        }
        if (exLocal.isBefore(creLocal)) {
            JOptionPane.showMessageDialog(null, "Ngày hết hạn không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            upExpiryDob.setDate(null);
            return false;
        }
        if (upNameTXT.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tên không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (upEmailTXT.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnOut = new javax.swing.JButton();
        imageChooseBtn = new javax.swing.JButton();
        memAvatar = new javax.swing.JLabel();
        lblUpdate = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        upNameTXT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        upBirthDob = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        upEmailTXT = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        upCreateDob = new com.toedter.calendar.JDateChooser();
        upExpiryDob = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        upCbGender = new javax.swing.JComboBox<>();
        upCbStatus = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        upMaDocGia = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnUpdateOut = new javax.swing.JButton();
        upImageChooseBtn = new javax.swing.JButton();
        upMemAvatar = new javax.swing.JLabel();

        jLabel7.setText("jLabel7");

        btnAdd.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnOut.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        btnOut.setText("Thoát");
        btnOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOutActionPerformed(evt);
            }
        });

        imageChooseBtn.setText("Chọn ảnh");
        imageChooseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageChooseBtnActionPerformed(evt);
            }
        });

        memAvatar.setBackground(new java.awt.Color(255, 255, 255));
        memAvatar.setForeground(new java.awt.Color(255, 255, 255));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 153, 153));

        lblUpdate.setBackground(new java.awt.Color(255, 103, 102));
        lblUpdate.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblUpdate.setForeground(new java.awt.Color(255, 255, 255));
        lblUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUpdate.setText("Cập nhật Member");
        lblUpdate.setOpaque(true);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Mã Độc Giả");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Họ và tên");

        upNameTXT.setBackground(new java.awt.Color(245, 247, 248));
        upNameTXT.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Ngày sinh");

        upBirthDob.setBackground(new java.awt.Color(245, 247, 248));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Email");

        upEmailTXT.setBackground(new java.awt.Color(245, 247, 248));
        upEmailTXT.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Ngày lập thẻ");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Ngày hết hạn");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Giới tính");

        upCbGender.setBackground(new java.awt.Color(245, 247, 248));
        upCbGender.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        upCbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        upCbGender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upCbGenderActionPerformed(evt);
            }
        });

        upCbStatus.setBackground(new java.awt.Color(245, 247, 248));
        upCbStatus.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        upCbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", " " }));
        upCbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upCbStatusActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Trạng thái");

        upMaDocGia.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        upMaDocGia.setText("...");

        btnUpdate.setBackground(new java.awt.Color(0, 204, 0));
        btnUpdate.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Sửa");
        btnUpdate.setBorder(null);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnUpdateOut.setBackground(new java.awt.Color(255, 51, 51));
        btnUpdateOut.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        btnUpdateOut.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateOut.setText("Thoát");
        btnUpdateOut.setBorder(null);
        btnUpdateOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateOutActionPerformed(evt);
            }
        });

        upImageChooseBtn.setBackground(new java.awt.Color(255, 153, 51));
        upImageChooseBtn.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        upImageChooseBtn.setForeground(new java.awt.Color(255, 255, 255));
        upImageChooseBtn.setText("Chọn ảnh");
        upImageChooseBtn.setBorder(null);
        upImageChooseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upImageChooseBtnActionPerformed(evt);
            }
        });

        upMemAvatar.setBackground(new java.awt.Color(255, 255, 255));
        upMemAvatar.setForeground(new java.awt.Color(255, 255, 255));
        upMemAvatar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        upMemAvatar.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(219, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(upMaDocGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(upNameTXT, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(upBirthDob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                            .addComponent(upEmailTXT)
                            .addComponent(upCreateDob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(upExpiryDob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(upCbGender, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(upCbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(upMemAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(upImageChooseBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                            .addComponent(btnUpdateOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(139, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(102, 102, 102)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9))
                    .addContainerGap(373, Short.MAX_VALUE))
                .addComponent(lblUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(upMaDocGia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(upNameTXT)
                .addGap(12, 12, 12)
                .addComponent(upBirthDob, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(upEmailTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(upCreateDob, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(upExpiryDob, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(upCbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(upCbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(upMemAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(upImageChooseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateOut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel2)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel3)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel5)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel6)
                    .addGap(26, 26, 26)
                    .addComponent(jLabel9)
                    .addGap(26, 26, 26)
                    .addComponent(jLabel8)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel10)
                    .addGap(359, 359, 359)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void upCbGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upCbGenderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_upCbGenderActionPerformed

    private void upCbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upCbStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_upCbStatusActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOutActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnOutActionPerformed

    private void imageChooseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageChooseBtnActionPerformed

    }//GEN-LAST:event_imageChooseBtnActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        int s = 0;
        if (validateDob()) {
            int selectRow = jMemTable.getSelectedRow();
            String upTen = upNameTXT.getText();
            Date upSn = upBirthDob.getDate();
            String upMail = upEmailTXT.getText();
            Date upCre = upCreateDob.getDate();
            Date upExp = upExpiryDob.getDate();
            String upStatus = upCbStatus.getSelectedItem().toString();
            String upGender = upCbGender.getSelectedItem().toString().equals("Nam") ? "0" : "1";
            // Truy van Update
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();
            String ma = jMemTable.getValueAt(selectRow, 0).toString();
            int maDG = Integer.parseInt(ma);
            String sql = "Update DocGia set HoTen = ?,NgaySinh = ? ,Email= ?, NgayLapThe = ?, NgayHetHan = ? , GioiTinh = ?,Hinh = ? where MaDocGia = ? ";
            byte[] upImg = memImage;
            if (upImg == null) {
                upImg = img;
            }
            try (PreparedStatement ps = connection.prepareStatement(sql)) {//Query du lieu trong sql
                java.sql.Date upBirthDate = new java.sql.Date(upSn.getTime());
                java.sql.Date upCreateDate = new java.sql.Date(upCre.getTime());
                java.sql.Date upExpiryDate = new java.sql.Date(upExp.getTime());
                ps.setString(1, upTen);
                ps.setDate(2, upBirthDate);
                ps.setString(3, upMail);
                ps.setDate(4, upCreateDate);
                ps.setDate(5, upExpiryDate);
                MemberContext memberContext = new MemberContext();
                memberContext.setState(new ActiveMemberState());
                memberContext.ChangeState(maDG,0);
                ps.setString(6, upGender);
                ps.setBytes(7, upImg);
                ps.setString(8, ma);
                ps.executeUpdate();
                System.out.println("Cap nhat member thanh cong");
                s = 1;
            } catch (Exception ex) {
                ex.printStackTrace();
                s = 0;
            }
        }
        if (s == 1) {
            JOptionPane.showMessageDialog(null, "Update successfully");
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Update failed");
        }
        DefaultTableModel model = (DefaultTableModel) jMemTable.getModel();
        model.setRowCount(0);
        MemberDao memberDao = new MemberDao(jMemTable);
        memberDao.showMem();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnUpdateOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateOutActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnUpdateOutActionPerformed

    private void upImageChooseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upImageChooseBtnActionPerformed
        JFileChooser chooser = new JFileChooser(); //khoi tao JFileChooser
        chooser.showOpenDialog(null); //Mo bang chon file
        File f = chooser.getSelectedFile();
        fileName = f.getAbsolutePath(); //gán đường dẫn của file
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(fileName).getImage().getScaledInstance(upMemAvatar.getWidth(), upMemAvatar.getHeight(), Image.SCALE_SMOOTH)); //Tạo image icon dựa trên đường dẫn filename
        upMemAvatar.setIcon(imageIcon); //set hinh anh cho lable
        try {
            File image = new File(fileName); //Tạo file cho hình ảnh đã chọn
            FileInputStream fileInputStream = new FileInputStream(image); //Đọc file
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); //tạo biến byte để lưu dữ liệu của hình ảnh
            byte[] buf = new byte[1024];    // Tạo bộ đệm để đọc từng khối dữ liệu của tệp ChatGPT :))
            for (int readNum; (readNum = fileInputStream.read(buf)) != -1;) {
                byteArrayOutputStream.write(buf, 0, readNum); //ghi file đã đọc vào biến đã tạo
            }
            memImage = byteArrayOutputStream.toByteArray();//chuyển đổi biến đã tạo thành mảng byte
        } catch (Exception e) {

        }
    }//GEN-LAST:event_upImageChooseBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnOut;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdateOut;
    private javax.swing.JButton imageChooseBtn;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblUpdate;
    private javax.swing.JLabel memAvatar;
    public com.toedter.calendar.JDateChooser upBirthDob;
    public javax.swing.JComboBox<String> upCbGender;
    public javax.swing.JComboBox<String> upCbStatus;
    public com.toedter.calendar.JDateChooser upCreateDob;
    public javax.swing.JTextField upEmailTXT;
    public com.toedter.calendar.JDateChooser upExpiryDob;
    private javax.swing.JButton upImageChooseBtn;
    private javax.swing.JLabel upMaDocGia;
    private javax.swing.JLabel upMemAvatar;
    public javax.swing.JTextField upNameTXT;
    // End of variables declaration//GEN-END:variables
}
