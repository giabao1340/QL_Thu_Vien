/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProxyMember;

import com.mycompany.qlthuvien.model.BorrowedTicket;
import java.util.List;

/**
 *
 * @author minh9
 */
public interface TicketBorrowing {
    boolean borrowedTicket(BorrowedTicket ticket, List<String> bookTitles);
}
