/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EmailStrategy;

import java.util.Date;
import java.util.List;

/**
 *
 * @author luong
 */
public interface EmailStrategy {
    void sendEmail(String to, Date ngayMuon, Date ngayTra, List<String> sachDaMuon);
}
