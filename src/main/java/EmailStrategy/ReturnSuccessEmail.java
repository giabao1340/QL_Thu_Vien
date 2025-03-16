/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EmailStrategy;

/**
 *
 * @author luong
 */
import com.mycompany.qlthuvien.DatabaseConnection;

import java.util.Date;
import java.util.List;
public class ReturnSuccessEmail implements EmailStrategy {
    @Override
    public void sendEmail(String to, Date ngayMuon, Date ngayTra, List<String> sachDaMuon) {
        String subject = "Xác nhận trả sách thành công";
        String message = "Chào bạn,\n\nBạn đã trả sách thành công tại thư viện.\n\n"
                + "📅 Ngày mượn: " + ngayMuon + "\n"
                + "📅 Ngày trả: " + ngayTra + "\n\n"
                + "📚 Danh sách sách đã trả:\n- " + String.join("\n- ", sachDaMuon) + "\n\n"
                + "Cảm ơn bạn đã sử dụng dịch vụ thư viện!";

        EmailService.sendEmail(to, subject, message);
    }
}
