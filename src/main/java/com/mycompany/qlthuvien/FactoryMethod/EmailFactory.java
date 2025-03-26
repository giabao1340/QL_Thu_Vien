    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien.FactoryMethod;

import java.util.Date;
import java.util.List;

/**
 *
 * @author luong
 */
public class EmailFactory {
    public static EmailTemplate createEmail(String type, Date ngayMuon, Date ngayTraDuKien, Date ngayTraThucTe, List<String> sachDaMuon, Double phi, Double tienPhat, Double tongPhi) {
        switch (type) {
            case "BORROW_CONFIRMATION":
                return new BorrowConfirmationEmail(ngayMuon, ngayTraDuKien, sachDaMuon);
            case "RETURN_CONFIRMATION":
                return new ReturnConfirmationEmail(ngayMuon, ngayTraDuKien, ngayTraThucTe, sachDaMuon, phi);
            case "REMINDER":
                return new ReminderEmail(ngayMuon, ngayTraDuKien, sachDaMuon);
            case "OVERDUE":
                return new OverdueEmail(ngayMuon, ngayTraDuKien, sachDaMuon, tienPhat, tongPhi);
            case "LOSS":
                return new LossEmail(ngayMuon, ngayTraDuKien, sachDaMuon, tienPhat, tongPhi);
            default:
                throw new IllegalArgumentException("Loại email không hợp lệ: " + type);
        }
    }
}
