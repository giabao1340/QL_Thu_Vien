
package com.mycompany.BorrowedTicketStates;

/**
 *
 * @author luong
 */
import com.mycompany.qlthuvien.bookstate.*;
import com.mycompany.qlthuvien.DatabaseConnection;
import java.sql.Connection;

public class BorrowedTicketContext {//Mượn - Trả - Mất
    private BorrowedTicketState state;
    private Connection conn;
    private DatabaseConnection db;
    public BorrowedTicketContext(Connection conn) {
        db = DatabaseConnection.getInstance();
        this.conn = conn;
    }

    public void setState(BorrowedTicketState state) {
        this.state = state;
    }

    public Connection getConnection() {
        return conn;
    }

    public void updateSachStatus(int maPM) {
        if (state != null) {
            state.updateStatus(this, maPM);
        }
    }
}
