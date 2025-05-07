package com.example.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.fragments.GiangVienFragment;
import com.example.myapplication.fragments.KhoaHocFragment;
import com.example.myapplication.fragments.ThongKeFragment;
import com.example.myapplication.fragments.ThongKeTabFragment;
import com.example.myapplication.fragments.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

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



        // Mặc định mở fragment Khóa học
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new KhoaHocFragment()).commit();


        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                if (item.getItemId() == R.id.menu_khoahoc) {
                    selectedFragment = new KhoaHocFragment();
                } else if (item.getItemId() == R.id.menu_thongke) {
//                    selectedFragment = new ThongKeFragment();
//                    selectedFragment = new ThongKeTabFragment(); // <-- sửa tại đây
                    SharedPreferences pref = getSharedPreferences("MyApp", MODE_PRIVATE);
                    String emailDangNhap = pref.getString("email", "");

                    ThongKeTabFragment thongKeTabFragment = new ThongKeTabFragment();
                    thongKeTabFragment.setEmailNguoiDungHienTai(emailDangNhap);  // <-- THÊM DÒNG NÀY

                    selectedFragment = thongKeTabFragment;
                } else if (item.getItemId() == R.id.menu_user) {  // Mới thêm mục người dùng
                    selectedFragment = new UserFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });


//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences pref = getSharedPreferences("MyApp", MODE_PRIVATE);
//                int nguoiHocId = pref.getInt("nguoiHocId", -1);
//
//                if (nguoiHocId == -1) {
//                    // Nếu người dùng chưa đăng nhập, chuyển tới LoginActivity
//                    Intent loginIntent = new Intent(MainActivity2.this, LoginActivity.class);
//                    startActivity(loginIntent); // Mở giao diện đăng nhập
//                } else {
//                    // Nếu đã đăng nhập, mở Dialog đăng ký khóa học
//                    DangKyDialog dialog = new DangKyDialog(MainActivity2.this);
//
//                    dialog.setOnDangKySuccessListener(new DangKyDialog.OnDangKySuccessListener() {
//                        @Override
//                        public void onDangKySuccess() {
//                            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//                            if (currentFragment instanceof ThongKeTabFragment) {
//                                ThongKeTabFragment thongKeTabFragment = (ThongKeTabFragment) currentFragment;
//                                ViewPager2 viewPager = thongKeTabFragment.getViewPager();
//                                int currentItem = viewPager.getCurrentItem();
//                                Fragment tabFragment = thongKeTabFragment.getFragmentAt(currentItem);
//
//                                if (tabFragment instanceof ThongKeFragment) {
//                                    ((ThongKeFragment) tabFragment).loadData(); // Cập nhật lại dữ liệu
//                                } else if (tabFragment instanceof GiangVienFragment) {
//                                    ((GiangVienFragment) tabFragment).loadData();
//                                }
//                            }
//                        }
//                    });
//
//                    dialog.show();
//                }

//                SharedPreferences pref = getSharedPreferences("MyApp", MODE_PRIVATE);
//                int nguoiHocId = pref.getInt("nguoiHocId", -1);
//
//                if (nguoiHocId == -1) {
//                    // Nếu người dùng chưa đăng nhập, chuyển tới LoginActivity
//                    Intent loginIntent = new Intent(MainActivity2.this, LoginActivity.class);
//                    startActivity(loginIntent); // Mở giao diện đăng nhập
//                } else {
//                    // Nếu đã đăng nhập, mở Dialog đăng ký khóa học
////                    new DangKyDialog(MainActivity2.this).show();
//
//
//                    DangKyDialog dialog = new DangKyDialog(MainActivity2.this);
//
////                    dialog.setOnDangKySuccessListener(new DangKyDialog.OnDangKySuccessListener() {
////                        @Override
////                        public void onDangKySuccess() {
////                            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
////
////                            if (currentFragment instanceof ThongKeTabFragment) {
////                                ThongKeTabFragment thongKeTabFragment = (ThongKeTabFragment) currentFragment;
////                                ViewPager2 viewPager = thongKeTabFragment.getViewPager();
////                                int currentItem = viewPager.getCurrentItem();
////                                Fragment tabFragment = thongKeTabFragment.getFragmentAt(currentItem);
////
////                                if (tabFragment instanceof ThongKeFragment) {
////                                    // Gọi phương thức để load lại dữ liệu trong ThongKeFragment
////                                    ((ThongKeFragment) tabFragment).loadData(); // Cập nhật lại dữ liệu
////                                } else if (tabFragment instanceof GiangVienFragment) {
////                                    ((GiangVienFragment) tabFragment).loadData();
////                                }
////                            }
////                        }
////                    });
//                    dialog.setOnDangKySuccessListener(new DangKyDialog.OnDangKySuccessListener() {
//                        @Override
//                        public void onDangKySuccess() {
//                            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//                            if (currentFragment instanceof ThongKeTabFragment) {
//                                ThongKeTabFragment thongKeTabFragment = (ThongKeTabFragment) currentFragment;
//                                ViewPager2 viewPager = thongKeTabFragment.getViewPager();
//                                int currentItem = viewPager.getCurrentItem();
//                                Fragment tabFragment = thongKeTabFragment.getFragmentAt(currentItem);
//
//                                if (tabFragment instanceof ThongKeFragment) {
//                                    ((ThongKeFragment) tabFragment).loadData(); // Cập nhật lại dữ liệu
//                                } else if (tabFragment instanceof GiangVienFragment) {
//                                    ((GiangVienFragment) tabFragment).loadData();
//                                }
//                            }
//                        }
//                    });
//
//                    dialog.show();
//
//                }
//            }
//        });
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
                    DangKyDialog dialog = new DangKyDialog(MainActivity2.this);
                    dialog.setOnDangKySuccessListener(new DangKyDialog.OnDangKySuccessListener() {
                        @Override
                        public void onDangKySuccess() {
                            // Cập nhật dữ liệu khi đăng ký thành công
                            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                            if (currentFragment instanceof ThongKeTabFragment) {
                                ThongKeTabFragment thongKeTabFragment = (ThongKeTabFragment) currentFragment;
                                ViewPager2 viewPager = thongKeTabFragment.getViewPager();
                                int currentItem = viewPager.getCurrentItem();
                                Fragment tabFragment = thongKeTabFragment.getFragmentAt(currentItem);

                                if (tabFragment instanceof ThongKeFragment) {
                                    ((ThongKeFragment) tabFragment).loadData(); // Cập nhật lại dữ liệu
                                } else if (tabFragment instanceof GiangVienFragment) {
                                    ((GiangVienFragment) tabFragment).loadData();
                                }
                            }
                        }
                    });
                    dialog.show();
                }
            }
        });


        // Xử lý kéo FAB
        fab.setOnTouchListener(new View.OnTouchListener() {
            float dX, dY;
            int lastAction;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        lastAction = MotionEvent.ACTION_DOWN;
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        view.setX(event.getRawX() + dX);
                        view.setY(event.getRawY() + dY);
                        lastAction = MotionEvent.ACTION_MOVE;
                        return true;

                    case MotionEvent.ACTION_UP:
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            view.performClick();
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

}
