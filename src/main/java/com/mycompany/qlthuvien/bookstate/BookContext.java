
package com.mycompany.qlthuvien.bookstate;

/**
 *
 * @author luong
 */
import com.mycompany.qlthuvien.DatabaseConnection;
import java.sql.Connection;

public class BookContext {//Mượn - Trả - Mất
    private BookState state;
    private Connection conn;
    private DatabaseConnection db;
    public BookContext(Connection conn) {
        db = DatabaseConnection.getInstance();
        this.conn = conn;
    }

    public void setState(BookState state) {
        this.state = state;
    }

    public Connection getConnection() {
        return conn;
    }

    public void updateSachStatus(int maSach, int maPM) {
        if (state != null) {
            state.updateStatus(this, maSach, maPM);
        }
    }
}
