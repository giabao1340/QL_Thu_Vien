/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memberState;

import com.mycompany.qlthuvien.bookstate.ReturnedState;
import com.mycompany.qlthuvien.model.Member;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author minh9
 */
public class SuspendedMemberState extends MemberState{
    SuspendedMemberState(Member member){
        super(member);
    }
    @Override
    public void ChangeState(MemberContext context,int maDG, int maPM) {
        String query = "UPDATE DocGia SET TrangThaiThe = 3 WHERE MaDocGia IN (SELECT MaDocGia FROM PhieuMuon WHERE TrangThai = 2 and MaPM = ?)";
        try (PreparedStatement pstmtUpdateMem = context.getConnection().prepareStatement(query)) {
             pstmtUpdateMem.setInt(1, maPM);
            pstmtUpdateMem.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReturnedState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
