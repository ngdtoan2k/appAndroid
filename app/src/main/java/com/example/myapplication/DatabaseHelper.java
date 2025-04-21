package com.example.myapplication;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.model.GiangVien;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "KhoaHocDB.db";
    public static final int DB_VERSION = 8;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String createKhoaHoc = "CREATE TABLE KhoaHoc (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "ten TEXT, " +
//                "ngayBatDau TEXT, " +
//                "ngayKetThuc TEXT, " +
//                "kichHoat INTEGER)";

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
                "password TEXT)";
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
                "chuyenMon TEXT)";
        db.execSQL(createGiangVien);

// Dữ liệu mẫu
        db.execSQL("INSERT INTO GiangVien (tenGV, email, chuyenMon) VALUES " +
                "('Tran Van C', 'c@gmail.com', 'Android')," +
                "('Nguyen Thi D', 'd@gmail.com', 'Java Web')");

        // Dữ liệu mẫu
        db.execSQL("INSERT INTO NguoiHoc (tenNH, email, password) VALUES " +
                "('Nguyen Van A', 'a@gmail.com', '123')," +
                "('Le Thi B', 'b@gmail.com', '123')");
//        db.execSQL("INSERT INTO KhoaHoc (ten, ngayBatDau, ngayKetThuc, kichHoat) VALUES " +
//                "('Android Cơ bản', '2025-05-01', '2025-05-30', 1)," +
//                "('Java Web', '2025-06-01', '2025-06-30', 0)");
        db.execSQL("INSERT INTO KhoaHoc (ten, ngayBatDau, ngayKetThuc, kichHoat, giangVien, moTa) VALUES " +
                "('Android Cơ bản', '2025-05-01', '2025-05-30', 1, 'Nguyễn Văn A', 'Khóa học giới thiệu về Android cơ bản')," +
                "('Java Web', '2025-06-01', '2025-06-30', 0, 'Trần Thị B', 'Khóa học giới thiệu về lập trình Java Web')");

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

                // Check if any column index is -1 (which indicates the column doesn't exist)
                if (idIndex != -1 && tenGVIndex != -1 && emailIndex != -1 && chuyenMonIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String tenGV = cursor.getString(tenGVIndex);
                    String email = cursor.getString(emailIndex);
                    String chuyenMon = cursor.getString(chuyenMonIndex);

                    GiangVien gv = new GiangVien(id, tenGV, email, chuyenMon);
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

                // Check if any column index is -1 (which indicates the column doesn't exist)
                if (idIndex != -1 && tenGVIndex != -1 && emailIndex != -1 && chuyenMonIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String tenGV = cursor.getString(tenGVIndex);
                    String email = cursor.getString(emailIndex);
                    String chuyenMon = cursor.getString(chuyenMonIndex);

                    GiangVien gv = new GiangVien(id, tenGV, email, chuyenMon);
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

}
