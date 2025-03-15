package com.mycompany.qlthuvien;
    
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luong
 */
public class BorrowedState implements BookState {
    @Override
    public void updateStatus(BookContext context, int maSach, int maPM) {
        String updateSachQuery = "UPDATE Sach SET TrangThai = 1 WHERE MaSach = ?";

        try (PreparedStatement pstmtUpdateSach = context.getConnection().prepareStatement(updateSachQuery)) {
            pstmtUpdateSach.setInt(1, maSach);
            pstmtUpdateSach.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReturnedState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
