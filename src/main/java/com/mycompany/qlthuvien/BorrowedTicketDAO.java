/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien;

import java.sql.Connection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luong
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowedTicketDAO {
    private Connection conn;
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
            e.printStackTrace();
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
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean addBorrowedTicketWithBooks(BorrowedTicket ticket, List<String> bookTitles) {
        PreparedStatement pstmtPhieuMuon = null;
        PreparedStatement pstmtSachPhieuMuon = null;
        PreparedStatement pstmtFindMaSach = null;
        ResultSet rsMaSach = null;
        PreparedStatement pstmtUpdateTrangThai = null;
        
        try {
            conn.setAutoCommit(false);
            
            // Thêm phiếu mượn vào bảng PhieuMuon
            String insertPhieuMuonSQL = "INSERT INTO PhieuMuon (NgayMuon, NgayTraDuKien, MaDocGia) VALUES (?, ?, ?)";
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
                
                // Cập nhật trạng thái sách
                String updateTrangThaiSachSQL = "UPDATE Sach SET TrangThai = 1 WHERE MaSach = ?";
                pstmtUpdateTrangThai = conn.prepareStatement(updateTrangThaiSachSQL);
                
                for (String tenSach : bookTitles) {
                    pstmtFindMaSach.setString(1, tenSach);
                    rsMaSach = pstmtFindMaSach.executeQuery();
                    while (rsMaSach.next()) {
                        String maSach = rsMaSach.getString("MaSach");
                        pstmtSachPhieuMuon.setString(1, maSach);
                        pstmtSachPhieuMuon.setInt(2, maPM);
                        pstmtSachPhieuMuon.executeUpdate();
                        
                        pstmtUpdateTrangThai.setString(1, maSach);
                        pstmtUpdateTrangThai.executeUpdate();
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
                if (pstmtUpdateTrangThai != null) pstmtUpdateTrangThai.close();
                conn.setAutoCommit(true);
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
        return false;
    }
}
