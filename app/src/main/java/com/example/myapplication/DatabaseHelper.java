package com.example.myapplication;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.model.GiangVien;
import com.example.myapplication.model.NguoiHoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "KhoaHocDB.db";
    public static final int DB_VERSION = 18;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String createKhoaHoc = "CREATE TABLE KhoaHoc (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten TEXT, " +
                "ngayBatDau TEXT, " +
                "ngayKetThuc TEXT, " +
                "kichHoat INTEGER, " +
                "giangVien TEXT, " +
                "moTa TEXT)";

        db.execSQL(createKhoaHoc);

        String createNguoiHoc = "CREATE TABLE NguoiHoc (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenNH TEXT, " +
                "email TEXT UNIQUE, " +
                "password TEXT, " +

                "gioiTinh TEXT, " +
                "soDienThoai TEXT)";
        db.execSQL(createNguoiHoc);

        String createDangKy = "CREATE TABLE DangKy (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nguoiHocId INTEGER, " +
                "khoaHocId INTEGER, " +
                "ngayDangKy TEXT)";
        db.execSQL(createDangKy);

        String createGiangVien = "CREATE TABLE GiangVien (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenGV TEXT, " +
                "email TEXT, " +
                "chuyenMon TEXT, "+
                "gioiTinh TEXT,"+
                "thamNien INTEGER,"+
               " moTa TEXT,"+
                "monGiangDay TEXT,"+
                "anh INTEGER)";
        db.execSQL(createGiangVien);


        db.execSQL("INSERT INTO GiangVien (tenGV, email, chuyenMon, gioiTinh, thamNien, moTa, monGiangDay, anh) VALUES " +
                "('Trần Thị B', 'c@gmail.com', 'Android', 'Nam', 10, 'Giảng viên Android', 'Android, Kotlin', 123)," +
                "('Nguyen Thi D', 'd@gmail.com', 'Java Web', 'Nữ', 8, 'Giảng viên Java Web', 'Java, Spring', 124)");

        // Dữ liệu mẫu
        db.execSQL("INSERT INTO NguoiHoc (tenNH, email, password, gioiTinh,soDienThoai ) VALUES " +
                "('Nguyen Van A', 'a@gmail.com', '123','Nam','0912345678')," +
                "('Le Thi B', 'b@gmail.com', '123','Nữ','0912345678')");

        db.execSQL("INSERT INTO KhoaHoc (ten, ngayBatDau, ngayKetThuc, kichHoat, giangVien, moTa) VALUES " +
                "('Android Cơ bản', '2025-05-01', '2025-05-30', 1, 'Nguyễn Văn A', 'Khóa học giới thiệu về Android cơ bản')," +
                "('Java Web', '2025-06-01', '2025-06-30', 0, 'Trần Thị B', 'Khóa học giới thiệu về lập trình Java Web'),"+
                "('C++', '2025-06-01', '2025-06-30', 0, 'Trần Thị B', 'Khóa học giới thiệu về lập trình C++')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DangKy");
        db.execSQL("DROP TABLE IF EXISTS KhoaHoc");
        db.execSQL("DROP TABLE IF EXISTS NguoiHoc");
        db.execSQL("DROP TABLE IF EXISTS GiangVien");

        onCreate(db);
    }
    public List<GiangVien> getAllGiangVien() {
        List<GiangVien> danhSach = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM GiangVien", null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("id");
                int tenGVIndex = cursor.getColumnIndex("tenGV");
                int emailIndex = cursor.getColumnIndex("email");
                int chuyenMonIndex = cursor.getColumnIndex("chuyenMon");

                int gioiTinhIndex = cursor.getColumnIndex("gioiTinh");
                int thamNienIndex = cursor.getColumnIndex("thamNien");
                int moTaIndex = cursor.getColumnIndex("moTa");
                int monGiangDayIndex = cursor.getColumnIndex("monGiangDay");
                int anhIndex = cursor.getColumnIndex("anh");


//                if (idIndex != -1 && tenGVIndex != -1 && emailIndex != -1 && chuyenMonIndex != -1) {

                if (idIndex != -1 && tenGVIndex != -1 && emailIndex != -1 && chuyenMonIndex != -1 &&
                        gioiTinhIndex != -1 && thamNienIndex != -1 && moTaIndex != -1 && monGiangDayIndex != -1 && anhIndex != -1) {

                    int id = cursor.getInt(idIndex);
                    String tenGV = cursor.getString(tenGVIndex);
                    String email = cursor.getString(emailIndex);
                    String chuyenMon = cursor.getString(chuyenMonIndex);

                    String gioiTinh = cursor.getString(gioiTinhIndex);
                    int thamNien = cursor.getInt(thamNienIndex);
                    String moTa = cursor.getString(moTaIndex);
                    String monGiangDayStr = cursor.getString(monGiangDayIndex);
                    int anh = cursor.getInt(anhIndex);


                List<String> monGiangDay = Arrays.asList(monGiangDayStr.split(","));

                // Tạo đối tượng GiangVien với đầy đủ thông tin
                GiangVien gv = new GiangVien(id, tenGV, email, chuyenMon, gioiTinh, thamNien, moTa, monGiangDay, anh);
                    danhSach.add(gv);
                } else {
                    // Handle missing column case (you could log it or throw an exception)
                    Log.e("DatabaseHelper", "One or more columns are missing in GiangVien table.");
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return danhSach;
    }

    public List<GiangVien> getGiangVienByName(String name) {
        List<GiangVien> danhSach = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM GiangVien WHERE tenGV LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + name + "%"});

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("id");
                int tenGVIndex = cursor.getColumnIndex("tenGV");
                int emailIndex = cursor.getColumnIndex("email");
                int chuyenMonIndex = cursor.getColumnIndex("chuyenMon");

                int gioiTinhIndex = cursor.getColumnIndex("gioiTinh");
                int thamNienIndex = cursor.getColumnIndex("thamNien");
                int moTaIndex = cursor.getColumnIndex("moTa");
                int monGiangDayIndex = cursor.getColumnIndex("monGiangDay");
                int anhIndex = cursor.getColumnIndex("anh");

                // Check if any column index is -1 (which indicates the column doesn't exist)
//                if (idIndex != -1 && tenGVIndex != -1 && emailIndex != -1 && chuyenMonIndex != -1) {

                if (idIndex != -1 && tenGVIndex != -1 && emailIndex != -1 && chuyenMonIndex != -1 &&
                        gioiTinhIndex != -1 && thamNienIndex != -1 && moTaIndex != -1 && monGiangDayIndex != -1 && anhIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String tenGV = cursor.getString(tenGVIndex);
                    String email = cursor.getString(emailIndex);
                    String chuyenMon = cursor.getString(chuyenMonIndex);

                    String gioiTinh = cursor.getString(gioiTinhIndex);
                    int thamNien = cursor.getInt(thamNienIndex);
                    String moTa = cursor.getString(moTaIndex);
                    String monGiangDayStr = cursor.getString(monGiangDayIndex);
                    int anh = cursor.getInt(anhIndex);

//                    GiangVien gv = new GiangVien(id, tenGV, email, chuyenMon);
//                    danhSach.add(gv);

                    List<String> monGiangDay = Arrays.asList(monGiangDayStr.split(","));

                    // Tạo đối tượng GiangVien với đầy đủ thông tin
                    GiangVien gv = new GiangVien(id, tenGV, email, chuyenMon, gioiTinh, thamNien, moTa, monGiangDay, anh);
                    danhSach.add(gv);
                } else {
                    // Handle missing column case (you could log it or throw an exception)
                    Log.e("DatabaseHelper", "One or more columns are missing in GiangVien table.");
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return danhSach;
    }


    public GiangVien layGiangVienTheoTen(String ten) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM GiangVien WHERE tenGV = ?", new String[]{ten});
        GiangVien gv = null;
        if (cursor.moveToFirst()) {
            // Lấy chuỗi danh sách môn, ví dụ: "Java,C++,Android"
            String danhSachMonStr = cursor.getString(7); // Cột danh sách môn học là cột thứ 8 (index 7)
            List<String> danhSachMon = new ArrayList<>();
            if (danhSachMonStr != null && !danhSachMonStr.isEmpty()) {
                danhSachMon = Arrays.asList(danhSachMonStr.split(","));
            }

            gv = new GiangVien(
                    cursor.getInt(0),         // id
                    cursor.getString(1),      // tenGV
                    cursor.getString(2),      // email
                    cursor.getString(3),      // chuyenMon
                    cursor.getString(4),      // gioiTinh
                    cursor.getInt(5),         // thamNien
                    cursor.getString(6),      // moTa
                    danhSachMon,              // danh sách môn học
                    cursor.getInt(8)          // anh minh họa (resource id)
            );
        }
        cursor.close();
        return gv;
    }
    public boolean updateNguoiHoc(NguoiHoc nh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenNH", nh.getTenNH());
        values.put("email", nh.getEmail());
        values.put("gioiTinh", nh.getGioiTinh());
        values.put("soDienThoai", nh.getSoDienThoai());

        int rows = db.update("NguoiHoc", values, "id = ?", new String[]{String.valueOf(nh.getId())});
        db.close();
        return rows > 0;
    }

}
