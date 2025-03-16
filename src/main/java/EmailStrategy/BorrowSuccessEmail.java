/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EmailStrategy;

/**
 *
 * @author luong
 */
import java.util.Date;
import java.util.List;
public class BorrowSuccessEmail implements EmailStrategy {
    @Override
    public void sendEmail(String to, Date ngayMuon, Date ngayTra, List<String> sachDaMuon) {
        String subject = "Xác nhận mượn sách thành công";
        String message = "Chào bạn,\n\nBạn đã mượn sách thành công tại thư viện.\n\n"
                + "📅 Ngày mượn: " + ngayMuon + "\n"
                + "📅 Ngày trả dự kiến: " + ngayTra + "\n\n"
                + "📚 Danh sách sách đã mượn:\n- " + String.join("\n- ", sachDaMuon) + "\n\n"
                + "Hãy đảm bảo trả sách đúng hạn nhé!\n\n"
                + "Cảm ơn bạn đã sử dụng dịch vụ thư viện!";

        EmailService.sendEmail(to, subject, message);    }
}
