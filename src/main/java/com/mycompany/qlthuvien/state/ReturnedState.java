/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien.state;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author luong
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class ReturnedState implements BookState {
    @Override
    public void updateStatus(BookContext context, int maSach, int maPM) {
        String updateSachQuery = "UPDATE Sach SET TrangThai = 0 WHERE MaSach = ?";
        String updatePhieuMuonQuery = "UPDATE PhieuMuon SET NgayTraThucTe = GETDATE() WHERE MaPM = ?";
        String checkAllReturnedQuery = "SELECT COUNT(*) FROM Sach_PhieuMuon spm " +
                                       "JOIN Sach s ON spm.MaSach = s.MaSach " +
                                       "WHERE spm.MaPM = ? AND s.TrangThai = 0";
        String updatePhieuMuonStatusQuery = "UPDATE PhieuMuon SET TrangThai = 1 WHERE MaPM = ?";

        try (PreparedStatement pstmtUpdateSach = context.getConnection().prepareStatement(updateSachQuery)) {
            pstmtUpdateSach.setInt(1, maSach);
            pstmtUpdateSach.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReturnedState.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (PreparedStatement pstmtUpdatePhieuMuon = context.getConnection().prepareStatement(updatePhieuMuonQuery)) {
            pstmtUpdatePhieuMuon.setInt(1, maPM);
            pstmtUpdatePhieuMuon.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReturnedState.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Kiểm tra xem tất cả sách trong phiếu mượn đã được trả chưa
        try (PreparedStatement pstmtCheckAllReturned = context.getConnection().prepareStatement(checkAllReturnedQuery)) {
            pstmtCheckAllReturned.setInt(1, maPM);
            ResultSet rs = pstmtCheckAllReturned.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) { // Không còn sách nào chưa trả
                try (PreparedStatement pstmtUpdatePhieuMuonStatus = context.getConnection().prepareStatement(updatePhieuMuonStatusQuery)) {
                    pstmtUpdatePhieuMuonStatus.setInt(1, maPM);
                    int affectedRows = pstmtUpdatePhieuMuonStatus.executeUpdate();
                    System.out.println("Số dòng cập nhật: " + affectedRows);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReturnedState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

