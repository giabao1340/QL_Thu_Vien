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
public class ExpiredMemberState extends MemberState {
    ExpiredMemberState(Member member){
        super(member);
    }
    @Override
    public void ChangeState(MemberContext context,int maDG, int maPM) {
        String query = "Update DocGia Set TrangThaiThe  = 0 where GETDATE() > NgayHetHan";
        try (PreparedStatement pstmtUpdateMem = context.getConnection().prepareStatement(query)) {
            pstmtUpdateMem.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReturnedState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
