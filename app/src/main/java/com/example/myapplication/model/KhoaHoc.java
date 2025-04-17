package com.example.myapplication.model;



public class KhoaHoc {
    public int id;
    public String ten;
    public String ngayBD;
    public String ngayKT;
    public boolean kichHoat;

    public KhoaHoc(int id, String ten, String ngayBD, String ngayKT, boolean kichHoat) {
        this.id = id;
        this.ten = ten;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.kichHoat = kichHoat;
    }
}

