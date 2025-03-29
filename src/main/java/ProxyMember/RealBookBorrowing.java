/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProxyMember;

import com.mycompany.qlthuvien.DatabaseConnection;
import com.mycompany.qlthuvien.dao.BorrowedTicketDAO;
import com.mycompany.qlthuvien.model.BorrowedTicket;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author minh9
 */
public class RealBookBorrowing implements TicketBorrowing{
    private BorrowedTicketDAO borrowed;
    private DatabaseConnection db;
    public RealBookBorrowing()
    {
        this.borrowed = new BorrowedTicketDAO();
        this.db = DatabaseConnection.getInstance();
    }
    
        public Connection getConnection() {
        return  db.getConnection();
    }
    
    
    public boolean borrowedTicket(BorrowedTicket ticket, List<String> bookTitles) {
        return borrowed.addBorrowedTicketWithBooks(ticket, bookTitles);
    }       
}
