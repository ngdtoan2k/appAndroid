package com.example.myapplication.model;

public class GiangVien {
    private int id;
    private String tenGV, email, chuyenMon;

    public GiangVien(int id, String tenGV, String email, String chuyenMon) {
        this.id = id;
        this.tenGV = tenGV;
        this.email = email;
        this.chuyenMon = chuyenMon;
    }

    public String getTenGV() { return tenGV; }
    public String getEmail() { return email; }
    public String getChuyenMon() { return chuyenMon; }
}
