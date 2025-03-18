/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien.dao;

import com.mycompany.BorrowedTicketStates.BorrowedTicketContext;
import com.mycompany.BorrowedTicketStates.StillDueState;
import com.mycompany.qlthuvien.bookstate.BookContext;
import com.mycompany.qlthuvien.bookstate.BorrowedState;
import com.mycompany.qlthuvien.DatabaseConnection;
import com.mycompany.qlthuvien.model.BorrowedTicket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author luong
 */
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

public class BorrowedTicketDAO {
    private final Connection conn;
    public BorrowedTicketDAO() {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        this.conn = dbConnection.getConnection(); 
    }
    
    public List<BorrowedTicket> getAllBorrowedTickets() {
        List<BorrowedTicket> tickets = new ArrayList<>();
        String query = "SELECT MaPM, NgayMuon, NgayTraDuKien, NgayTraThucTe, TienPhat, MaDocGia, TrangThai FROM PhieuMuon";
        
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                BorrowedTicket ticket = new BorrowedTicket(
                    rs.getInt("MaPM"),
                    rs.getDate("NgayMuon"),
                    rs.getDate("NgayTraDuKien"),
                    rs.getDate("NgayTraThucTe"),
                    rs.getDouble("TienPhat"),
                    rs.getInt("MaDocGia"),
                    rs.getInt("TrangThai")
                );
                tickets.add(ticket);
            }
        } catch (SQLException e) {
        }
        return tickets;
    }
    
    public boolean addBorrowedTicket(BorrowedTicket ticket) {
        String query = "INSERT INTO PhieuMuon (NgayMuon, NgayTraDuKien, NgayTraThucTe, TienPhat, MaDocGia, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDate(1, (Date) ticket.getNgayMuon());
            pstmt.setDate(2, (Date) ticket.getNgayTraDuKien());
            pstmt.setDate(3, (Date) ticket.getNgayTraThucTe());
            pstmt.setDouble(4, ticket.getTienPhat());
            pstmt.setInt(5, ticket.getMaDocGia());
            pstmt.setInt(6, ticket.getTrangThai());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    ticket.setMaPM(generatedKeys.getInt(1));
                }
                int maPM = ticket.getMaPM();
                BorrowedTicketContext context = new BorrowedTicketContext(conn);
                context.setState(new StillDueState());
                context.updateSachStatus(maPM);
                
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }
public boolean addBorrowedTicketWithBooks(BorrowedTicket ticket, List<String> bookTitles) {
        PreparedStatement pstmtPhieuMuon = null;
        PreparedStatement pstmtSachPhieuMuon = null;
        PreparedStatement pstmtFindMaSach = null;
        ResultSet rsMaSach = null;

        try {
            conn.setAutoCommit(false);

            // Thêm phiếu mượn vào bảng PhieuMuon
            String insertPhieuMuonSQL = "INSERT INTO PhieuMuon (NgayMuon, NgayTraDuKien, MaDocGia, TrangThai) VALUES (?, ?, ?, 0)";
            pstmtPhieuMuon = conn.prepareStatement(insertPhieuMuonSQL, Statement.RETURN_GENERATED_KEYS);
            pstmtPhieuMuon.setDate(1, (Date) ticket.getNgayMuon());
            pstmtPhieuMuon.setDate(2, (Date) ticket.getNgayTraDuKien());
            pstmtPhieuMuon.setInt(3, ticket.getMaDocGia());

            int rowsInsertedPhieuMuon = pstmtPhieuMuon.executeUpdate();

            if (rowsInsertedPhieuMuon > 0) {
                ResultSet generatedKeys = pstmtPhieuMuon.getGeneratedKeys();
                int maPM = -1;
                if (generatedKeys.next()) {
                    maPM = generatedKeys.getInt(1);
                    ticket.setMaPM(maPM);
                }
                generatedKeys.close();

                // Tìm mã sách từ tên sách
                String findMaSachSQL = "SELECT MaSach FROM Sach WHERE TenSach = ?";
                pstmtFindMaSach = conn.prepareStatement(findMaSachSQL);

                // Thêm vào bảng Sach_PhieuMuon
                String insertSachPhieuMuonSQL = "INSERT INTO Sach_PhieuMuon (MaSach, MaPM) VALUES (?, ?)";
                pstmtSachPhieuMuon = conn.prepareStatement(insertSachPhieuMuonSQL);

                for (String tenSach : bookTitles) {
                    pstmtFindMaSach.setString(1, tenSach);
                    rsMaSach = pstmtFindMaSach.executeQuery();

                    while (rsMaSach.next()) {
                        int maSach = rsMaSach.getInt("MaSach");

                        pstmtSachPhieuMuon.setInt(1, maSach);
                        pstmtSachPhieuMuon.setInt(2, maPM);
                        pstmtSachPhieuMuon.executeUpdate();
                        //Set state
                        BookContext bookContext = new BookContext(conn);
                        bookContext.setState(new BorrowedState());
                        bookContext.updateSachStatus(maSach, maPM);
                    }
                }

                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (rsMaSach != null) rsMaSach.close();
                if (pstmtFindMaSach != null) pstmtFindMaSach.close();
                if (pstmtSachPhieuMuon != null) pstmtSachPhieuMuon.close();
                if (pstmtPhieuMuon != null) pstmtPhieuMuon.close();
                conn.setAutoCommit(true);
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
        return false;
    }

    public String getReaderEmailByPhieuMuon(int maPM) {
        String email = null;
        String query = "SELECT dg.Email FROM DocGia dg " +
                       "JOIN PhieuMuon pm ON dg.MaDocGia = pm.MaDocGia " +
                       "WHERE pm.MaPM = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, maPM);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                email = rs.getString("Email");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorrowedTicketDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return email;
    }
            public java.util.Date getNgayMuonByPhieuMuon(int maPM) {
            String query = "SELECT NgayMuon FROM PhieuMuon WHERE MaPM = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, maPM);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getDate("NgayMuon");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        public java.util.Date getNgayTraByPhieuMuon(int maPM) {
            String query = "SELECT NgayTraDuKien FROM PhieuMuon WHERE MaPM = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, maPM);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getDate("NgayTraDuKien");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        public List<String> getReturnedBooks(JTable tableSachMuon) {
            List<String> returnedBooks = new ArrayList<>();
            int[] selectedRows = tableSachMuon.getSelectedRows();

            for (int row : selectedRows) {
                String tenSach = tableSachMuon.getValueAt(row, 1).toString(); // Cột thứ 2 là tên sách
                returnedBooks.add(tenSach);
            }
            return returnedBooks;
        }
        
    public double tinhPhi(java.util.Date ngayMuon, java.util.Date ngayTra, java.util.Date ngayTraThucTe, int soSachMuon) {
        // Tính số ngày đã mượn
        long daysBorrowed = (ngayTraThucTe.getTime() - ngayMuon.getTime()) / (1000 * 60 * 60 * 24);
        // Tính số tuần mượn (lấy phần nguyên)
        int weeksBorrowed = (int) Math.ceil(daysBorrowed / 7.0);
        double phi;
        if (weeksBorrowed < 7) {
            phi = 3000 * soSachMuon;
        } else {
            phi = weeksBorrowed * 5000 * soSachMuon;
        }
        return phi;
    }
    public double tinhTienPhat(java.util.Date ngayMuon, java.util.Date ngayTra, java.util.Date ngayTraThucTe, int soSachMuon, List<Integer> sachDaMuon) {
        // Tính số ngày quá hạn
        long daysLate = (ngayTraThucTe.getTime() - ngayTra.getTime()) / (1000 * 60 * 60 * 24);
        System.out.println("\nngay tre: " + daysLate);
        // Tính tiền phạt
        double phat = 0;
        if (daysLate > 0) {
            if (daysLate <= 6) {
                phat = 3000 * soSachMuon;
            } else {
                int weeksLate = (int) Math.ceil(daysLate / 7.0);
                System.out.println("tuan tre: " + weeksLate);
                phat = weeksLate * 5000 * soSachMuon;
            }
        }

        // Tính tổng giá sách bị mất
        double totalBookPrice = 0;
        for (int bookId : sachDaMuon) {  // Duyệt danh sách mã sách
            if (isSachLoss(bookId)) {  // Kiểm tra sách có bị mất không
                totalBookPrice += getPriceById(bookId); // Lấy giá sách và cộng vào tổng
            }
        }

        System.out.println("Tiền phạt: " + phat);
        System.out.println("Tổng giá sách mất: " + totalBookPrice);
        return phat + totalBookPrice;
    }
   
    private boolean isSachLoss(int maSach) {
        String query = "SELECT TrangThai FROM Sach WHERE MaSach = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, maSach);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int trangThai = rs.getInt("TrangThai");
                    return trangThai == 2; // Trả về true nếu trạng thái = 2
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public double getPriceById(int bookId) {
        double price = 0;
        try {String sql = "SELECT GiaSach FROM Sach WHERE MaSach = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                price = rs.getDouble("giaSach");
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(price);
        return price;
}
    public List<Integer> getMaSachByPM(int maPM) {
        List<Integer> maSachList = new ArrayList<>();
        String sql = "SELECT MaSach FROM Sach_PhieuMuon WHERE MaPM = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPM);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                maSachList.add(rs.getInt("MaSach"));
                System.out.print(", Mã sách: " + rs.getInt("MaSach"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maSachList;
    }

    public List<String> getSachDaMuon(int maPM) {
    List<String> sachList = new ArrayList<>();
    String sql = "SELECT s.TenSach FROM Sach s " +
                 "JOIN Sach_PhieuMuon spm ON s.MaSach = spm.MaSach " +
                 "WHERE spm.MaPM = ?";

    DatabaseConnection db = DatabaseConnection.getInstance();
    Connection connect = db.getConnection();

    try (PreparedStatement stmt = connect.prepareStatement(sql)) {
        stmt.setInt(1, maPM);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            sachList.add(rs.getString("TenSach")); // Lấy tên sách
            System.out.print(", Mã sách: " + rs.getInt("TenSach"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return sachList;
    }

    public int getState(int maPM) {
        int trangThai = -1; // Giá trị mặc định nếu không tìm thấy
        String sql = "SELECT TrangThai FROM PhieuMuon WHERE MaPM = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maPM);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                trangThai = rs.getInt("TrangThai"); // Lấy giá trị trạng thái
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy trạng thái phiếu mượn: " + e.getMessage());
        }

        return trangThai; // Trả về trạng thái của phiếu mượn
    }

    public List<Integer> getListPMQuaHan() {
        List<Integer> danhSachPM = new ArrayList<>();
        String sql = "SELECT MaPM FROM PhieuMuon WHERE TrangThai = 2";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                danhSachPM.add(rs.getInt("MaPM"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachPM;
    }

    // Lấy mã độc giả theo mã phiếu mượn
    public int getMaDocGiaByPM(int maPM) {
        String sql = "SELECT MaDocGia FROM PhieuMuon WHERE MaPM = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPM);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("MaDocGia");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }

}