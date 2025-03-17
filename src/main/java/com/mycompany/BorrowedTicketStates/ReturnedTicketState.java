/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.BorrowedTicketStates;

import com.mycompany.qlthuvien.bookstate.BookContext;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luong
 */
public class ReturnedTicketState implements BorrowedTicketState {
    @Override
    public void updateStatus(BorrowedTicketContext context, int maPM) {
        String updateSachQuery = "UPDATE PhieuMuon SET TrangThai = 1 WHERE MaPM = ?";

        try (PreparedStatement pstmtUpdateSach = context.getConnection().prepareStatement(updateSachQuery)) {
            pstmtUpdateSach.setInt(1, maPM);
            pstmtUpdateSach.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReturnedTicketState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
