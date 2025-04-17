package com.example.myapplication.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;

public class UserFragment extends Fragment {

    private TextView txtUserInfo;
    private Button btnLoginLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        txtUserInfo = view.findViewById(R.id.txtUserInfo);
//        btnLoginLogout = view.findViewById(R.id.btnLoginLogout);
//
//        SharedPreferences pref = getActivity().getSharedPreferences("MyApp", getContext().MODE_PRIVATE);
//        int nguoiHocId = pref.getInt("nguoiHocId", -1);
//
//        if (nguoiHocId == -1) {
//            // Chưa đăng nhập, hiển thị nút Đăng nhập
//            txtUserInfo.setText("Bạn chưa đăng nhập");
//            btnLoginLogout.setText("Đăng nhập");
//            btnLoginLogout.setOnClickListener(v -> {
//                // Chuyển đến màn hình đăng nhập
//                startActivity(new Intent(getContext(), LoginActivity.class));
//            });
//        } else {
//            // Đã đăng nhập, hiển thị thông tin người dùng
//            String tenNguoiHoc = pref.getString("tenNguoiHoc", "Người dùng");
//            String email = pref.getString("email", "Email không xác định");
//            txtUserInfo.setText("Tên: " + tenNguoiHoc + "\nEmail: " + email);
//
//            // Nút đăng xuất
//            btnLoginLogout.setText("Đăng xuất");
//            btnLoginLogout.setOnClickListener(v -> {
//                SharedPreferences.Editor editor = pref.edit();
//                editor.remove("nguoiHocId");
//                editor.remove("tenNguoiHoc");
//                editor.remove("email");
//                editor.apply();
//
//                // Quay lại LoginActivity
//                startActivity(new Intent(getContext(), LoginActivity.class));
//                getActivity().finish(); // Đóng MainActivity2
//            });
//        }
//    }
@Override
public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    txtUserInfo = view.findViewById(R.id.txtUserInfo);
    btnLoginLogout = view.findViewById(R.id.btnLoginLogout);

    SharedPreferences pref = getActivity().getSharedPreferences("MyApp", getContext().MODE_PRIVATE);
    int nguoiHocId = pref.getInt("nguoiHocId", -1);

    if (nguoiHocId == -1) {
        // Chưa đăng nhập, hiển thị nút Đăng nhập
        txtUserInfo.setText("Bạn chưa đăng nhập");
        btnLoginLogout.setText("Đăng nhập");
        btnLoginLogout.setOnClickListener(v -> {
            // Chuyển đến màn hình đăng nhập
            startActivity(new Intent(getContext(), LoginActivity.class));
        });
    } else {
        // Đã đăng nhập, hiển thị thông tin người dùng
        String tenNguoiHoc = pref.getString("tenNguoiHoc", "Người dùng");
        String email = pref.getString("email", "Email không xác định");
        txtUserInfo.setText("Tên: " + tenNguoiHoc + "\nEmail: " + email);

        // Nút đăng xuất
        btnLoginLogout.setText("Đăng xuất");
        btnLoginLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = pref.edit();
            editor.remove("nguoiHocId");
            editor.remove("tenNguoiHoc");
            editor.remove("email");
            editor.apply();

            // Quay lại LoginActivity
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish(); // Đóng MainActivity2
        });
    }
}

}
