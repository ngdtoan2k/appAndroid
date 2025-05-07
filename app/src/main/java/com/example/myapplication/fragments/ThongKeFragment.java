package com.example.myapplication.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.myapplication.DangKyDialog;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.model.NguoiHoc;
//import com.example.myapplication.dialogs.DangKyDialog;
import java.util.ArrayList;


public class ThongKeFragment extends Fragment implements DangKyDialog.OnDangKySuccessListener {
    Spinner spinner;
    ListView listView;
    DatabaseHelper dbHelper;
    ArrayList<String> dsKhoaHoc, dsNguoiHoc;
    ArrayAdapter<String> adapterKH, adapterNH;
    ArrayList<Integer> idKhoaHoc;

    private String emailNguoiDungHienTai; // Biến lưu email của người đang dùng
    ArrayList<NguoiHoc> danhSachNguoiHoc = new ArrayList<>();

    public ThongKeFragment() {}
    public void setEmailNguoiDungHienTai(String email) {
        this.emailNguoiDungHienTai = email;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thongke, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        spinner = view.findViewById(R.id.spinnerKH);
        listView = view.findViewById(R.id.listNguoiHoc);
        dbHelper = new DatabaseHelper(getContext());

        dsKhoaHoc = new ArrayList<>();
        dsNguoiHoc = new ArrayList<>();
        idKhoaHoc = new ArrayList<>();

        adapterKH = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dsKhoaHoc);
        adapterKH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterKH);

        adapterNH = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dsNguoiHoc);
        listView.setAdapter(adapterNH);

        loadData();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadNguoiHocTheoKH(idKhoaHoc.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Tạo và đăng ký listener khi mở DangKyDialog
        DangKyDialog dialog = new DangKyDialog(getContext());
        dialog.setOnDangKySuccessListener(this);

//        listView.setOnItemClickListener((parent, view1, position, id) -> {
//            String tenNguoiHoc = dsNguoiHoc.get(position);
//            showConfirmDeleteDialog(tenNguoiHoc);
//        });



    }
    private void showConfirmDeleteDialog(String tenNguoiHoc) {
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Xóa người học")
                .setMessage("Bạn có chắc muốn xóa người học \"" + tenNguoiHoc + "\" khỏi khóa học này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    deleteNguoiHoc(tenNguoiHoc); // Gọi hàm xóa
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
    private void deleteNguoiHoc(String tenNguoiHoc) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Lấy id người học theo tên (chỉ xóa trong bảng DangKy)
        Cursor cursor = db.rawQuery("SELECT id FROM NguoiHoc WHERE tenNH = ?", new String[]{tenNguoiHoc});
        if (cursor.moveToFirst()) {
            int nguoiHocId = cursor.getInt(0);
            int khoaHocId = idKhoaHoc.get(spinner.getSelectedItemPosition());

            db.delete("DangKy", "nguoiHocId = ? AND khoaHocId = ?", new String[]{String.valueOf(nguoiHocId), String.valueOf(khoaHocId)});
        }
        cursor.close();

        // Làm mới danh sách
        loadNguoiHocTheoKH(idKhoaHoc.get(spinner.getSelectedItemPosition()));
    }


    @Override
    public void onDangKySuccess() {
        // Lấy ID khóa học đang chọn từ Spinner
        int selectedKhoaHocId = idKhoaHoc.get(spinner.getSelectedItemPosition());

        // Làm mới danh sách người học cho khóa học đã chọn
        loadNguoiHocTheoKH(selectedKhoaHocId);

        // Làm mới lại Spinner để đảm bảo thông tin mới được cập nhật
        loadKhoaHoc();  // Gọi lại phương thức load lại danh sách khóa học

        // Sau khi làm mới, đảm bảo rằng Spinner được chọn đúng khóa học đã đăng ký
        spinner.setSelection(idKhoaHoc.indexOf(selectedKhoaHocId));
    }

    public void showDangKyDialog() {
        DangKyDialog dialog = new DangKyDialog(getContext());
        dialog.setOnDangKySuccessListener(this);
        dialog.show();
    }
    @Override
    public void onResume() {
        super.onResume();

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String tenNguoiHoc = dsNguoiHoc.get(position);
            showConfirmDeleteDialog(tenNguoiHoc);
        });
    }

public void loadData() {
    loadKhoaHoc();

    // Gọi thêm loadNguoiHocTheoKH nếu bạn muốn làm mới danh sách người học ngay khi tải dữ liệu
    if (spinner.getSelectedItemPosition() >= 0) {
        int selectedKhoaHocId = idKhoaHoc.get(spinner.getSelectedItemPosition());
        loadNguoiHocTheoKH(selectedKhoaHocId);
    }
}


private void loadKhoaHoc() {
    dsKhoaHoc.clear();
    idKhoaHoc.clear();
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT id, ten FROM KhoaHoc", null);
    while (cursor.moveToNext()) {
        idKhoaHoc.add(cursor.getInt(0));
        dsKhoaHoc.add(cursor.getString(1));
    }
    cursor.close();
    adapterKH.notifyDataSetChanged();  // Thông báo cho Adapter cập nhật lại Spinner
}




//    private void loadNguoiHocTheoKH(int khoaHocId) {
//        dsNguoiHoc.clear();
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT tenNH FROM DangKy dk INNER JOIN NguoiHoc nh ON dk.nguoiHocId = nh.id WHERE dk.khoaHocId = ?", new String[]{String.valueOf(khoaHocId)});
//        while (cursor.moveToNext()) {
//            dsNguoiHoc.add(cursor.getString(0));
//        }
//        cursor.close();
//        adapterNH.notifyDataSetChanged();  // Đảm bảo Adapter được thông báo thay đổi
//
//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            String tenNguoiHoc = dsNguoiHoc.get(position);
//            showConfirmDeleteDialog(tenNguoiHoc);
//        });
//    }
private void loadNguoiHocTheoKH(int khoaHocId) {
    dsNguoiHoc.clear();
    danhSachNguoiHoc.clear();
    SQLiteDatabase db = dbHelper.getReadableDatabase();

    Cursor cursor = db.rawQuery(
            "SELECT nh.id, nh.tenNH, nh.email, nh.gioiTinh, nh.soDienThoai " +
                    "FROM DangKy dk INNER JOIN NguoiHoc nh ON dk.nguoiHocId = nh.id WHERE dk.khoaHocId = ?",
            new String[]{String.valueOf(khoaHocId)}
    );

    while (cursor.moveToNext()) {
        NguoiHoc nh = new NguoiHoc(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
        );
        danhSachNguoiHoc.add(nh);
        dsNguoiHoc.add(nh.getTenNH());
    }
    cursor.close();
    adapterNH.notifyDataSetChanged();

    listView.setOnItemClickListener((parent, view, position, id) -> {
        NguoiHoc selectedNH = danhSachNguoiHoc.get(position);
        if (selectedNH.getEmail().equals(emailNguoiDungHienTai)) {
            showConfirmDeleteDialog(selectedNH.getTenNH());
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Không thể xóa")
                    .setMessage("Bạn chỉ có thể xóa tài khoản của chính mình.")
                    .setPositiveButton("OK", null)
                    .show();
        }
    });
}


}

