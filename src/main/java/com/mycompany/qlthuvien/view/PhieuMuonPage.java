/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien.view;
import FactoryMethod.EmailFactory;
import FactoryMethod.EmailSender;
import FactoryMethod.EmailTemplate;
import FactoryMethod.OverdueEmail;
import com.mycompany.BorrowedTicketStates.BorrowedTicketContext;
import com.mycompany.BorrowedTicketStates.OverDueState;
import com.mycompany.BorrowedTicketStates.ReturnedTicketState;
import com.mycompany.qlthuvien.DatabaseConnection;
import com.mycompany.qlthuvien.dao.BorrowInfo;
import com.mycompany.qlthuvien.dao.BorrowedTicketDAO;
import com.mycompany.qlthuvien.dao.MemberDao;
import com.mycompany.qlthuvien.model.BorrowedTicket;
import javax.swing.DefaultListModel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.ParseException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class PhieuMuonPage extends javax.swing.JFrame {

    private javax.swing.JScrollPane ThongtinTable;
    private javax.swing.JPanel Thongtinphieu;
    private javax.swing.JPanel titlePanel; // New panel for the title
    private javax.swing.JButton btnClose, btnSearch, btnXoa, btnSendEmail, btnDangKy;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelMaDG, labelNgayMuon, labelTenSach, lalbelNgayTra, LabelTieuDe;
    private javax.swing.JTextArea txtTenSach; // Updated to JTextArea
    private javax.swing.JScrollPane scrollPaneTenSach; // JScrollPane for JTextArea
    private javax.swing.JTable tablePhieuMuon;
    private javax.swing.JTextField txtMaDocGia;
    private JDateChooser txtNgayMuon, txtNgayTra;
    private DefaultTableModel tableModel;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JList<String> listTenSach;
    private String trangThai;
    private DefaultListModel<String> listModelTenSach;
    private String email, tenDocGia;
    private int maDocGia;
    private DatabaseConnection dbConnection;
    private Connection conn;

    public PhieuMuonPage() {
        dbConnection = DatabaseConnection.getInstance();
        conn = dbConnection.getConnection();
        initComponents();
        loadBookNames();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        Thongtinphieu = new javax.swing.JPanel();
        titlePanel = new javax.swing.JPanel(); // Initialize the title panel
        labelMaDG = new javax.swing.JLabel();
        labelNgayMuon = new javax.swing.JLabel();
        lalbelNgayTra = new javax.swing.JLabel();
        labelTenSach = new javax.swing.JLabel();
        btnDangKy = new javax.swing.JButton();
        btnDangKy.setBorder(null);
        btnDangKy.setBackground(new Color(153, 153, 255));
        btnDangKy.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btnDangKy.setBackground(new Color(102, 102, 255)); // Change color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnDangKy.setBackground(new Color(153, 153, 255)); // Reset to original color
            }
        });
        txtMaDocGia = new javax.swing.JTextField();
        txtNgayMuon = new JDateChooser();
        txtNgayTra = new JDateChooser();
        listTenSach = new javax.swing.JList<String>();

        scrollPaneTenSach = new javax.swing.JScrollPane(listTenSach); // Initialize JScrollPane for JTextArea
        LabelTieuDe = new javax.swing.JLabel();
        ThongtinTable = new javax.swing.JScrollPane();
        tablePhieuMuon = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnSearch.setBackground(new Color(0, 255, 0));
        btnSearch.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btnSearch.setBackground(new Color(51, 204, 0)); // Change color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSearch.setBackground(new Color(0, 255, 0)); // Reset to original color
            }
        });
        btnClose = new javax.swing.JButton();
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
        btnXoa = new javax.swing.JButton();
        btnXoa.setBackground(new Color(255, 51, 51));
        btnXoa.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btnXoa.setBackground(new Color(255, 0, 0)); // Change color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnXoa.setBackground(new Color(255, 51, 51)); // Reset to original color
            }
        });
        btnSendEmail = new javax.swing.JButton();
        btnSendEmail.setBackground(new Color(255, 204, 0));
        btnSendEmail.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btnSendEmail.setBackground(new Color(204, 204, 0)); // Change color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSendEmail.setBackground(new Color(255, 204, 0)); // Reset to original color
            }
        });
        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(668, 650));

        // Title Panel Initialization
        titlePanel.setLayout(new BorderLayout());
        LabelTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 20));
        LabelTieuDe.setText("QUẢN LÝ PHIẾU MƯỢN");
        LabelTieuDe.setOpaque(true);
        LabelTieuDe.setBackground(new Color(255, 102, 102));
        LabelTieuDe.setForeground(Color.WHITE);
        titlePanel.add(LabelTieuDe, BorderLayout.CENTER);

        txtMaDocGia.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtNgayMuon.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtNgayTra.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtNgayMuon.setPreferredSize(new Dimension(200, 25));
        txtNgayTra.setPreferredSize(new Dimension(200, 25));
        listTenSach.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        Thongtinphieu.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Thông tin phiếu mượn",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18),
                new Color(255, 102, 102) // This is the color of the text
        ));
        Thongtinphieu.setPreferredSize(new java.awt.Dimension(800, 500));

        labelMaDG.setFont(new java.awt.Font("Segoe UI", 1, 15));
        labelMaDG.setText("Mã độc giả");

        labelNgayMuon.setFont(new java.awt.Font("Segoe UI", 1, 15));
        labelNgayMuon.setText("Ngày mượn");

        lalbelNgayTra.setFont(new java.awt.Font("Segoe UI", 1, 15));
        lalbelNgayTra.setText("Ngày trả");

        labelTenSach.setFont(new java.awt.Font("Segoe UI", 1, 15));
        labelTenSach.setText("Tên sách");

        scrollPaneTenSach.setPreferredSize(new Dimension(200, 150)); // Đặt kích thước cho JScrollPane
        scrollPaneTenSach.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Tắt thanh cuộn ngang
        scrollPaneTenSach.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneTenSach.setPreferredSize(new Dimension(200, 20));
        scrollPaneTenSach.setViewportView(listTenSach);

        javax.swing.GroupLayout ThongtinphieuLayout = new javax.swing.GroupLayout(Thongtinphieu);
        Thongtinphieu.setLayout(ThongtinphieuLayout);
        ThongtinphieuLayout.setHorizontalGroup(
                ThongtinphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ThongtinphieuLayout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addGroup(ThongtinphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtMaDocGia, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelMaDG, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(47, 47, 47)
                                .addGroup(ThongtinphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(labelTenSach, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                                        .addComponent(scrollPaneTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(63, 63, 63)
                                .addGroup(ThongtinphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(labelNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(72, 72, 72)
                                .addGroup(ThongtinphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lalbelNgayTra)
                                        .addComponent(txtNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(90, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ThongtinphieuLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnDangKy, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(292, 292, 292))
        );
        ThongtinphieuLayout.setVerticalGroup(
                ThongtinphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(ThongtinphieuLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(ThongtinphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(ThongtinphieuLayout.createSequentialGroup()
                                                .addComponent(labelTenSach)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(scrollPaneTenSach, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                                        .addGroup(ThongtinphieuLayout.createSequentialGroup()
                                                .addGroup(ThongtinphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(labelMaDG)
                                                        .addComponent(labelNgayMuon)
                                                        .addComponent(lalbelNgayTra))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(ThongtinphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtMaDocGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                .addComponent(btnDangKy, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        ThongtinTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã Phiếu Mượn");
        tableModel.addColumn("Ngày Mượn");
        tableModel.addColumn("Ngày Trả");
        tableModel.addColumn("Ngày Trả Thực Tế");
        tableModel.addColumn("Tiền phạt");
        tableModel.addColumn("Mã Độc Giả");
        tableModel.addColumn("Trạng Thái");
        tablePhieuMuon = new JTable(tableModel);
        ThongtinTable.setViewportView(tablePhieuMuon);
        tablePhieuMuon = new JTable(tableModel);
        ThongtinTable.setViewportView(tablePhieuMuon);
        JTableHeader header = tablePhieuMuon.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tablePhieuMuon.setRowHeight(30);
        tablePhieuMuon.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tablePhieuMuon.setDefaultEditor(Object.class, null);
        tablePhieuMuon.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Gọi hàm searchReaderEmail() khi người dùng chọn dòng
                    searchReaderEmail();
                }
            }
        });
        // Bắt sự kiện click trên bảng (1 lần click)
        tablePhieuMuon.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {  // Kiểm tra click 1 lần
                    int selectedRow = tablePhieuMuon.getSelectedRow();
                    if (selectedRow != -1) {
                        openDetailPage(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng hợp lệ!");
                    }
                }
            }
        });


        btnSearch.setText("Tìm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnSendEmail.setText("Gửi Email");
        btnSendEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnSendEmailActionPerformed(evt);
                } catch (java.text.ParseException ex) {
                    Logger.getLogger(PhieuMuonPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnDangKy.setText("Đăng ký");
        btnDangKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangKyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 36, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(Thongtinphieu, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(38, 38, 38))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(ThongtinTable, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(40, 40, 40))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(btnSendEmail)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnXoa)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(44, 44, 44))))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(364, 364, 364))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE) // Title panel
                                .addGap(33, 33, 33)
                                .addComponent(Thongtinphieu, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSendEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ThongtinTable, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                                .addComponent(btnClose)
                                .addGap(27, 27, 27))
        );
        Thongtinphieu.getAccessibleContext().setAccessibleName("");
        updateTableData();
        pack();
        setLocationRelativeTo(null);

    }

    private void clear() {
        txtMaDocGia.setText("");
        txtNgayMuon.setDate(null);
        txtNgayTra.setDate(null);
    }

    private void btnDangKyActionPerformed(java.awt.event.ActionEvent evt) {
        saveBorrowingInfo();
        clear();

    }

    private void btnSendEmailActionPerformed(java.awt.event.ActionEvent evt) throws java.text.ParseException {                                             
        MemberDao readerDAO = new MemberDao();
        BorrowedTicketDAO dao = new BorrowedTicketDAO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date(); // Ngày hiện tại
        // Duyệt tabel lấy ra mã PM bị trễ hạn sau đó set state =2
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            try {
                int maPM = Integer.parseInt(tableModel.getValueAt(i, 0).toString()); // Cột 0: Mã Phiếu Mượn
                String ngayTraDuKienStr = tableModel.getValueAt(i, 2).toString(); // Cột 2: Ngày Trả Dự Kiến
                Date ngayTraDuKien = sdf.parse(ngayTraDuKienStr);

                if (ngayTraDuKien.before(currentDate)) { // Nếu đã quá hạn
                    BorrowedTicketContext context = new BorrowedTicketContext(conn);
                    context.setState(new OverDueState());
                    context.updateSachStatus(maPM); // ✅ Cập nhật trạng thái = 2
                }
            } catch (Exception e) {
                System.out.println("⚠ Lỗi tại dòng " + i + ": " + e.getMessage());
            }
        }
        // ✅ Lấy danh sách phiếu mượn có trạng thái = 2 để gửi email
        List<Integer> listPM = dao.getListPMQuaHan(); // Lấy danh sách MaPM có trạng thái = 2
        for (int maPM : listPM) {
            try {
                System.out.println("📌 Xử lý gửi email cho Mã Phiếu Mượn: " + maPM);

                // Lấy Mã Độc Giả từ Mã Phiếu Mượn
                int maDocGia = dao.getMaDocGiaByPM(maPM);
                String email = readerDAO.getEmailByReaderId(maDocGia);

                if (email == null || email.isEmpty()) {
                    System.out.println("⚠ Không tìm thấy email của độc giả có Mã Độc Giả: " + maDocGia);
                    continue;
                }
                // ✅ Lấy thông tin sách mượn
                List<String> sachDaMuon = dao.getSachDaMuon(maPM);
                // ✅ Tìm index của hàng có mã phiếu mượn tương ứng
                int rowIndex = -1;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).toString().equals(String.valueOf(maPM))) { // Cột 0: Mã Phiếu Mượn
                        rowIndex = i;
                        break;
                    }
                }
                // ✅ Kiểm tra nếu tìm thấy dòng hợp lệ
                Date ngayMuon = null;
                Date ngayTraDuKien = null;
                if (rowIndex != -1) {
                    ngayMuon = sdf.parse(tableModel.getValueAt(rowIndex, 1).toString()); // Cột 1: Ngày Mượn
                    ngayTraDuKien = sdf.parse(tableModel.getValueAt(rowIndex, 2).toString()); // Cột 2: Ngày Trả Dự Kiến
                } else {
                    System.out.println("⚠ Không tìm thấy Mã Phiếu Mượn trong bảng: " + maPM);
                    continue;
                }
                // ✅ Tính phí phạt
                double phi = dao.tinhPhi(ngayMuon, ngayTraDuKien, currentDate, sachDaMuon.size());
                double tienPhat = dao.tinhTienPhat(ngayMuon, ngayTraDuKien, currentDate, sachDaMuon.size(), dao.getMaSachByPM(maPM));
                double tongPhi = phi + tienPhat;

                System.out.println("📌 Mã PM: " + maPM + ", Ngày Mượn: " + ngayMuon + ", Ngày Trả Dự Kiến: " + ngayTraDuKien);
                System.out.println("💰 Phí phạt: " + tienPhat + ", Tổng phí: " + tongPhi);

                // ✅ Gửi email
                OverdueEmail overdueEmail = new OverdueEmail(ngayMuon, ngayTraDuKien, sachDaMuon, tienPhat, tongPhi);
                EmailSender.send(email, "Thông báo quá hạn", overdueEmail.createEmailContent());

                System.out.println("📩 Đã gửi email quá hạn cho: " + email);
            } catch (Exception e) {
                System.out.println("❌ Lỗi khi gửi email cho Mã Phiếu Mượn: " + maPM + " - " + e.getMessage());
            }
        }

        JOptionPane.showMessageDialog(null, "📬 Đã gửi email thông báo quá hạn.");
    }

    
    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here: 
        dispose();

    }

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here: 
        searchPhieuMuon();
    }

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {
        deleteSelectedRecord();
    }

    private void loadBookNames() {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        this.conn = dbConnection.getConnection();
        String sql = "SELECT TenSach FROM Sach WHERE TrangThai = 0";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            DefaultListModel<String> listModel = new DefaultListModel<>();
            while (rs.next()) {
                listModel.addElement(rs.getString("TenSach"));
            }

            listTenSach.setModel(listModel);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách tên sách: " + ex.getMessage());
        }
    }

    private void saveBorrowingInfo() {
        String maDocGiaStr = txtMaDocGia.getText().trim();
        if (maDocGiaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã độc giả.");
            return;
        }

        java.util.Date ngayMuonUtil = txtNgayMuon.getDate();
        java.util.Date ngayTraUtil = txtNgayTra.getDate();

        if (ngayMuonUtil == null || ngayTraUtil == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày mượn và ngày trả.");
            return;
        }

        if (ngayTraUtil.before(ngayMuonUtil)) {
            JOptionPane.showMessageDialog(this, "Ngày trả phải lớn hơn ngày mượn.");
            return;
        }

        try {
            maDocGia = Integer.parseInt(maDocGiaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã độc giả phải là số.");
            return;
        }

        java.sql.Date ngayMuon = new java.sql.Date(ngayMuonUtil.getTime());
        java.sql.Date ngayTra = new java.sql.Date(ngayTraUtil.getTime());

        BorrowedTicket ticket = new BorrowedTicket();
        ticket.setNgayMuon(ngayMuon);
        ticket.setNgayTraDuKien(ngayTra);
        ticket.setMaDocGia(maDocGia);
        ticket.setTrangThai(0); 
        BorrowedTicketDAO dao = new BorrowedTicketDAO();

        List<String> bookTitles = listTenSach.getSelectedValuesList();

        if (dao.addBorrowedTicketWithBooks(ticket, bookTitles)) {
            JOptionPane.showMessageDialog(this, "Đăng ký phiếu mượn thành công.");
            // 📩 Gửi email xác nhận mượn sách
            MemberDao readerDAO = new MemberDao();
            String email = readerDAO.getEmailByReaderId(maDocGia);
            if (email != null && !email.isEmpty()) {
                EmailTemplate emailContent = EmailFactory.createEmail("BORROW_CONFIRMATION", ngayMuon, ngayTra, null, bookTitles, null, null, null);
                EmailSender.send(email, "Xác nhận mượn sách", emailContent.createEmailContent());
                JOptionPane.showMessageDialog(this, "Email xác nhận đã được gửi đến độc giả.");
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy email của độc giả.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Đăng ký phiếu mượn thất bại.");
        }
            updateTableData();
            loadListTenSach();
    }
    private void loadListTenSach() {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        this.conn = dbConnection.getConnection();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT TenSach FROM Sach WHERE TrangThai = 0"; // Lọc những sách chưa được mượn
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String tenSach = rs.getString("TenSach");
                listModel.addElement(tenSach);
            }

            listTenSach.setModel(listModel);

        } catch (SQLException ex) {
            System.out.println("Lỗi khi load danh sách tên sách: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException closeEx) {
                System.out.println("Lỗi khi đóng PreparedStatement: " + closeEx.getMessage());
            }
        }
    }

    private void deleteSelectedRecord() {
        int selectedRow = tablePhieuMuon.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu mượn để xóa.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa phiếu mượn này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Xóa dữ liệu trong cơ sở dữ liệu
        int maPM = (int) tableModel.getValueAt(selectedRow, 0); // Assume Mã Phiếu Mượn là cột đầu tiên

        // Xóa các sách trong Sach_PhieuMuon liên quan đến phiếu mượn
        deleteSachPhieuMuonRecords(maPM);

        // Xóa phiếu mượn
        deletePhieuMuonRecord(maPM);

        // Cập nhật lại dữ liệu trên JTable
        updateTableData();

        loadBookNames();
    }

    private void deleteSachPhieuMuonRecords(int maPM) {
        PreparedStatement pstmt = null;

        try {
            String sql = "DELETE FROM Sach_PhieuMuon WHERE MaPM = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, maPM);
            pstmt.executeUpdate();
            System.out.println("Đã xóa các sách trong phiếu mượn.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa sách trong phiếu mượn: " + ex.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException closeEx) {
                System.out.println("Lỗi khi đóng PreparedStatement: " + closeEx.getMessage());
            }
        }
    }

    private void deletePhieuMuonRecord(int maPM) {
        PreparedStatement pstmt = null;

        try {
            String sql = "DELETE FROM PhieuMuon WHERE MaPM = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, maPM);
            pstmt.executeUpdate();
            System.out.println("Đã xóa phiếu mượn.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa phiếu mượn: " + ex.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException closeEx) {
                System.out.println("Lỗi khi đóng PreparedStatement: " + closeEx.getMessage());
            }
        }
    }

    private void searchPhieuMuon() {
        String maPM = txtSearch.getText().trim();
        if (maPM.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã Phiếu Mượn để tìm kiếm.");
            return;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT MaPM, NgayMuon, NgayTraDuKien, NgayTraThucTe, TienPhat, MaDocGia "
                    + "FROM PhieuMuon WHERE MaPM = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maPM);
            rs = pstmt.executeQuery();

            // Xóa hết các dòng hiện tại trong tableModel
            tableModel.setRowCount(0);

            // Duyệt qua kết quả truy vấn và thêm vào tableModel
            while (rs.next()) {
                int maPhieuMuon = rs.getInt("MaPM");
                Date ngayMuon = rs.getDate("NgayMuon");
                Date ngayTraDuKien = rs.getDate("NgayTraDuKien");
                Date ngayTraThucTe = rs.getDate("NgayTraThucTe");
                int tienPhat = rs.getInt("TienPhat");
                String maDocGia = rs.getString("MaDocGia");

                // Thêm dòng mới vào tableModel
                tableModel.addRow(new Object[]{maPhieuMuon, ngayMuon, ngayTraDuKien, ngayTraThucTe, tienPhat, maDocGia});
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm phiếu mượn: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException closeEx) {
                System.out.println("Lỗi khi đóng PreparedStatement hoặc ResultSet: " + closeEx.getMessage());
            }
        }
    }

    public void updateTableData() {
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            // Lấy kết nối từ Singleton
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn == null || conn.isClosed()) {
                System.err.println("Lỗi: Kết nối bị đóng, đang mở lại...");
                conn = DatabaseConnection.getInstance().getConnection();
            }

            // Truy vấn SQL lấy dữ liệu từ bảng PhieuMuon
            String sql = "SELECT MaPM, NgayMuon, NgayTraDuKien, NgayTraThucTe, TienPhat, MaDocGia, TrangThai FROM PhieuMuon";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // Xóa toàn bộ dữ liệu cũ trong tableModel
            tableModel.setRowCount(0);

            // Duyệt qua dữ liệu từ ResultSet và thêm vào JTable
            while (rs.next()) {
                int maPM = rs.getInt("MaPM");
                Date ngayMuon = rs.getDate("NgayMuon");
                Date ngayTra = rs.getDate("NgayTraDuKien");
                Date ngayTraTT = rs.getDate("NgayTraThucTe");
                int tienPhat = rs.getInt("TienPhat");
                String maDocGia = rs.getString("MaDocGia");
                int status = rs.getInt("TrangThai");

                // Khai báo biến trạng thái
                String trangThai = "";

                // Xác định trạng thái phiếu mượn
                switch (status) {
                    case 0:
                        trangThai = "Còn hạn trả";
                        break;
                    case 1:
                        trangThai = "Đã trả";
                        break;
                    case 2:
                        trangThai = "Quá hạn trả";
                        break;
                    default:
                        trangThai = "Mất sách";
                        break;
                }

                // Thêm dòng vào bảng
                tableModel.addRow(new Object[]{maPM, ngayMuon, ngayTra, ngayTraTT, tienPhat, maDocGia, trangThai});
            }
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu phiếu mượn: " + ex.getMessage());
            ex.printStackTrace(); // In lỗi ra console để dễ debug
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException closeEx) {
                System.err.println("Lỗi khi đóng PreparedStatement hoặc ResultSet: " + closeEx.getMessage());
            }
        }
    }
    public void searchReaderEmail() {
        int selectedRow = tablePhieuMuon.getSelectedRow();
        if (selectedRow != -1) { // Kiểm tra nếu có dòng dữ liệu được chọn
            // Lấy giá trị mã độc giả từ tableModel
            DefaultTableModel tableModel = (DefaultTableModel) tablePhieuMuon.getModel();
            int columnIndex = tableModel.findColumn("Mã Độc Giả"); // Tìm chỉ số cột "Mã Độc Giả"
            if (columnIndex != -1) { // Kiểm tra nếu tìm thấy cột
                String readerID = tableModel.getValueAt(selectedRow, columnIndex).toString();
                if (!readerID.isEmpty()) {
                    try {

                        // Truy vấn lấy thông tin email của độc giả dựa trên mã độc giả
                        String query = "SELECT Email, HoTen FROM DocGia WHERE MaDocGia = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement(query);
                        preparedStatement.setInt(1, Integer.parseInt(readerID));
                        ResultSet resultSet = preparedStatement.executeQuery();
                        maDocGia = Integer.parseInt(readerID);
                        if (resultSet.next()) {
                            email = resultSet.getString("Email");
                            tenDocGia = resultSet.getString("HoTen");
                        } else {
                            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin độc giả với mã " + readerID);
                        }

                        resultSet.close();
                        preparedStatement.close();

                    } catch (NumberFormatException | SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi tìm kiếm: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Mã độc giả không được để trống.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy cột 'Mã Độc Giả' trong tableModel.");
            }
        }
    }

    public String[] getTenSachDaMuon(int maDocGia) {
        ArrayList<String> tenSachList = new ArrayList<>();

        String query = "SELECT s.TenSach "
                + "FROM Sach s "
                + "INNER JOIN Sach_PhieuMuon spm ON s.MaSach = spm.MaSach "
                + "INNER JOIN PhieuMuon pm ON spm.MaPM = pm.MaPM "
                + "WHERE pm.MaDocGia = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, maDocGia);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String tenSach = resultSet.getString("TenSach");
                    tenSachList.add(tenSach);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // In ra thông tin ngoại lệ để phân tích và sửa lỗi
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi truy vấn cơ sở dữ liệu: " + ex.getMessage());
        }

        // Chuyển đổi ArrayList thành mảng String và trả về
        return tenSachList.toArray(new String[0]);
    }

    private void openDetailPage(int rowIndex) {
    if (rowIndex >= 0 && rowIndex < tableModel.getRowCount()) {
        // Chuyển đổi chỉ số dòng nếu bảng có sắp xếp
        int modelRowIndex = tablePhieuMuon.convertRowIndexToModel(rowIndex);

        // Lấy dữ liệu từ tableModel
        Object maPhieuMuonObj = tableModel.getValueAt(modelRowIndex, 0);
        Object ngayMuonObj = tableModel.getValueAt(modelRowIndex, 1);
        Object ngayTraObj = tableModel.getValueAt(modelRowIndex, 2);
        Object maDocGiaObj = tableModel.getValueAt(modelRowIndex, 5);

        // Chuyển đổi về String, xử lý null
        String maPhieuMuon = (maPhieuMuonObj != null) ? maPhieuMuonObj.toString() : "Không có dữ liệu";
        String ngayMuon = (ngayMuonObj != null) ? ngayMuonObj.toString() : "Không có dữ liệu";
        String ngayTra = (ngayTraObj != null) ? ngayTraObj.toString() : "Không có dữ liệu";
        String maDocGia = (maDocGiaObj != null) ? maDocGiaObj.toString() : "Không có dữ liệu";

        // Mở cửa sổ chi tiết phiếu mượn
        ChiTietPhieuMuon detailPage = new ChiTietPhieuMuon(this, maPhieuMuon, ngayMuon, ngayTra, maDocGia);
        detailPage.setVisible(true);

        // Thêm sự kiện khi đóng cửa sổ
        detailPage.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                updateTableData();  // Cập nhật dữ liệu bảng
                loadBookNames();    // Load lại danh sách sách
            }
        });
    } else {
        JOptionPane.showMessageDialog(this, "Dòng dữ liệu không hợp lệ.");
    }
}


    private void sendEmail() {
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "ascf5649@gmail.com";
        String password = "qyed tqed tlbe frad"; // Thay thế bằng App Password

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Kiểm tra email hợp lệ
            if (email == null || email.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Email độc giả không hợp lệ!");
                return;
            }

            // Bật chế độ debug để kiểm tra lỗi
            session.setDebug(true);

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            msg.setSubject("Trễ hạn trả sách");

            // Lấy danh sách sách mượn
            String[] tenSachDaMuon = getTenSachDaMuon(maDocGia);
            StringBuilder emailContent = new StringBuilder();
            emailContent.append("Mã độc giả: ").append(maDocGia).append("\n");
            emailContent.append("Họ tên: ").append(tenDocGia).append("\n");
            emailContent.append("Danh sách sách đã mượn:\n");
            for (String tenSach : tenSachDaMuon) {
                emailContent.append("- ").append(tenSach).append("\n");
            }

            msg.setText(emailContent.toString());
            Transport.send(msg);

            JOptionPane.showMessageDialog(this, "Email đã gửi thành công đến: " + email);
        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi gửi email: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new PhieuMuonPage().setVisible(true);
        });
    }

    public String getBorrowedBooks(int maDocGia) {
        String sql = "SELECT s.tenSach FROM Sach s " +
                     "JOIN ChiTietPhieuMuon c ON s.maSach = c.maSach " +
                     "JOIN PhieuMuon p ON c.maPM = p.MaPM " +
                     "WHERE p.MaDocGia = ? AND p.TrangThai = 2"; // Chỉ lấy sách quá hạn
        StringBuilder books = new StringBuilder();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maDocGia);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if (books.length() > 0) books.append(", ");
                books.append(rs.getString("tenSach"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books.toString();
    }

}
