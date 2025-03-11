package com.mycompany.qlthuvien;
    
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
        System.out.println("Sách đang được mượn.");
        JOptionPane.showMessageDialog(null, "Sách đang mượn, không thể cập nhật.");
    }
}
