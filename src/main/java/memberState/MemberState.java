package memberState;
import com.mycompany.qlthuvien.model.Member;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author minh9
 */
public interface MemberState {
     void ChangeState(MemberContext context,int maDG, int maPM);    
}
    
