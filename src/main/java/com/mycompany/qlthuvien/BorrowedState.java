package com.mycompany.qlthuvien;


import com.mycompany.qlthuvien.AvailableState;
import com.mycompany.qlthuvien.BookContext;
import com.mycompany.qlthuvien.BookState;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luong
 */
public class BorrowedState implements BookState {

    @Override
    public void borrowBook(BookContext context) {
        System.out.println("Sách đã được mượn, không thể mượn tiếp.");
    }

    @Override
    public void returnBook(BookContext context) {
        System.out.println("Sách đã được trả lại!");
        context.setState(new AvailableState());
    }

    @Override
    public void markAsLost(BookContext context) {
        System.out.println("Sách bị mất trong khi mượn!");
        context.setState(new LostState());
    }
}