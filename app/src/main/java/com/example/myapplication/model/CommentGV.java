package com.example.myapplication.model;

public class CommentGV {
    private int id;
    private int giangVienId;
    private int nguoiHocId;
    private String noiDung;
    private String thoiGian;
    private String tenNguoiBinhLuan;

    public CommentGV(int id, int giangVienId, int nguoiHocId, String noiDung, String thoiGian, String tenNguoiBinhLuan) {
        this.id = id;
        this.giangVienId = giangVienId;
        this.nguoiHocId = nguoiHocId;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.tenNguoiBinhLuan = tenNguoiBinhLuan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGiangVienId() {
        return giangVienId;
    }

    public void setGiangVienId(int giangVienId) {
        this.giangVienId = giangVienId;
    }

    public int getNguoiHocId() {
        return nguoiHocId;
    }

    public void setNguoiHocId(int nguoiHocId) {
        this.nguoiHocId = nguoiHocId;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTenNguoiBinhLuan() {
        return tenNguoiBinhLuan;
    }

    public void setTenNguoiBinhLuan(String tenNguoiBinhLuan) {
        this.tenNguoiBinhLuan = tenNguoiBinhLuan;
    }
}
