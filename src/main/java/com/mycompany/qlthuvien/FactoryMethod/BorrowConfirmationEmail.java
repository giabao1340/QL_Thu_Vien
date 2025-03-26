/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien.FactoryMethod;

import com.mycompany.qlthuvien.model.BorrowedTicket;
import java.util.Date;
import java.util.List;

/**
 *
 * @author luong
 */
public class BorrowConfirmationEmail implements EmailTemplate {
    private Date ngayMuon;
    private Date ngayTraDuKien;
    private List<String> sachDaMuon;

    public BorrowConfirmationEmail(Date ngayMuon, Date ngayTraDuKien, List<String> sachDaMuon) {
        this.ngayMuon = ngayMuon;
        this.ngayTraDuKien = ngayTraDuKien;
        this.sachDaMuon = sachDaMuon;
    }

    @Override
    public String createEmailContent() {
        return "Chào bạn,\n\nBạn đã mượn sách thành công tại thư viện.\n\n" +
                "📅 Ngày mượn: " + ngayMuon + "\n" +
                "📅 Ngày trả dự kiến: " + ngayTraDuKien + "\n\n" +
                "📚 Danh sách sách đã mượn:\n- " + String.join("\n- ", sachDaMuon) + "\n\n" +
                "Hãy đảm bảo trả sách đúng hạn nhé!\n\nCảm ơn bạn đã sử dụng dịch vụ thư viện!";
    }
}
