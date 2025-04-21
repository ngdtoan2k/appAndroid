package com.example.myapplication;



import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class LoginActivity extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    Button btnLogin;
    DatabaseHelper dbHelper;

    public static int currentUserId = -1; // lưu ID người dùng sau khi đăng nhập


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        dbHelper = new DatabaseHelper(this);
        Button btnBack = findViewById(R.id.btnBack);

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
            startActivity(intent);
            finish();
        });

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString();
            String pass = edtPassword.getText().toString();

            if (kiemTraDangNhap(email, pass)) {
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                // Lấy thông tin người dùng từ cơ sở dữ liệu
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT tenNH, gioiTinh, soDienThoai FROM NguoiHoc WHERE email = ?", new String[]{email});

                String tenNguoiHoc = "";
                String gioiTinh = "Chưa rõ";  // Giá trị mặc định
                String soDienThoai = "Không có";  // Giá trị mặc định

                if (cursor.moveToFirst()) {
                    // Lấy thông tin từ cơ sở dữ liệu
                    int tenNHIndex = cursor.getColumnIndex("tenNH");
                    int gioiTinhIndex = cursor.getColumnIndex("gioiTinh");
                    int soDienThoaiIndex = cursor.getColumnIndex("soDienThoai");

                    if (tenNHIndex != -1) {
                        tenNguoiHoc = cursor.getString(tenNHIndex);  // Lấy tên người học
                    }

                    if (gioiTinhIndex != -1) {
                        gioiTinh = cursor.getString(gioiTinhIndex);  // Lấy giới tính
                    }

                    if (soDienThoaiIndex != -1) {
                        soDienThoai = cursor.getString(soDienThoaiIndex);  // Lấy số điện thoại
                    }
                }

                cursor.close();

                // Lưu thông tin vào SharedPreferences
                SharedPreferences pref = getSharedPreferences("MyApp", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("nguoiHocId", currentUserId);
                editor.putString("email", email);
                editor.putString("tenNguoiHoc", tenNguoiHoc);
                editor.putString("gioiTinh", gioiTinh);
                editor.putString("soDienThoai", soDienThoai);
                editor.apply();

                // Chuyển đến MainActivity2 sau khi đăng nhập thành công
                Intent i = new Intent(LoginActivity.this, MainActivity2.class);
                startActivity(i);
                finish(); // Đóng LoginActivity
            } else {
                Toast.makeText(LoginActivity.this, "Sai thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
            }
        });


    }

//    private boolean kiemTraDangNhap(String email, String password) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM NguoiHoc WHERE email=? AND password=?", new String[]{email, password});
//        if (cursor.moveToFirst()) {
//            currentUserId = cursor.getInt(0); // lưu lại id người dùng
//            cursor.close();
//            return true;
//        }
//        return false;
//    }
private boolean kiemTraDangNhap(String email, String password) {
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM NguoiHoc WHERE email=? AND password=?", new String[]{email, password});
    if (cursor.moveToFirst()) {
        currentUserId = cursor.getInt(0); // Lưu lại id người dùng
        cursor.close();
        return true;
    }
    cursor.close();
    return false;
}

}