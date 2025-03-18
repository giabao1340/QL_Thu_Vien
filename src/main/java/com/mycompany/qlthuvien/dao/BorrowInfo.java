/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien.dao;

import com.mycompany.qlthuvien.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author luong
 */
public class BorrowInfo {
    private Date ngayMuon;
    private Date ngayTraDuKien;
    private List<String> sachDaMuon;
    private double tienPhat;
    private double tongPhi;
    private DatabaseConnection db = DatabaseConnection.getInstance();
    Connection conn = db.getConnection();
    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public Date getNgayTraDuKien() {
        return ngayTraDuKien;
    }

    public void setNgayTraDuKien(Date ngayTraDuKien) {
        this.ngayTraDuKien = ngayTraDuKien;
    }

    public List<String> getSachDaMuon() {
        return sachDaMuon;
    }

    public void setSachDaMuon(List<String> sachDaMuon) {
        this.sachDaMuon = sachDaMuon;
    }

    public double getTienPhat() {
        return tienPhat;
    }

    public void setTienPhat(double tienPhat) {
        this.tienPhat = tienPhat;
    }

    public double getTongPhi() {
        return tongPhi;
    }

    public void setTongPhi(double tongPhi) {
        this.tongPhi = tongPhi;
    }
    
        public double tinhPhi(java.util.Date ngayMuon, java.util.Date ngayTra, java.util.Date ngayTraThucTe, int soSachMuon) {
        // Tính số ngày đã mượn
        long daysBorrowed = (ngayTraThucTe.getTime() - ngayMuon.getTime()) / (1000 * 60 * 60 * 24);
        // Tính số ngày quá hạn
        long daysLate = (ngayTraThucTe.getTime() - ngayTra.getTime()) / (1000 * 60 * 60 * 24);

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
    public double tinhTienPhat(java.util.Date ngayMuon, java.util.Date ngayTra, java.util.Date ngayTraThucTe, int soSachMuon, JTable table) {
        // Tính số ngày đã mượn
        long daysBorrowed = (ngayTraThucTe.getTime() - ngayMuon.getTime()) / (1000 * 60 * 60 * 24);
        // Tính số ngày quá hạn
        long daysLate = (ngayTraThucTe.getTime() - ngayTra.getTime()) / (1000 * 60 * 60 * 24);

        // Tính tiền phạt
        double phat = 0;
        if (daysLate > 0) {
            if (daysLate <= 6) {
                phat = 3000 * soSachMuon;
            } else {
                int weeksLate = (int) Math.ceil(daysLate / 7.0);
                phat = weeksLate * 5000 * soSachMuon;
            }
        }
                // Cộng tổng giá sách bị quá hạn vào tiền phạt
    
        double totalBookPrice = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            int bookId = (int) table.getValueAt(i, 0); // Cột 1 chứa mã sách
            if (isSachLoss(bookId)) {
            totalBookPrice += getPriceById(bookId);
            System.out.println(totalBookPrice);
            }
        }
        System.out.println(totalBookPrice);
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

    public int getMaPM(int maDocGia) {
        String sql = "SELECT MaPM FROM PhieuMuon WHERE MaDocGia = ? ORDER BY NgayMuon DESC LIMIT 1";
        db = DatabaseConnection.getInstance();
        Connection connect = db.getConnection();

        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setInt(1, maDocGia);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("MaPM"); // Lấy mã phiếu mượn gần nhất
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }
}
