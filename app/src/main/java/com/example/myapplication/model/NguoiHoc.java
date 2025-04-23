package com.example.myapplication.model;



//public class NguoiHoc {
//    public int id;
//    public String tenNH;
//    public String email;
//
//    public NguoiHoc(int id, String tenNH, String email) {
//        this.id = id;
//        this.tenNH = tenNH;
//        this.email = email;
//    }
//}
public class NguoiHoc {
    private int id;
    private String tenNH;
    private String email;
    private String gioiTinh;
    private String soDienThoai;

    public NguoiHoc(int id, String tenNH, String email, String gioiTinh, String soDienThoai) {
        this.id = id;
        this.tenNH = tenNH;
        this.email = email;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
    }

    // Các getter
    public int getId() {
        return id;
    }

    public String getTenNH() {
        return tenNH;
    }

    public String getEmail() {
        return email;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    // (Tuỳ chọn) setter nếu bạn cần
}

