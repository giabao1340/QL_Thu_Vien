/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FactoryMethod;

import java.text.SimpleDateFormat;
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

    /**
     *
     * @return
     */
    @Override
    public String createEmailContent() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String ngayMuonStr = sdf.format(ngayMuon);
        String ngayTraDuKienStr = sdf.format(ngayTraDuKien);

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("📢 THÔNG BÁO QUÁ HẠN TRẢ SÁCH 📢\n\n");
        emailContent.append("📅 Ngày mượn: ").append(ngayMuonStr).append("\n");
        emailContent.append("📅 Ngày trả dự kiến: ").append(ngayTraDuKienStr).append("\n\n");
        emailContent.append("📚 Danh sách sách đã mượn:\n");
        
        for (String sach : sachDaMuon) {
            emailContent.append("   - ").append(sach).append("\n");
        }
        
        emailContent.append("\n💰 Tiền phạt: ").append(tienPhat).append(" VNĐ\n");
        emailContent.append("💳 Tổng phí cần thanh toán: ").append(tongPhi).append(" VNĐ\n\n");
        emailContent.append("⚠ Vui lòng đến thư viện để hoàn tất việc trả sách và thanh toán các khoản phí. Cảm ơn bạn!");
        
        return emailContent.toString();
    }
}
