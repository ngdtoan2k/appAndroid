package com.example.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity2 extends AppCompatActivity {
    BottomNavigationView bottomNav;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNav = findViewById(R.id.bottomNav);
        fab = findViewById(R.id.fabAdd);
        Button btnLogout = findViewById(R.id.btnLogout);


        // Mặc định mở fragment Khóa học
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new KhoaHocFragment()).commit();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                if (item.getItemId() == R.id.menu_khoahoc) {
                    selectedFragment = new KhoaHocFragment();
                } else if (item.getItemId() == R.id.menu_thongke) {
                    selectedFragment = new ThongKeFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("MyApp", MODE_PRIVATE);
                int nguoiHocId = pref.getInt("nguoiHocId", -1);

                if (nguoiHocId == -1) {
                    // Nếu người dùng chưa đăng nhập, chuyển tới LoginActivity
                    Intent loginIntent = new Intent(MainActivity2.this, LoginActivity.class);
                    startActivity(loginIntent); // Mở giao diện đăng nhập
                } else {
                    // Nếu đã đăng nhập, mở Dialog đăng ký khóa học
                    new DangKyDialog(MainActivity2.this).show();
                }
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("MyApp", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("nguoiHocId");
                editor.apply();

                Intent intent = new Intent(MainActivity2.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
