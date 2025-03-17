/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.BorrowedTicketStates;

import com.mycompany.qlthuvien.bookstate.BookContext;

/**
 *
 * @author luong
 */
public interface BorrowedTicketState {
    void updateStatus(BorrowedTicketContext context, int maPM);
}
