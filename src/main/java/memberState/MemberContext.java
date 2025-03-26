/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memberState;
import com.mycompany.qlthuvien.model.Member;
import com.mycompany.qlthuvien.DatabaseConnection;
import java.sql.Connection;
/**
 *
 * @author minh9
 */
public class MemberContext {
    private MemberState state;
    private DatabaseConnection db;
    
    
    public MemberContext() {
        this.db = DatabaseConnection.getInstance();
    }
    
    
    public void setState(MemberState state) {
            this.state = state;
    }
    
    
    public Connection getConnection() {
        return  db.getConnection();
    }
    
    
    public void ChangeState(int maDG, int maPM) {
        if(state != null) {
            state.ChangeState(this,maDG,maPM);
        }
    }
}
