/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien.bookstate;

/**
 *
 * @author luong
 */
public interface BookState {
    void updateStatus(BookContext context, int maSach, int maPM);
}
