/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien;

/**
 *
 * @author luong
 */
public interface BookState {
    void borrowBook(BookContext context);  // Mượn sách
    void returnBook(BookContext context);  // Trả sách
    void markAsLost(BookContext context);  // Đánh dấu là mất
}