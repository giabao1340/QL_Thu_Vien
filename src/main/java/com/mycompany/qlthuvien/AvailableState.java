/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien;

/**
 *
 * @author luong
 */
public class AvailableState implements BookState {

    @Override
    public void borrowBook(BookContext context) {
        System.out.println("Sách đã được mượn!");
        context.setState(new BorrowedState());
    }

    @Override
    public void returnBook(BookContext context) {
        System.out.println("Sách đã sẵn có, không cần trả.");
    }

    @Override
    public void markAsLost(BookContext context) {
        System.out.println("Sách bị mất!");
        context.setState(new LostState());
    }
}