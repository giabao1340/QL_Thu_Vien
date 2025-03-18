/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien.view;
import FactoryMethod.EmailFactory;
import FactoryMethod.EmailSender;
import FactoryMethod.EmailTemplate;
import com.mycompany.BorrowedTicketStates.BorrowedTicketContext;
import com.mycompany.BorrowedTicketStates.LostBookState;
import com.mycompany.BorrowedTicketStates.StillDueState;
import com.mycompany.qlthuvien.bookstate.BookContext;
import com.mycompany.qlthuvien.DatabaseConnection;
import com.mycompany.qlthuvien.dao.BorrowedTicketDAO;
import com.mycompany.qlthuvien.dao.MemberDao;
import com.mycompany.qlthuvien.bookstate.LostState;
import com.mycompany.BorrowedTicketStates.ReturnedTicketState;
import com.mycompany.qlthuvien.bookstate.ReturnedState;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.util.List;
public class ChiTietPhieuMuon extends javax.swing.JFrame {

    private String ngayMuon, ngayTra, tenPM, ngayTraTT, tienPhat, tenDocGia, tenSach;
    int maPM, maDocGia, maSach;
    private int trangThaiSach;
    private DatabaseConnection dbConnection;
    private Connection conn;
    private PhieuMuonPage mainFrame;
    private String email;
    private int tienphat;
    private int tienphat2;
    private int trangThaiPhieu;
    private int tra_mat;

    public ChiTietPhieuMuon(PhieuMuonPage mainFrame, String maPM, String ngayMuon, String ngayTra, String maDocGia) {
        this.mainFrame = mainFrame;
        dbConnection = DatabaseConnection.getInstance();;
        conn = dbConnection.getConnection();
        this.maPM = Integer.parseInt(maPM);
        trangThaiPhieu = getTrangThaiTuMaPM(this.maPM);
        this.maDocGia = Integer.parseInt(maDocGia);
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
        initComponents();
        setDetailToFields();
        setLocationRelativeTo(null);

    }

    private void setDetailToFields() {
        txtMaPM.setText(String.valueOf(maPM));
        txtMaDocGia.setText(String.valueOf(maDocGia));
        txtNgayMuon.setText(ngayMuon);
        txtNgayTra.setText(ngayTra);
        txtTenDocGia.setText(getTenDocGia(maDocGia));
        getTienPhatAndNgayTraThucTe(maPM);
        getEmail(maDocGia);
        txtMaPM.setEditable(false);
        txtMaDocGia.setEditable(false);
        txtNgayMuon.setEditable(false);
        txtNgayTra.setEditable(false);
        txtTenDocGia.setEditable(false);
        txtNgayTraThucTe.setEditable(false);
        txtTienPhat.setEditable(false);
        loadSachInfo(maPM);
    }

    private void initComponents() {
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMaPM = new javax.swing.JTextField();
        txtNgayMuon = new javax.swing.JTextField();
        txtTenDocGia = new javax.swing.JTextField();
        txtNgayTra = new javax.swing.JTextField();
        txtMaDocGia = new javax.swing.JTextField();
        txtTienPhat = new javax.swing.JTextField();
        btnTraSach = new javax.swing.JButton();
        btnMatSach = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtNgayTraThucTe = new javax.swing.JTextField();
        btnClose = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSachMuon = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        JPanel leftPanel = new JPanel();

        // Create a panel for the table with a border and title
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2), // Set border color
                "Sách Mượn",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.PLAIN, 18),
                new Color(255, 102, 102) // Set font color
        ));

        // Initialize the table and scroll pane
        tableSachMuon.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                },
                new String[]{
                    "Mã Sách", "Tên Sách", "Trạng Thái"
                }
        ));
        JTableHeader header = tableSachMuon.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tableSachMuon.setRowHeight(30);
        tableSachMuon.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        jScrollPane1.setViewportView(tableSachMuon);

        // Add scroll pane to the table panel
        tablePanel.add(jScrollPane1, BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Update jLabel1 properties
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Set font size to 20 and bold
        jLabel1.setText("CHI TIẾT PHIẾU MƯỢN");
        jLabel1.setOpaque(true); // Make background color visible
        jLabel1.setBackground(new Color(255, 102, 102)); // Set background color
        jLabel1.setForeground(Color.WHITE); // Set font color to white

        // Add padding using Border
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border border = BorderFactory.createCompoundBorder(jLabel1.getBorder(), paddingBorder);
        jLabel1.setBorder(border);

        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        jLabel2.setText("Mã Phiếu Mượn");

        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        jLabel3.setText("Mã Đọc Giả");

        jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        jLabel4.setText("Tên Đọc Giả");

        jLabel7.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        jLabel7.setText("Ngày Mượn");

        jLabel8.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        jLabel8.setText("Ngày Trả");

        jLabel9.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        jLabel9.setText("Ngày Trả Thực Tế");

        jLabel10.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        jLabel10.setText("Tiền Phạt");

        txtNgayMuon.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtMaPM.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtTenDocGia.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtNgayTra.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtMaDocGia.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtTienPhat.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtNgayTraThucTe.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        btnTraSach.setText("Trả Sách");
        btnTraSach.setBackground(new Color(153, 153, 255));
        btnTraSach.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btnTraSach.setBackground(new Color(102, 102, 255)); // Change color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnTraSach.setBackground(new Color(153, 153, 255)); // Reset to original color
            }
        });
        btnTraSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraSachActionPerformed(evt);
            }
        });

        btnMatSach.setText("Mất Sách");
        btnMatSach.setBackground(new Color(255, 51, 51));
        btnMatSach.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btnMatSach.setBackground(new Color(255, 0, 0)); // Change color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnMatSach.setBackground(new Color(255, 51, 51)); // Reset to original color
            }
        });
        btnMatSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMatSachActionPerformed(evt);
            }
        });

        btnClose.setText("Close");
        btnClose.setBackground(new Color(204, 102, 255));
        btnClose.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btnClose.setBackground(new Color(204, 51, 255)); // Change color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnClose.setBackground(new Color(204, 102, 255)); // Reset to original color
            }
        });
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        // Create leftPanel and set its layout and border
        leftPanel.setLayout(new GridLayout(0, 2, 10, 10));
        leftPanel.setPreferredSize(new Dimension(600, 300)); // Increase size of the panel
        leftPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2), // Set border color
                "Thông tin chi tiết",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.PLAIN, 18),
                new Color(255, 102, 102) // Set font color
        ));

        leftPanel.add(jLabel2);
        leftPanel.add(txtMaPM);
        leftPanel.add(jLabel3);
        leftPanel.add(txtMaDocGia);
        leftPanel.add(jLabel4);
        leftPanel.add(txtTenDocGia);
        leftPanel.add(jLabel7);
        leftPanel.add(txtNgayMuon);
        leftPanel.add(jLabel8);
        leftPanel.add(txtNgayTra);
        leftPanel.add(jLabel9);
        leftPanel.add(txtNgayTraThucTe);
        leftPanel.add(jLabel10);
        leftPanel.add(txtTienPhat);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Ensure jLabel1 spans entire width
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(376, 376, 376))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(654, Short.MAX_VALUE)
                                .addComponent(btnTraSach)
                                .addGap(18, 18, 18)
                                .addComponent(btnMatSach)
                                .addContainerGap(89, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1) // Ensure jLabel1 is at the top
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnMatSach)
                                        .addComponent(btnTraSach))
                                .addGap(70, 70, 70)
                                .addComponent(btnClose)
                                .addGap(25, 25, 25))
        );

        pack();
    }

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {

        dispose();
    }

private void btnTraSachActionPerformed(java.awt.event.ActionEvent evt) {
    int[] selectedRows = tableSachMuon.getSelectedRows();
    if (selectedRows.length > 0) {
        BorrowedTicketDAO dao = new BorrowedTicketDAO();
        List<Integer> sachDaMuon = dao.getMaSachByPM(maPM); // Lấy danh sách sách theo mã phiếu mượn
        List<String> sachDaTra = new ArrayList<>(); // Lưu sách đã trả
        int soSachMuon = selectedRows.length;

        Date ngayTraThucTe = new Date();
        Date ngayMuon = dao.getNgayMuonByPhieuMuon(maPM);
        Date ngayTra = dao.getNgayTraByPhieuMuon(maPM);

        for (int row : selectedRows) {
            int maSach = (int) tableSachMuon.getValueAt(row, 0);
            
            if (isSachAvailable(maSach)) {
                JOptionPane.showMessageDialog(null, "Sách đã được trả trước đó: " + tableSachMuon.getValueAt(row, 1));
            } else if (isSachLoss(maSach)) {
                JOptionPane.showMessageDialog(null, "Sách đã mất: " + tableSachMuon.getValueAt(row, 1));
            } else {
                // Cập nhật trạng thái sách
                BookContext bookContext = new BookContext(conn);
                bookContext.setState(new ReturnedState());
                bookContext.updateSachStatus(maSach, maPM);

                // Cập nhật trạng thái phiếu mượn
                BorrowedTicketContext context = new BorrowedTicketContext(conn);
                context.setState(new ReturnedTicketState());
                context.updateSachStatus(maPM);

                // Lưu sách đã trả
                sachDaTra.add(tableSachMuon.getValueAt(row, 1).toString());
                        // Tính phí và tiền phạt
        double phi = dao.tinhPhi(ngayMuon, ngayTra, ngayTraThucTe, soSachMuon);
        double tienPhat = dao.tinhTienPhat(ngayMuon, ngayTra, ngayTraThucTe, soSachMuon, sachDaMuon);
        double tongPhi = phi + tienPhat;

        // Gửi email xác nhận
        MemberDao readerDAO = new MemberDao();
        String email = readerDAO.getEmailByReaderId(maDocGia);

        if (email != null && !email.isEmpty()) {
            EmailSender.send(
                email,
                "Xác nhận trả sách thành công",
                EmailFactory.createEmail(
                    "RETURN_CONFIRMATION", 
                    ngayMuon, 
                    ngayTra, 
                    ngayTraThucTe, 
                    sachDaTra, 
                    phi, 
                    tienPhat, 
                    tongPhi
                ).createEmailContent()
            );
            JOptionPane.showMessageDialog(this, "✅ Email xác nhận trả sách đã được gửi đến: " + email);
        } else {
            JOptionPane.showMessageDialog(this, "❌ Không tìm thấy email của độc giả.");
            System.out.println("❌ Không tìm thấy email của độc giả.");
        }
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn ít nhất một sách để trả.");
    }
}

private void btnMatSachActionPerformed(java.awt.event.ActionEvent evt) {
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    this.conn = dbConnection.getConnection();

    int[] selectedRows = tableSachMuon.getSelectedRows();
    if (selectedRows.length > 0) {
        BorrowedTicketDAO dao = new BorrowedTicketDAO();
        List<String> sachDaMat = new ArrayList<>();
        List<Integer> maSachMat = new ArrayList<>(); // Danh sách mã sách mất
        int soSachMuon = countBooksInLoan(maPM);

        Date ngayTraThucTe = new Date();
        Date ngayMuon = dao.getNgayMuonByPhieuMuon(maPM);
        Date ngayTra = dao.getNgayTraByPhieuMuon(maPM);

        for (int row : selectedRows) {
            int maSach = (int) tableSachMuon.getValueAt(row, 0);
            String tenSach = tableSachMuon.getValueAt(row, 1).toString();

            if (isSachAvailable(maSach)) {
                JOptionPane.showMessageDialog(null, "Sách đã được trả trước đó: " + tenSach);
            } else if (isSachLoss(maSach)) {
                JOptionPane.showMessageDialog(null, "Sách đã mất: " + tenSach);
            } else {
                // Cập nhật trạng thái sách thành Mất
                BookContext bookContext = new BookContext(conn);
                bookContext.setState(new LostState());
                bookContext.updateSachStatus(maSach, maPM);

                // Cập nhật trạng thái phiếu mượn
                BorrowedTicketContext context = new BorrowedTicketContext(conn);
                context.setState(new LostBookState());
                context.updateSachStatus(maPM);

                sachDaMat.add(tenSach);
                maSachMat.add(maSach); // Lưu mã sách để tính tiền phạt
            }
        }

        if (!sachDaMat.isEmpty()) {
            double phi = dao.tinhPhi(ngayMuon, ngayTra, ngayTraThucTe, soSachMuon);
            double tienPhat = dao.tinhTienPhat(ngayMuon, ngayTra, ngayTraThucTe, soSachMuon, maSachMat);
            double tongPhi = phi + tienPhat;

            // Gửi email thông báo mất sách
            MemberDao readerDAO = new MemberDao();
            String email = readerDAO.getEmailByReaderId(maDocGia);

            if (email != null && !email.isEmpty()) {
                EmailSender.send(
                    email,
                    "Thông báo mất sách",
                    EmailFactory.createEmail(
                        "LOSS",
                        ngayMuon,
                        ngayTra,
                        ngayTraThucTe,
                        sachDaMat,
                        phi,
                        tienPhat,
                        tongPhi
                    ).createEmailContent()
                );
                JOptionPane.showMessageDialog(this, "✅ Email xác nhận mất sách đã được gửi đến: " + email);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Không tìm thấy email của độc giả.");
                System.out.println("❌ Không tìm thấy email của độc giả.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không có sách nào được báo mất.");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn ít nhất một sách để báo mất.");
    }
}
   

    public int countBooksInLoan(int maPM) {
        String sql = "SELECT COUNT(*) FROM Sach_PhieuMuon WHERE MaPM = ?";
        DatabaseConnection db = DatabaseConnection.getInstance();
        Connection connect = db.getConnection();
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setInt(1, maPM);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu có lỗi hoặc không tìm thấy
    }
    private void getTienPhatAndNgayTraThucTe(int maPM) {
        String query = "SELECT TienPhat, NgayTraThucTe FROM PhieuMuon WHERE MaPM = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, maPM);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int tienPhat = rs.getInt("TienPhat");
                    Date ngayTraThucTe = rs.getDate("NgayTraThucTe");

                    // Hiển thị dữ liệu hoặc lưu vào JTextField
                    txtTienPhat.setText(String.valueOf(tienPhat));

                    // Định dạng ngày để hiển thị
                    txtNgayTraThucTe.setText(String.valueOf(ngayTraThucTe));
                } else {
                    txtTienPhat.setText("Không có dữ liệu");
                    txtNgayTraThucTe.setText("Không có dữ liệu");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getTenDocGia(int maDocGia) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {

            String sql = "SELECT HoTen FROM DocGia WHERE MaDocGia =" + maDocGia;
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                tenDocGia = rs.getString("HoTen");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tenDocGia;
    }

    private String getEmail(int maDocGia) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {

            String sql = "SELECT Email FROM DocGia WHERE MaDocGia =" + maDocGia;
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                email = rs.getString("Email");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return email;
    }



    public int getTrangThaiTuMaPM(int maPM) {
        int trangThai1 = 0;
        String query = "SELECT TrangThai FROM PhieuMuon WHERE MaPM = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, maPM);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    trangThai1 = rs.getInt("TrangThai");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trangThai1;
    }

    private void loadSachInfo(int maPM) {
        String query = "SELECT s.MaSach, s.TenSach, s.TrangThai "
                + "FROM Sach s "
                + "JOIN Sach_PhieuMuon spm ON s.MaSach = spm.MaSach "
                + "WHERE spm.MaPM = ?";

        try (
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, maPM);
            try (ResultSet rs = pstmt.executeQuery()) {
                // Xóa dữ liệu cũ trong table
                DefaultTableModel model = (DefaultTableModel) tableSachMuon.getModel();
                model.setRowCount(0);  // Xóa tất cả các dòng hiện tại trong bảng

                // Lấy dữ liệu và thêm vào model
                while (rs.next()) {
                    int trangThai = rs.getInt("TrangThai");
                    String trangThaiString;

                    // Chuyển đổi trạng thái thành chuỗi mô tả
                    switch (trangThai) {
                        case 0:
                            trangThaiString = "Không có ai mượn";
                            break;
                        case 1:
                            trangThaiString = "Đang mượn";
                            break;
                        case 2:
                            trangThaiString = "Bị mất";
                            break;
                        default:
                            trangThaiString = "Không xác định";
                            break;
                    }

                    Object[] rowData = {
                        rs.getInt("MaSach"), // Mã sách
                        rs.getString("TenSach"), // Tên sách
                        trangThaiString // Trạng thái sách
                    };
                    model.addRow(rowData);  // Thêm dòng mới vào bảng
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private boolean isSachAvailable(int maSach) {
        String query = "SELECT TrangThai FROM Sach WHERE MaSach = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, maSach);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int trangThai = rs.getInt("TrangThai");
                    return trangThai == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Nếu không tìm thấy sách, trả về false
        return false;
    }

    private boolean isSachLoss(int maSach) {
        String query = "SELECT TrangThai FROM Sach WHERE MaSach = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, maSach);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int trangThai = rs.getInt("TrangThai");
                    System.out.println(trangThai);
                    return trangThai == 2; // Trả về true nếu trạng thái = 2
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PhieuMuonPage mainFrame2 = new PhieuMuonPage();
                String maDocGia = "";
                String maPM = "";
                String ngayMuon = "";
                String ngayTra = "";
                new ChiTietPhieuMuon(mainFrame2, maPM, ngayMuon, ngayTra, maDocGia).setVisible(true);
            }
        });
    }
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnMatSach;
    private javax.swing.JButton btnTraSach;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableSachMuon;
    private javax.swing.JTextField txtMaDocGia;
    private javax.swing.JTextField txtMaPM;
    private javax.swing.JTextField txtNgayMuon;
    private javax.swing.JTextField txtNgayTra;
    private javax.swing.JTextField txtNgayTraThucTe;
    private javax.swing.JTextField txtTenDocGia;
    private javax.swing.JTextField txtTienPhat;
    // End of variables declaration                   
}
