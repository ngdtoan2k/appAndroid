package com.example.myapplication;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.model.CommentGV;
import com.example.myapplication.model.GiangVien;
import com.example.myapplication.model.NguoiHoc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "KhoaHocDB.db";
    public static final int DB_VERSION = 23;

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

        String createCommentGV = "CREATE TABLE CommentGV (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "giangVienId INTEGER, " +
                "nguoiHocId INTEGER, " +
                "tenNguoiBinhLuan TEXT, " +
                "noiDung TEXT, " +
                "thoiGian TEXT, " +
                "FOREIGN KEY (giangVienId) REFERENCES GiangVien(id), " +
                "FOREIGN KEY (nguoiHocId) REFERENCES NguoiHoc(id))";
        db.execSQL(createCommentGV);


        db.execSQL("INSERT INTO GiangVien (tenGV, email, chuyenMon, gioiTinh, thamNien, moTa, monGiangDay, anh) VALUES " +
//                "('Trần Thị B', 'c@gmail.com', 'Android', 'Nam', 10, 'Giảng viên Android', 'Android, Kotlin', 123)," +
//                "('Nguyen Thi D', 'd@gmail.com', 'Java Web', 'Nữ', 8, 'Giảng viên Java Web', 'Java, Spring', 124)");
                "('Nguyễn Văn A', 'a@gmail.com', 'Android', 'Nam', 12, 'Giảng viên chuyên về Android', 'Android, Java', 101)," +
                "('Trần Thị B', 'b@gmail.com', 'Web', 'Nữ', 9, 'Giảng viên phát triển web', 'HTML, CSS, JavaScript', 102)," +
                "('Lê Minh C', 'c@gmail.com', 'Java', 'Nam', 15, 'Giảng viên Java kỳ cựu', 'Java Core, JavaFX', 103)," +
                "('Phạm Thị D', 'd@gmail.com', 'Kotlin', 'Nữ', 6, 'Giảng viên Kotlin cho Android', 'Kotlin, Android Jetpack', 104)," +
                "('Hoàng Văn E', 'e@gmail.com', 'PHP', 'Nam', 7, 'Giảng viên backend PHP', 'PHP, Laravel', 105)," +
                "('Đỗ Thị F', 'f@gmail.com', 'Python', 'Nữ', 10, 'Giảng viên Machine Learning', 'Python, TensorFlow', 106)," +
                "('Vũ Văn G', 'g@gmail.com', 'C#', 'Nam', 8, 'Giảng viên lập trình C#', 'C#, .NET', 107)," +
                "('Ngô Thị H', 'h@gmail.com', 'Mobile', 'Nữ', 5, 'Giảng viên phát triển di động', 'Flutter, Dart', 108)," +
                "('Bùi Văn I', 'i@gmail.com', 'Database', 'Nam', 11, 'Giảng viên hệ quản trị cơ sở dữ liệu', 'MySQL, SQL Server', 109)," +
                "('Đặng Thị K', 'k@gmail.com', 'AI', 'Nữ', 13, 'Giảng viên trí tuệ nhân tạo', 'AI, Deep Learning', 110)");

        // Dữ liệu mẫu
        db.execSQL("INSERT INTO NguoiHoc (tenNH, email, password, gioiTinh,soDienThoai ) VALUES " +
                "('Nguoi hoc A', 'a@gmail.com', '123','Nam','0912345678')," +
                "('Nguoi hoc B', 'b@gmail.com', '123','Nữ','0912345678')");

        db.execSQL("INSERT INTO KhoaHoc (ten, ngayBatDau, ngayKetThuc, kichHoat, giangVien, moTa) VALUES " +
//                "('Android Cơ bản', '2025-05-01', '2025-05-30', 1, 'Nguyen Thi D', 'Khóa học giới thiệu về Android cơ bản')," +
//                "('Java Web', '2025-06-01', '2025-06-30', 0, 'Trần Thị B', 'Khóa học giới thiệu về lập trình Java Web'),"+
//                "('C++', '2025-06-01', '2025-06-30', 0, 'Trần Thị B', 'Khóa học giới thiệu về lập trình C++')");
                "('Android Cơ bản', '2025-05-01', '2025-05-30', 1, 'Nguyễn Văn A', 'Khóa học giới thiệu về Android cơ bản')," +
                "('Web Frontend', '2025-06-01', '2025-06-30', 1, 'Trần Thị B', 'Học HTML, CSS, JavaScript cơ bản')," +
                "('Java nâng cao', '2025-07-01', '2025-07-30', 1, 'Lê Minh C', 'Khóa học Java nâng cao')," +
                "('Lập trình Kotlin', '2025-08-01', '2025-08-30', 0, 'Phạm Thị D', 'Kotlin trong phát triển Android hiện đại')," +
                "('PHP Laravel', '2025-09-01', '2025-09-30', 1, 'Hoàng Văn E', 'Khóa học lập trình web với Laravel')," +
                "('Python ML', '2025-10-01', '2025-10-30', 0, 'Đỗ Thị F', 'Machine Learning với Python và TensorFlow')," +
                "('Lập trình C#', '2025-11-01', '2025-11-30', 1, 'Vũ Văn G', 'Học C# và lập trình Windows Forms')," +
                "('Flutter cơ bản', '2025-12-01', '2025-12-30', 0, 'Ngô Thị H', 'Khóa học Flutter từ cơ bản đến nâng cao')," +
                "('Quản trị CSDL', '2026-01-01', '2026-01-30', 1, 'Bùi Văn I', 'Học quản lý cơ sở dữ liệu với MySQL')," +
                "('AI và Deep Learning', '2026-02-01', '2026-02-28', 1, 'Đặng Thị K', 'Khóa học chuyên sâu về AI và DL')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DangKy");
        db.execSQL("DROP TABLE IF EXISTS KhoaHoc");
        db.execSQL("DROP TABLE IF EXISTS NguoiHoc");
        db.execSQL("DROP TABLE IF EXISTS GiangVien");
        db.execSQL("DROP TABLE IF EXISTS CommentGV");
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


public void themBinhLuan(int giangVienId, int nguoiHocId, String noiDung, String tenNguoiHoc) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("giangVienId", giangVienId);
    values.put("nguoiHocId", nguoiHocId);
    values.put("noiDung", noiDung);
    values.put("tenNguoiBinhLuan", tenNguoiHoc);
    values.put("thoiGian", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

    long result = db.insert("CommentGV", null, values);
    if (result == -1) {
        Log.e("Database", "Lỗi khi thêm bình luận.");
    } else {
        Log.d("Database", "Bình luận đã được thêm thành công.");
    }
    db.close();
}

    public void xoaBinhLuan(int id, int nguoiHocId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Chỉ xoá nếu đúng người viết
        db.delete("CommentGV", "id = ? AND nguoiHocId = ?", new String[]{String.valueOf(id), String.valueOf(nguoiHocId)});
        db.close();
    }


public List<CommentGV> layBinhLuanTheoGiangVien(int giangVienId) {
    List<CommentGV> ds = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM CommentGV WHERE giangVienId = ? ORDER BY id DESC", new String[]{String.valueOf(giangVienId)});

    if (cursor.moveToFirst()) {
        do {
            // Kiểm tra sự tồn tại của các cột trước khi lấy giá trị
            int idColumnIndex = cursor.getColumnIndex("id");
            int nguoiHocIdColumnIndex = cursor.getColumnIndex("nguoiHocId");
            int tenNguoiBinhLuanColumnIndex = cursor.getColumnIndex("tenNguoiBinhLuan");
            int noiDungColumnIndex = cursor.getColumnIndex("noiDung");
            int thoiGianColumnIndex = cursor.getColumnIndex("thoiGian");

            // Kiểm tra nếu cột không tồn tại
            if (idColumnIndex == -1 || nguoiHocIdColumnIndex == -1 || tenNguoiBinhLuanColumnIndex == -1 || noiDungColumnIndex == -1 || thoiGianColumnIndex == -1) {
                Log.e("DatabaseError", "Một hoặc nhiều cột không tồn tại trong bảng CommentGV.");
                return ds;  // Nếu cột không tồn tại, trả về danh sách rỗng
            }

            // Lấy dữ liệu từ cursor
            int id = cursor.getInt(idColumnIndex);
            int nguoiHocId = cursor.getInt(nguoiHocIdColumnIndex);
            String tenNguoiBinhLuan = cursor.getString(tenNguoiBinhLuanColumnIndex);
            String noiDung = cursor.getString(noiDungColumnIndex);
            String thoiGian = cursor.getString(thoiGianColumnIndex);

            // Tạo đối tượng CommentGV và thêm vào danh sách
            CommentGV comment = new CommentGV(id, giangVienId, nguoiHocId, tenNguoiBinhLuan, noiDung, thoiGian);
            ds.add(comment);
        } while (cursor.moveToNext());
    }
    cursor.close();
    db.close();
    return ds;
}





}
