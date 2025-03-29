/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProxyMember;

import com.mycompany.qlthuvien.DatabaseConnection;
import com.mycompany.qlthuvien.model.BorrowedTicket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author minh9
 */
public class BorrowingProxy implements TicketBorrowing {
    private RealBookBorrowing realBookBorrowing;
    private int maDocGia;
    
    public BorrowingProxy (int maDocGia)
    {
        this.maDocGia = maDocGia;
        this.realBookBorrowing = new RealBookBorrowing();
    }
    
    
    @Override
    public boolean borrowedTicket(BorrowedTicket ticket, List<String> bookTitles) {
        String checkQuery = "SELECT 1 FROM DocGia WHERE MaDocGia = ? AND TrangThaiThe IN (2, 3)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(checkQuery)) {
            ps.setInt(1, maDocGia);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Thành viên này không được phép mượn sách thẻ đã bị tạm ngưng hoặc khóa.");
                return false;
            } else {
                return realBookBorrowing.borrowedTicket(ticket, bookTitles);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra trạng thái độc giả.");
            e.printStackTrace();
            return false;
        }
    }
}
