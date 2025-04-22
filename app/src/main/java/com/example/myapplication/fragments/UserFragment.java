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



@Override
public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    txtUserInfo = view.findViewById(R.id.txtUserInfo);
    btnLoginLogout = view.findViewById(R.id.btnLoginLogout);

    SharedPreferences pref = getActivity().getSharedPreferences("MyApp", getContext().MODE_PRIVATE);
    int nguoiHocId = pref.getInt("nguoiHocId", -1);

    if (nguoiHocId == -1) {
        txtUserInfo.setText("Bạn chưa đăng nhập");
        btnLoginLogout.setText("Đăng nhập");
        btnLoginLogout.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LoginActivity.class));
        });
    } else {
        // Đọc thông tin đầy đủ từ SharedPreferences
        String tenNguoiHoc = pref.getString("tenNguoiHoc", "Người dùng");
        String email = pref.getString("email", "Email không xác định");
        String gioiTinh = pref.getString("gioiTinh", "Chưa rõ");
        String soDienThoai = pref.getString("soDienThoai", "Không có");

        // Hiển thị thông tin chi tiết
        String info = "Tên: " + tenNguoiHoc +
                "\nEmail: " + email +
                "\nGiới tính: " + gioiTinh +
                "\nSĐT: " + soDienThoai;

        txtUserInfo.setText(info);

        btnLoginLogout.setText("Đăng xuất");
        btnLoginLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = pref.edit();
            editor.clear(); // Xóa toàn bộ thông tin
            editor.apply();

            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        });
    }
}

}
