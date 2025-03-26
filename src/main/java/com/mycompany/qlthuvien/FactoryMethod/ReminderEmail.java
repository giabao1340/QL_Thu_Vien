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
public class ReminderEmail implements EmailTemplate {
    private Date ngayMuon;
    private Date ngayTraDuKien;
    private List<String> sachDaMuon;

    public ReminderEmail(Date ngayMuon, Date ngayTraDuKien, List<String> sachDaMuon) {
        this.ngayMuon = ngayMuon;
        this.ngayTraDuKien = ngayTraDuKien;
        this.sachDaMuon = sachDaMuon;
    }

    @Override
    public String createEmailContent() {
        return "Chào bạn,\n\nĐây là lời nhắc nhở bạn cần trả sách trước ngày " + ngayTraDuKien + ".\n\n" +
                "📅 Ngày mượn: " + ngayMuon + "\n" +
                "📅 Ngày trả dự kiến: " + ngayTraDuKien + "\n\n" +
                "📚 Danh sách sách đã mượn:\n- " + String.join("\n- ", sachDaMuon) + "\n\n" +
                "Hãy đảm bảo trả sách đúng hạn để tránh phí phạt.\n\nCảm ơn bạn!";
    }
}
