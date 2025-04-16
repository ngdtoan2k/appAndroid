package com.example.myapplication;



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

import java.util.ArrayList;

public class ThongKeFragment extends Fragment {
    Spinner spinner;
    ListView listView;
    DatabaseHelper dbHelper;
    ArrayList<String> dsKhoaHoc, dsNguoiHoc;
    ArrayAdapter<String> adapterKH, adapterNH;
    ArrayList<Integer> idKhoaHoc;

    public ThongKeFragment() {}

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

        loadKhoaHoc();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadNguoiHocTheoKH(idKhoaHoc.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
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
        adapterKH.notifyDataSetChanged();
    }

    private void loadNguoiHocTheoKH(int khoaHocId) {
        dsNguoiHoc.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT tenNH FROM DangKy dk INNER JOIN NguoiHoc nh ON dk.nguoiHocId = nh.id WHERE dk.khoaHocId = ?", new String[]{String.valueOf(khoaHocId)});
        while (cursor.moveToNext()) {
            dsNguoiHoc.add(cursor.getString(0));
        }
        cursor.close();
        adapterNH.notifyDataSetChanged();
    }
}
