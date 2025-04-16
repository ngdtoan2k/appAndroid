package com.example.myapplication;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KhoaHocFragment extends Fragment {
    RecyclerView recyclerView;
    EditText edtSearch;
    CheckBox chkKichHoat;
    DatabaseHelper dbHelper;
    KhoaHocAdapter adapter;
    ArrayList<KhoaHoc> list;

    public KhoaHocFragment() {}

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

        list = new ArrayList<>();
        adapter = new KhoaHocAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        loadData();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadData();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        chkKichHoat.setOnCheckedChangeListener((buttonView, isChecked) -> loadData());
    }

    private void loadData() {
        list.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String keyword = edtSearch.getText().toString().trim();
        boolean filterKichHoat = chkKichHoat.isChecked();

        String sql = "SELECT * FROM KhoaHoc WHERE ten LIKE ?";
        ArrayList<String> args = new ArrayList<>();
        args.add("%" + keyword + "%");

        if (filterKichHoat) {
            sql += " AND kichHoat = 1";
        }

        Cursor cursor = db.rawQuery(sql, args.toArray(new String[0]));
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String ngayBD = cursor.getString(2);
            String ngayKT = cursor.getString(3);
            int kichHoat = cursor.getInt(4);
            list.add(new KhoaHoc(id, ten, ngayBD, ngayKT, kichHoat == 1));
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}

