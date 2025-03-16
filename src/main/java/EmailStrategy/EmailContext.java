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
public class EmailContext {
    private EmailStrategy strategy;

    public void setStrategy(EmailStrategy strategy) {
        this.strategy = strategy;
    }

    public void sendEmail(String to, Date ngayMuon, Date ngayTra, List<String> sachDaMuon) {
        if (strategy != null) {
            strategy.sendEmail(to, ngayMuon, ngayTra, sachDaMuon);
        } else {
            throw new IllegalStateException("Email strategy chưa được thiết lập!");
        }
    }
}
