package com.example.myapplication;



import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class DangKyDialog extends Dialog {

    Spinner spnKhoaHoc, spnNguoiHoc;
    Button btnDangKy;
    DatabaseHelper dbHelper;
    ArrayList<String> dsEmailNguoiHoc = new ArrayList<>();

    ArrayList<String> dsKhoaHoc = new ArrayList<>();
    ArrayList<String> dsNguoiHoc = new ArrayList<>();
    ArrayList<Integer> idKhoaHoc = new ArrayList<>();
    ArrayList<Integer> idNguoiHoc = new ArrayList<>();

    public DangKyDialog(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_dangky);

        spnKhoaHoc = findViewById(R.id.spnKhoaHoc);
        spnNguoiHoc = findViewById(R.id.spnNguoiHoc);
        btnDangKy = findViewById(R.id.btnXacNhanDangKy);

        dbHelper = new DatabaseHelper(getContext());

        loadData();

        // Lấy thông tin người học hiện tại từ SharedPreferences
        SharedPreferences pref = getContext().getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        String email = pref.getString("email", "");



// Lọc người học hiện tại theo email

        if (!email.isEmpty()) {
            for (int i = 0; i < dsEmailNguoiHoc.size(); i++) {
                if (dsEmailNguoiHoc.get(i).equals(email)) {
                    int id = idNguoiHoc.get(i);
                    String name = dsNguoiHoc.get(i);
                    idNguoiHoc.clear();
                    dsNguoiHoc.clear();
                    idNguoiHoc.add(id);
                    dsNguoiHoc.add(name);
                    break;
                }
            }
        }


// Nếu chỉ có một người học, tự động chọn người đó trong Spinner
        if (idNguoiHoc.size() == 1) {
            spnNguoiHoc.setSelection(0);
            spnNguoiHoc.setEnabled(false); // Disable spinner, không cho thay đổi người học
        }

//        btnDangKy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int khId = idKhoaHoc.get(spnKhoaHoc.getSelectedItemPosition());
//                int nhId = idNguoiHoc.get(spnNguoiHoc.getSelectedItemPosition());
//
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                Cursor check = db.rawQuery("SELECT * FROM DangKy WHERE khoaHocId=? AND nguoiHocId=?",
//                        new String[]{String.valueOf(khId), String.valueOf(nhId)});
//                if (check.moveToFirst()) {
//                    Toast.makeText(getContext(), "Người học đã đăng ký khóa này", Toast.LENGTH_SHORT).show();
//                } else {
//                    db.execSQL("INSERT INTO DangKy(khoaHocId, nguoiHocId, ngayDangKy) VALUES (?, ?, date('now'))",
//                            new Object[]{khId, nhId});
//                    Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//                    dismiss();
//                    if (listener != null) {
//                        listener.onDangKySuccess();  // Gọi listener để cập nhật lại UI
//                    }
//                }
//                check.close();
//            }
//        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int khId = idKhoaHoc.get(spnKhoaHoc.getSelectedItemPosition());
                int nhId = idNguoiHoc.get(spnNguoiHoc.getSelectedItemPosition()); // <-- Đảm bảo sử dụng idNguoiHoc chính xác

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor check = db.rawQuery("SELECT * FROM DangKy WHERE khoaHocId=? AND nguoiHocId=?",
                        new String[]{String.valueOf(khId), String.valueOf(nhId)});
                if (check.moveToFirst()) {
                    Toast.makeText(getContext(), "Người học đã đăng ký khóa này", Toast.LENGTH_SHORT).show();
                } else {
                    db.execSQL("INSERT INTO DangKy(khoaHocId, nguoiHocId, ngayDangKy) VALUES (?, ?, date('now'))",
                            new Object[]{khId, nhId});
                    Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    dismiss();
                    if (listener != null) {
                        listener.onDangKySuccess();  // Gọi listener để cập nhật lại UI
                    }
                }
                check.close();
            }
        });

    }

    private void loadData() {
        dsKhoaHoc.clear();
        idKhoaHoc.clear();
        dsNguoiHoc.clear();
        idNguoiHoc.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c1 = db.rawQuery("SELECT id, ten FROM KhoaHoc", null);
        while (c1.moveToNext()) {
            idKhoaHoc.add(c1.getInt(0));
            dsKhoaHoc.add(c1.getString(1));
        }
        c1.close();

        // Lấy tất cả người học
//        Cursor c2 = db.rawQuery("SELECT id, tenNH, email FROM NguoiHoc", null);
//        while (c2.moveToNext()) {
//            idNguoiHoc.add(c2.getInt(0));
//            dsNguoiHoc.add(c2.getString(1));
//        }
        Cursor c2 = db.rawQuery("SELECT id, tenNH, email FROM NguoiHoc", null);
        while (c2.moveToNext()) {
            idNguoiHoc.add(c2.getInt(0));
            dsNguoiHoc.add(c2.getString(1));
            dsEmailNguoiHoc.add(c2.getString(2)); // thêm dòng này
        }
        c2.close();


        // Set adapter cho Spinner
        spnKhoaHoc.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, dsKhoaHoc));
        spnNguoiHoc.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, dsNguoiHoc));
    }

    //    validatedulieu
    public interface OnDangKySuccessListener {
        void onDangKySuccess();
    }

    private OnDangKySuccessListener listener;

    public void setOnDangKySuccessListener(OnDangKySuccessListener listener) {
        this.listener = listener;
    }

}

