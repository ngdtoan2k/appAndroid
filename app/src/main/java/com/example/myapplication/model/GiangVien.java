//package com.example.myapplication.model;
//
//public class GiangVien {
//    private int id;
//    private String tenGV, email, chuyenMon;
//
//    public GiangVien(int id, String tenGV, String email, String chuyenMon) {
//        this.id = id;
//        this.tenGV = tenGV;
//        this.email = email;
//        this.chuyenMon = chuyenMon;
//    }
//
//    public String getTenGV() { return tenGV; }
//    public String getEmail() { return email; }
//    public String getChuyenMon() { return chuyenMon; }
//}
package com.example.myapplication.model;

import java.util.List;

public class GiangVien {
    private int id;
    private String tenGV;
    private String email;
    private String chuyenMon;
    private String gioiTinh;
    private int thamNien;
    private String moTa;
    private List<String> danhSachMon;
    private int anhMinhHoaResId; // Drawable resource ID

    public GiangVien(int id, String tenGV, String email, String chuyenMon, String gioiTinh, int thamNien, String moTa, List<String> danhSachMon, int anhMinhHoaResId) {
        this.id = id;
        this.tenGV = tenGV;
        this.email = email;
        this.chuyenMon = chuyenMon;
        this.gioiTinh = gioiTinh;
        this.thamNien = thamNien;
        this.moTa = moTa;
        this.danhSachMon = danhSachMon;
        this.anhMinhHoaResId = anhMinhHoaResId;
    }

    public int getId() { return id; }
    public String getTenGV() { return tenGV; }
    public String getEmail() { return email; }
    public String getChuyenMon() { return chuyenMon; }
    public String getGioiTinh() { return gioiTinh; }
    public int getThamNien() { return thamNien; }
    public String getMoTa() { return moTa; }
    public List<String> getDanhSachMon() { return danhSachMon; }
    public int getAnhMinhHoaResId() { return anhMinhHoaResId; }
}
