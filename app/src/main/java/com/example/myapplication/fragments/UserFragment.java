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

    private TextView txtUserInfo;
    private Button btnLoginLogout;
    private Button btnEditInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }



@Override
public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    txtUserInfo = view.findViewById(R.id.txtUserInfo);
    btnLoginLogout = view.findViewById(R.id.btnLoginLogout);


    btnEditInfo = view.findViewById(R.id.btnEditInfo);

    btnEditInfo.setOnClickListener(v -> {
        showEditDialog();
    });



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
//    private void showEditDialog() {
//        SharedPreferences pref = getActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE);
//        int nguoiHocId = pref.getInt("nguoiHocId", -1);
//
//        if (nguoiHocId == -1) {
//            Toast.makeText(getContext(), "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_user, null);
//
//        EditText edtHoTen = dialogView.findViewById(R.id.edtHoTen);
//        EditText edtEmail = dialogView.findViewById(R.id.edtEmail);
//        EditText edtSdt = dialogView.findViewById(R.id.edtSdt);
//        RadioGroup rgGioiTinh = dialogView.findViewById(R.id.rgGioiTinh);
//        RadioButton rbNam = dialogView.findViewById(R.id.rbNam);
//        RadioButton rbNu = dialogView.findViewById(R.id.rbNu);
//
//        // Lấy dữ liệu hiện tại từ SharedPreferences
//        String ten = pref.getString("tenNguoiHoc", "");
//        String email = pref.getString("email", "");
//        String sdt = pref.getString("soDienThoai", "");
//        String gioiTinh = pref.getString("gioiTinh", "");
//
//        // Gán vào EditText
//        edtHoTen.setText(ten);
//        edtEmail.setText(email);
//        edtSdt.setText(sdt);
//        if (gioiTinh.equals("Nam")) rbNam.setChecked(true);
//        else if (gioiTinh.equals("Nữ")) rbNu.setChecked(true);
//
//        new AlertDialog.Builder(getContext())
//                .setTitle("Chỉnh sửa thông tin")
//                .setView(dialogView)
//                .setPositiveButton("Lưu", (dialog, which) -> {
//                    String newTen = edtHoTen.getText().toString();
//                    String newEmail = edtEmail.getText().toString();
//                    String newSdt = edtSdt.getText().toString();
//                    String newGioiTinh = rbNam.isChecked() ? "Nam" : "Nữ";
//
//                    // Cập nhật vào SharedPreferences
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("tenNguoiHoc", newTen);
//                    editor.putString("email", newEmail);
//                    editor.putString("soDienThoai", newSdt);
//                    editor.putString("gioiTinh", newGioiTinh);
//                    editor.apply();
//
//                    // Cập nhật lại TextView hiển thị thông tin
//                    String info = "Tên: " + newTen +
//                            "\nEmail: " + newEmail +
//                            "\nGiới tính: " + newGioiTinh +
//                            "\nSĐT: " + newSdt;
//                    txtUserInfo.setText(info);
//
//                    Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
//                })
//                .setNegativeButton("Hủy", null)
//                .show();
//    }
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

    // Lấy dữ liệu hiện tại từ SharedPreferences
    String ten = pref.getString("tenNguoiHoc", "");
    String email = pref.getString("email", "");
    String sdt = pref.getString("soDienThoai", "");
    String gioiTinh = pref.getString("gioiTinh", "");

    // Gán vào EditText
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

                // Cập nhật vào cơ sở dữ liệu
                DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                NguoiHoc updatedNguoiHoc = new NguoiHoc(nguoiHocId, newTen, newEmail, newGioiTinh, newSdt);
                boolean success = dbHelper.updateNguoiHoc(updatedNguoiHoc);

                if (success) {
                    // Nếu cần vẫn có thể lưu vào SharedPreferences để hiển thị nhanh
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("tenNguoiHoc", newTen);
                    editor.putString("email", newEmail);
                    editor.putString("soDienThoai", newSdt);
                    editor.putString("gioiTinh", newGioiTinh);
                    editor.apply();

                    // Cập nhật lại giao diện
                    String info = "Tên: " + newTen +
                            "\nEmail: " + newEmail +
                            "\nGiới tính: " + newGioiTinh +
                            "\nSĐT: " + newSdt;
                    txtUserInfo.setText(info);

                    Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("Hủy", null)
            .show();
}



}
