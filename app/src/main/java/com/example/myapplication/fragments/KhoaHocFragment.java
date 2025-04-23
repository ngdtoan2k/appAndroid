package com.example.myapplication.fragments;



import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.adapter.KhoaHocAdapter;
import com.example.myapplication.model.KhoaHoc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
public class KhoaHocFragment extends Fragment {
    RecyclerView recyclerView;
    EditText edtSearch;
    CheckBox chkKichHoat;
    DatabaseHelper dbHelper;
    KhoaHocAdapter adapter;
    ArrayList<KhoaHoc> list;
    TextView txtTimeStart;
    Calendar selectedCalendar = Calendar.getInstance();
    private TextView tvSelectedDate;

    public KhoaHocFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_khoahoc, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        edtSearch = view.findViewById(R.id.edtSearch);
        chkKichHoat = view.findViewById(R.id.chkKichHoat);
        dbHelper = new DatabaseHelper(getContext());

        tvSelectedDate = view.findViewById(R.id.tvSelectedDate);
        tvSelectedDate.setOnClickListener(v -> showDatePickerDialog());

        // Hiệu ứng background động
        ConstraintLayout constraintLayout = view.findViewById(R.id.constraintLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000); // 2 giây cho hiệu ứng fade-in
        animationDrawable.setExitFadeDuration(2000);  // 2 giây cho hiệu ứng fade-out
        animationDrawable.start();

        // Khởi tạo adapter cho RecyclerView
        list = new ArrayList<>();
        adapter = new KhoaHocAdapter(getContext(), list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Gọi loadData để hiển thị tất cả khóa học khi mới mở app
        loadData(); // Không cần tham số để hiển thị tất cả dữ liệu


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadData(); // Tìm kiếm độc lập
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        // Lắng nghe sự thay đổi trạng thái của checkbox KichHoat
//        chkKichHoat.setOnCheckedChangeListener((buttonView, isChecked) -> loadData()); // Gọi lại loadData khi checkbox thay đổi
        chkKichHoat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            loadData(); // Dù đã chọn ngày hay chưa, hàm xử lý logic bên trong
        });



    }



    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Gán lại giá trị cho selectedCalendar
                    selectedCalendar.set(selectedYear, selectedMonth, selectedDay);

                    // Hiển thị ngày đã chọn
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    tvSelectedDate.setText("Ngày bắt đầu: " + selectedDate);

                    // Gọi lại loadData duy nhất
                    loadData();
                }, year, month, day);
        datePickerDialog.show();
    }



    private void loadData() {
        list.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String keyword = edtSearch.getText().toString().trim();
        boolean filterKichHoat = chkKichHoat.isChecked();

        boolean hasDateFilter = selectedCalendar != null && selectedCalendar.getTimeInMillis() > 0;
        String selectedDate = String.format("%04d-%02d-%02d",
                selectedCalendar.get(Calendar.YEAR),
                selectedCalendar.get(Calendar.MONTH) + 1,
                selectedCalendar.get(Calendar.DAY_OF_MONTH));

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM KhoaHoc WHERE 1=1");
        ArrayList<String> args = new ArrayList<>();

        if (hasDateFilter) {
            sqlBuilder.append(" AND ngayBatDau >= ?");
            args.add(selectedDate);
        }

        if (filterKichHoat) {
            sqlBuilder.append(" AND kichHoat = 1");
        }

        if (!keyword.isEmpty()) {
            sqlBuilder.append(" AND ten LIKE ?");
            args.add("%" + keyword + "%");
        }

        Cursor cursor = db.rawQuery(sqlBuilder.toString(), args.toArray(new String[0]));
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String ngayBD = cursor.getString(2);
            String ngayKT = cursor.getString(3);
            int kichHoat = cursor.getInt(4);
            String giangVien = cursor.getString(5);
            String moTa = cursor.getString(6);
            list.add(new KhoaHoc(id, ten, ngayBD, ngayKT, kichHoat == 1, giangVien, moTa));
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

}