package com.mycompany.qlthuvien;

public class Book {
    private int maSach;
    private String tenSach;
    private String tenTacGia;
    private Integer namNXB;
    private int trangThai;
    private String nXB;
    private byte[] hinh;
    private String moTaSach;
    private float giaSach;
    private String theLoai;
    public Book(int maSach1, String tenSach1, String tenTacGia1, int namNXB1, String nXB1, float giaSach1, String tenTheLoai, String moTaSach1) {
    }
    public Book(int maSach, String tenSach, String tenTacGia, Integer namNXB, int trangThai, String nXB, byte[] hinh, String moTaSach, float giaSach, String theLoai) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tenTacGia = tenTacGia;
        this.namNXB = namNXB;
        this.trangThai = trangThai;
        this.nXB = nXB;
        this.hinh = hinh;
        this.moTaSach = moTaSach;
        this.giaSach = giaSach;
        this.theLoai = theLoai;
    }
    public Book(int maSach, String tenSach, String tenTacGia, Integer namNXB, String nXB, String moTaSach, float giaSach, String theLoai) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tenTacGia = tenTacGia;
        this.namNXB = namNXB;
        this.nXB = nXB;
        this.moTaSach = moTaSach;
        this.giaSach = giaSach;
        this.theLoai = theLoai;
    }
    // Constructor không có mã sách
    public Book(String tenSach, String tenTacGia, Integer namNXB, int trangThai, String nXB, byte[] hinh, String moTaSach, float giaSach, String theLoai) {
        this.tenSach = tenSach;
        this.tenTacGia = tenTacGia;
        this.namNXB = namNXB;
        this.trangThai = trangThai;
        this.nXB = nXB;
        this.hinh = hinh;
        this.moTaSach = moTaSach;
        this.giaSach = giaSach;
        this.theLoai = theLoai;
    }

    // Các phương thức getter và setter
    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public Integer getNamNXB() {
        return namNXB;
    }

    public void setNamNXB(Integer namNXB) {
        this.namNXB = namNXB;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getNXB() {
        return nXB;
    }

    public void setNXB(String nXB) {
        this.nXB = nXB;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    public String getMoTaSach() {
        return moTaSach;
    }

    public void setMoTaSach(String moTaSach) {
        this.moTaSach = moTaSach;
    }

    public float getGiaSach() {
        return giaSach;
    }

    public void setGiaSach(float giaSach) {
        this.giaSach = giaSach;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    @Override
    public String toString() {
        return "Book{" +
                "maSach=" + maSach +
                ", tenSach='" + tenSach + '\'' +
                ", tenTacGia='" + tenTacGia + '\'' +
                ", namNXB=" + namNXB +
                ", trangThai=" + trangThai +
                ", nXB='" + nXB + '\'' +
                ", giaSach=" + giaSach +
                ", theLoai='" + theLoai + '\'' +
                '}';
    }
}
