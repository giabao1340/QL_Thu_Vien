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

        JOptionPane.showMessageDialog(null, "Trả sách thành công!!");
    }
}
