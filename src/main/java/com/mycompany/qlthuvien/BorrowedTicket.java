/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien;

/**
 *
 * @author luong
 */
import  java.util.Date;

public class BorrowedTicket {
    private int maPM;
    private Date ngayMuon;
    private Date ngayTraDuKien;
    private Date ngayTraThucTe;
    private double tienPhat;
    private int maDocGia;
    private int trangThai;
    public BorrowedTicket(){}
    public BorrowedTicket(int maPM, Date ngayMuon, Date ngayTraDuKien, Date ngayTraThucTe, double tienPhat, int maDocGia, int trangThai) {
        this.maPM = maPM;
        this.ngayMuon = ngayMuon;
        this.ngayTraDuKien = ngayTraDuKien;
        this.ngayTraThucTe = ngayTraThucTe;
        this.tienPhat = tienPhat;
        this.maDocGia = maDocGia;
        this.trangThai = trangThai;
    }

    public int getMaPM() { return maPM; }
    public void setMaPM(int maPM) { this.maPM = maPM; }
    public Date getNgayMuon() { return ngayMuon; }
    public void setNgayMuon(Date ngayMuon) { this.ngayMuon = ngayMuon; }
    public Date getNgayTraDuKien() { return ngayTraDuKien; }
    public void setNgayTraDuKien(Date ngayTraDuKien) { this.ngayTraDuKien = ngayTraDuKien; }
    public Date getNgayTraThucTe() { return ngayTraThucTe; }
    public void setNgayTraThucTe(Date ngayTraThucTe) { this.ngayTraThucTe = ngayTraThucTe; }
    public double getTienPhat() { return tienPhat; }
    public void setTienPhat(double tienPhat) { this.tienPhat = tienPhat; }
    public int getMaDocGia() { return maDocGia; }
    public void setMaDocGia(int maDocGia) { this.maDocGia = maDocGia; }
    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
}