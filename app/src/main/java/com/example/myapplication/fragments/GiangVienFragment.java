package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.adapter.GiangVienAdapter;
import com.example.myapplication.model.GiangVien;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GiangVienFragment extends Fragment {

    private RecyclerView recyclerView;
    private GiangVienAdapter adapter;
    private List<GiangVien> danhSachGiangVien;
    private SearchView searchView;

    private boolean isDescending = true;
    private ImageView imgSortArrow;

    public GiangVienFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_giangvien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewGiangVien);
        searchView = view.findViewById(R.id.searchViewGiangVien);
        imgSortArrow = view.findViewById(R.id.imgSortArrow);

        loadData();


        imgSortArrow.setOnClickListener(v -> {
            isDescending = !isDescending;
            updateArrowIcon();
//            sortListByThamNien(); // đã tự notify nếu cần
//            adapter.notifyDataSetChanged();
            adapter.sortByThamNien(isDescending);

        });

    }

    public void loadData() {
        danhSachGiangVien = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        danhSachGiangVien = dbHelper.getAllGiangVien();

        sortListByThamNien();

        adapter = new GiangVienAdapter(getContext(), danhSachGiangVien);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    private void sortListByThamNien() {
        if (danhSachGiangVien == null) return;

        Collections.sort(danhSachGiangVien, new Comparator<GiangVien>() {
            @Override
            public int compare(GiangVien gv1, GiangVien gv2) {
                return isDescending
                        ? Integer.compare(gv2.getThamNien(), gv1.getThamNien()) // giảm dần
                        : Integer.compare(gv1.getThamNien(), gv2.getThamNien()); // tăng dần
            }
        });

        // Chỉ gọi notifyDataSetChanged nếu adapter đã được khởi tạo
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }



    private void updateArrowIcon() {
        if (isDescending) {
            imgSortArrow.setImageResource(R.drawable.arrow_drop_down_24);
        } else {
            imgSortArrow.setImageResource(R.drawable.arrow_drop_up_24);
        }
    }

}

