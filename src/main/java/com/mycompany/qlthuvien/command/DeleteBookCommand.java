/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien.command;

import com.mycompany.qlthuvien.dao.BookDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luong
 */
public class DeleteBookCommand implements Command {
    private BookDAO bookDAO;
    private int maSach;

    public DeleteBookCommand(BookDAO bookDAO, int maSach) {
        this.bookDAO = bookDAO;
        this.maSach = maSach;
    }

    @Override
    public void execute() {
        try {
            bookDAO.deleteBook(maSach);
        } catch (SQLException ex) {
            Logger.getLogger(DeleteBookCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void undo() {
        try {
            bookDAO.restoreBook(maSach);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


