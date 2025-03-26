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
public class ReturnConfirmationEmail implements EmailTemplate {
    private Date ngayMuon;
    private Date ngayTraDuKien;
    private Date ngayTraThucTe;
    private List<String> sachDaMuon;
    private double phi;

    public ReturnConfirmationEmail(Date ngayMuon, Date ngayTraDuKien, Date ngayTraThucTe, List<String> sachDaMuon, double phi) {
        this.ngayMuon = ngayMuon;
        this.ngayTraDuKien = ngayTraDuKien;
        this.ngayTraThucTe = ngayTraThucTe;
        this.sachDaMuon = sachDaMuon;
        this.phi = phi;
    }

    @Override
    public String createEmailContent() {
        return "Chào bạn,\n\nBạn đã trả sách thành công.\n\n" +
                "📅 Ngày mượn: " + ngayMuon + "\n" +
                "📅 Ngày trả dự kiến: " + ngayTraDuKien + "\n" +
                "📅 Ngày trả thực tế: " + ngayTraThucTe + "\n\n" +
                "📚 Danh sách sách đã mượn:\n- " + String.join("\n- ", sachDaMuon) + "\n\n" +
                "💰 Phí: " + phi + " VNĐ\n\n" +
                "Cảm ơn bạn đã sử dụng dịch vụ thư viện!";
    }
}
