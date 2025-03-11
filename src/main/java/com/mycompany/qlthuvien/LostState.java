/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien;

/**
 *
 * @author luong
 */
public class LostState implements BookState {

    @Override
    public void borrowBook(BookContext context) {
        System.out.println("Sách bị mất, không thể mượn.");
    }

    @Override
    public void returnBook(BookContext context) {
        System.out.println("Sách đã mất, không thể trả.");
    }

    @Override
    public void markAsLost(BookContext context) {
        System.out.println("Sách đã bị đánh dấu mất trước đó.");
    }
}