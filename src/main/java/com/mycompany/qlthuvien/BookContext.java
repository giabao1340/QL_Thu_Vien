/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien;

/**
 *
 * @author luong
 */
public class BookContext {
    private BookState state;

    public BookContext() {
        this.state = new AvailableState(); // Mặc định sách sẵn có
    }

    public void setState(BookState state) {
        this.state = state;
    }

    public void borrowBook() {
        state.borrowBook(this);
    }

    public void returnBook() {
        state.returnBook(this);
    }

    public void markAsLost() {
        state.markAsLost(this);
    }
}