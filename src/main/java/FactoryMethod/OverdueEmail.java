/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FactoryMethod;

import java.util.Date;
import java.util.List;

/**
 *
 * @author luong
 */
public class OverdueEmail implements EmailTemplate {
    private Date ngayMuon;
    private Date ngayTraDuKien;
    private List<String> sachDaMuon;
    private double tienPhat;
    private double tongPhi;

    public OverdueEmail(Date ngayMuon, Date ngayTraDuKien, List<String> sachDaMuon, double tienPhat, double tongPhi) {
        this.ngayMuon = ngayMuon;
        this.ngayTraDuKien = ngayTraDuKien;
        this.sachDaMuon = sachDaMuon;
        this.tienPhat = tienPhat;
        this.tongPhi = tongPhi;
    }

    @Override
    public String createEmailContent() {
        return "Chào bạn,\n\nBạn đã quá hạn trả sách!\n\n" +
                "📅 Ngày mượn: " + ngayMuon + "\n" +
                "📅 Ngày trả dự kiến: " + ngayTraDuKien + "\n\n" +
                "📚 Danh sách sách đã mượn:\n- " + String.join("\n- ", sachDaMuon) + "\n\n" +
                "💰 Phí phạt: " + tienPhat + " VNĐ\n" +
                "💰 Tổng phí cần thanh toán: " + tongPhi + " VNĐ\n\n" +
                "Vui lòng hoàn thành việc trả sách ngay lập tức.\n\nCảm ơn bạn!";
    }
}
