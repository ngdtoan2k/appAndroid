package com.example.myapplication;



import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

                // Lưu thông tin người dùng vào SharedPreferences
                SharedPreferences pref = getSharedPreferences("MyApp", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("nguoiHocId", currentUserId);  // Lưu ID người học
                editor.putString("email", email);  // Lưu Email người học

                // Lấy tên người học từ database

                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT tenNH FROM NguoiHoc WHERE email = ?", new String[]{email});

                if (cursor.moveToFirst()) {
                    // Kiểm tra xem cột "tenNH" có tồn tại không
                    int tenNHIndex = cursor.getColumnIndex("tenNH");

                    if (tenNHIndex != -1) {
                        String tenNguoiHoc = cursor.getString(tenNHIndex);  // Lấy tên người học
                        editor.putString("tenNguoiHoc", tenNguoiHoc);  // Lưu tên người học
                    } else {
                        // Cột "tenNH" không tồn tại
                        Toast.makeText(this, "Không tìm thấy tên người học!", Toast.LENGTH_SHORT).show();
                    }
                }

                cursor.close();
                editor.apply();

                // Quay lại MainActivity2 sau khi đăng nhập thành công
                Intent i = new Intent(LoginActivity.this, MainActivity2.class);
                startActivity(i);
                finish(); // Đóng LoginActivity
            } else {
                Toast.makeText(LoginActivity.this, "Sai thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
            }
        });
    }

            private boolean kiemTraDangNhap (String email, String password){
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM NguoiHoc WHERE email=? AND password=?", new String[]{email, password});
            if (cursor.moveToFirst()) {
                currentUserId = cursor.getInt(0); // lưu lại id người dùng
                cursor.close();
                return true;
            }
            return false;
        }



}

//public class LoginActivity extends AppCompatActivity {
//    EditText edtEmail, edtPassword;
//    Button btnLogin;
//    DatabaseHelper dbHelper;
//
//    public static int currentUserId = -1; // lưu ID người dùng sau khi đăng nhập
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        edtEmail = findViewById(R.id.edtEmail);
//        edtPassword = findViewById(R.id.edtPassword);
//        btnLogin = findViewById(R.id.btnLogin);
//        dbHelper = new DatabaseHelper(this);
//        Button btnBack = findViewById(R.id.btnBack);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = edtEmail.getText().toString();
//                String pass = edtPassword.getText().toString();
//
//                if (kiemTraDangNhap(email, pass)) {
//                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                    // Lưu thông tin người dùng vào SharedPreferences
//                    SharedPreferences pref = getSharedPreferences("MyApp", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putInt("nguoiHocId", currentUserId);
//                    editor.apply();
                    // Sau khi xác thực đăng nhập thành công:
//                    SharedPreferences pref = getSharedPreferences("MyApp", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putInt("nguoiHocId", nguoiHocId);  // ID người học
//                    editor.putString("tenNguoiHoc", tenNguoiHoc);  // Tên người học
//                    editor.putString("email", email);  // Email người học
//                    editor.apply();


                    // Quay lại MainActivity2 sau khi đăng nhập thành công
//                    Intent i = new Intent(LoginActivity.this, MainActivity2.class);
//                    startActivity(i);
//                    finish(); // Đóng LoginActivity
//                } else {
//                    Toast.makeText(LoginActivity.this, "Sai thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//        private boolean kiemTraDangNhap (String email, String password){
//            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            Cursor cursor = db.rawQuery("SELECT * FROM NguoiHoc WHERE email=? AND password=?", new String[]{email, password});
//            if (cursor.moveToFirst()) {
//                currentUserId = cursor.getInt(0); // lưu lại id người dùng
//                cursor.close();
//                return true;
//            }
//            return false;
//        }
//
//
//    }

