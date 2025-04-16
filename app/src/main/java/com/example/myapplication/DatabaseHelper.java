package com.example.myapplication;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "KhoaHocDB.db";
    public static final int DB_VERSION = 4;

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
                "kichHoat INTEGER)";
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

        // Dữ liệu mẫu
        db.execSQL("INSERT INTO NguoiHoc (tenNH, email, password) VALUES " +
                "('Nguyen Van A', 'a@gmail.com', '123')," +
                "('Le Thi B', 'b@gmail.com', '123')");
        db.execSQL("INSERT INTO KhoaHoc (ten, ngayBatDau, ngayKetThuc, kichHoat) VALUES " +
                "('Android Cơ bản', '2025-05-01', '2025-05-30', 1)," +
                "('Java Web', '2025-06-01', '2025-06-30', 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DangKy");
        db.execSQL("DROP TABLE IF EXISTS KhoaHoc");
        db.execSQL("DROP TABLE IF EXISTS NguoiHoc");
        onCreate(db);
    }
}
