package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText edtName, edtEmail, edtPassword;
    Button btnRegister;
    DatabaseHelper dbHelper;
    EditText edtConfirmPassword, edtPhone;
    RadioGroup radioGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        dbHelper = new DatabaseHelper(this);

        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtPhone = findViewById(R.id.edtPhone);
        radioGender = findViewById(R.id.radioGender);


        Button btnBackToLogin = findViewById(R.id.btnBackToLogin);
        btnBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        btnRegister.setOnClickListener(v -> {
            String ten = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();
            String confirmPass = edtConfirmPassword.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();

            int selectedGenderId = radioGender.getCheckedRadioButtonId();
            String gender = (selectedGenderId == R.id.radioMale) ? "Nam" : "Nữ";

            if (ten.isEmpty() || email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(confirmPass)) {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            try {
//                db.execSQL("INSERT INTO NguoiHoc (tenNH, email, password) VALUES (?, ?, ?)",
//                        new Object[]{ten, email, pass});

                db.execSQL("INSERT INTO NguoiHoc (tenNH, email, password, gioiTinh, soDienThoai) VALUES (?, ?, ?, ?, ?)",
                        new Object[]{ten, email, pass, gender, phone});

                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                // Chuyển về trang Login
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Email đã được sử dụng!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
