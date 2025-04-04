/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien.model;

import MemberMemento.MemberMemento;
import java.util.Date;

/**
 *
 * @author minh9
 */
public class Member {

    private String maDocGia;
    private String hoTen;
    private Date ngaySinh;
    private String email;
    private Date ngayLapThe;
    private Date ngayHetHan;
    private String trangThai;
    private int gioiTinh;
    private byte[] hinh;

    public Member(String maDocGia, String hoTen, Date ngaySinh, String email, Date ngayLapThe, Date ngayHetHan, String trangThai, int gioiTinh, byte [] hinh) {
        this.maDocGia = maDocGia;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.ngayLapThe = ngayLapThe;
        this.ngayHetHan = ngayHetHan;
        this.trangThai = trangThai;
        this.gioiTinh = gioiTinh;
        this.hinh= hinh;
    }
    public Member (){}

    public String getMaDocGia() {
        return maDocGia;
    }

    public void setMaDocGia(String maDocGia) {
        this.maDocGia = maDocGia;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNgayLapThe() {
        return ngayLapThe;
    }

    public void setNgayLapThe(Date ngayLapThe) {
        this.ngayLapThe = ngayLapThe;
    }

    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    public byte[] getHinh() {
        return hinh;
    }
    
    
    
    public MemberMemento saveToMemento() {
        return new MemberMemento(maDocGia, hoTen, ngaySinh, email, ngayLapThe, ngayHetHan, trangThai, gioiTinh, hinh);
    }

    public void restoreFromMemento(MemberMemento memento) {
        this.maDocGia = memento.getMaDocGia();
        this.hoTen = memento.getHoTen();
        this.ngaySinh = memento.getNgaySinh();
        this.email = memento.getEmail();
        this.ngayLapThe = memento.getNgayLapThe();
        this.ngayHetHan = memento.getNgayHetHan();
        this.trangThai = memento.getTrangThai();
        this.gioiTinh = memento.getGioiTinh();
        this.hinh = memento.getHinh();
    }
}
