package com.example.myapplication.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.NguoiHoc;

public class UserFragment extends Fragment {

    private TextView txtTenNguoiDung, txtEmailNguoiDung, txtGioiTinhNguoiDung, txtSdtNguoiDung , btnEditInfo;
    private Button btnLoginLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtTenNguoiDung = view.findViewById(R.id.txtTenNguoiDung);
        txtEmailNguoiDung = view.findViewById(R.id.txtEmailNguoiDung);
        txtGioiTinhNguoiDung = view.findViewById(R.id.txtGioiTinhNguoiDung);
        txtSdtNguoiDung = view.findViewById(R.id.txtSdtNguoiDung);

        btnLoginLogout = view.findViewById(R.id.btnLoginLogout);
        btnEditInfo = view.findViewById(R.id.btnEditInfo);

        SharedPreferences pref = getActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        int nguoiHocId = pref.getInt("nguoiHocId", -1);

        if (nguoiHocId == -1) {
            txtTenNguoiDung.setText("Tên: Bạn chưa đăng nhập");
            txtEmailNguoiDung.setText("Email: -");
            txtGioiTinhNguoiDung.setText("Giới tính: -");
            txtSdtNguoiDung.setText("SĐT: -");

            btnLoginLogout.setText("Đăng nhập");
            btnLoginLogout.setOnClickListener(v -> {
                startActivity(new Intent(getContext(), LoginActivity.class));
            });
        } else {
            // Lấy dữ liệu
            String tenNguoiHoc = pref.getString("tenNguoiHoc", "Người dùng");
            String email = pref.getString("email", "Email không xác định");
            String gioiTinh = pref.getString("gioiTinh", "Chưa rõ");
            String soDienThoai = pref.getString("soDienThoai", "Không có");

            // Hiển thị
            txtTenNguoiDung.setText("Tên: " + tenNguoiHoc);
            txtEmailNguoiDung.setText("Email: " + email);
            txtGioiTinhNguoiDung.setText("Giới tính: " + gioiTinh);
            txtSdtNguoiDung.setText("SĐT: " + soDienThoai);

            btnLoginLogout.setText("Đăng xuất");
            btnLoginLogout.setOnClickListener(v -> {
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            });
        }

        btnEditInfo.setOnClickListener(v -> showEditDialog());
    }

    private void showEditDialog() {
        SharedPreferences pref = getActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        int nguoiHocId = pref.getInt("nguoiHocId", -1);

        if (nguoiHocId == -1) {
            Toast.makeText(getContext(), "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_user, null);
        EditText edtHoTen = dialogView.findViewById(R.id.edtHoTen);
        EditText edtEmail = dialogView.findViewById(R.id.edtEmail);
        EditText edtSdt = dialogView.findViewById(R.id.edtSdt);
        RadioGroup rgGioiTinh = dialogView.findViewById(R.id.rgGioiTinh);
        RadioButton rbNam = dialogView.findViewById(R.id.rbNam);
        RadioButton rbNu = dialogView.findViewById(R.id.rbNu);

        String ten = pref.getString("tenNguoiHoc", "");
        String email = pref.getString("email", "");
        String sdt = pref.getString("soDienThoai", "");
        String gioiTinh = pref.getString("gioiTinh", "");

        edtHoTen.setText(ten);
        edtEmail.setText(email);
        edtSdt.setText(sdt);
        if (gioiTinh.equals("Nam")) rbNam.setChecked(true);
        else if (gioiTinh.equals("Nữ")) rbNu.setChecked(true);

        new AlertDialog.Builder(getContext())
                .setTitle("Chỉnh sửa thông tin")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String newTen = edtHoTen.getText().toString();
                    String newEmail = edtEmail.getText().toString();
                    String newSdt = edtSdt.getText().toString();
                    String newGioiTinh = rbNam.isChecked() ? "Nam" : "Nữ";

                    DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                    NguoiHoc updatedNguoiHoc = new NguoiHoc(nguoiHocId, newTen, newEmail, newGioiTinh, newSdt);
                    boolean success = dbHelper.updateNguoiHoc(updatedNguoiHoc);

                    if (success) {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("tenNguoiHoc", newTen);
                        editor.putString("email", newEmail);
                        editor.putString("soDienThoai", newSdt);
                        editor.putString("gioiTinh", newGioiTinh);
                        editor.apply();

                        txtTenNguoiDung.setText("Tên: " + newTen);
                        txtEmailNguoiDung.setText("Email: " + newEmail);
                        txtGioiTinhNguoiDung.setText("Giới tính: " + newGioiTinh);
                        txtSdtNguoiDung.setText("SĐT: " + newSdt);

                        Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}


