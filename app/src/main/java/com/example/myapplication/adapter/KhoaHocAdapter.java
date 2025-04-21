package com.example.myapplication.adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.KhoaHoc;

import java.util.ArrayList;
import java.util.List;

//public class KhoaHocAdapter extends RecyclerView.Adapter<KhoaHocAdapter.KhoaHocViewHolder> {
//    private ArrayList<KhoaHoc> list;
//
//    public KhoaHocAdapter(ArrayList<KhoaHoc> list) {
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public KhoaHocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_khoahoc, parent, false);
//        return new KhoaHocViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull KhoaHocViewHolder holder, int position) {
//        KhoaHoc kh = list.get(position);
//        holder.txtTen.setText(kh.ten);
//        holder.txtNgay.setText("Từ " + kh.ngayBD + " đến " + kh.ngayKT);
//        holder.txtTrangThai.setText(kh.kichHoat ? "Đang kích hoạt" : "Chưa kích hoạt");
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public static class KhoaHocViewHolder extends RecyclerView.ViewHolder {
//        TextView txtTen, txtNgay, txtTrangThai;
//
//        public KhoaHocViewHolder(@NonNull View itemView) {
//            super(itemView);
//            txtTen = itemView.findViewById(R.id.txtTenKH);
//            txtNgay = itemView.findViewById(R.id.txtNgay);
//            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
//        }
//    }
//}
//
public class KhoaHocAdapter extends RecyclerView.Adapter<KhoaHocAdapter.KhoaHocViewHolder> {
    private List<KhoaHoc> list;
    private int expandedPosition = -1;

    public KhoaHocAdapter(List<KhoaHoc> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public KhoaHocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khoahoc, parent, false);
        return new KhoaHocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhoaHocViewHolder holder, int position) {
        KhoaHoc kh = list.get(position);
        holder.txtTenKH.setText(kh.ten);
        holder.txtNgay.setText(kh.ngayBD + " - " + kh.ngayKT);
        holder.txtTrangThai.setText(kh.kichHoat ? "Kích hoạt" : "Chưa kích hoạt");

        holder.txtGiangVien.setText("Giảng viên: " + kh.giangVien);
        holder.txtMoTa.setText("Mô tả: " + kh.moTa);

        final boolean isExpanded = position == expandedPosition;
        holder.txtGiangVien.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.txtMoTa.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            expandedPosition = isExpanded ? -1 : position;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class KhoaHocViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenKH, txtNgay, txtTrangThai, txtGiangVien, txtMoTa;

        public KhoaHocViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenKH = itemView.findViewById(R.id.txtTenKH);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtGiangVien = itemView.findViewById(R.id.txtGiangVien);
            txtMoTa = itemView.findViewById(R.id.txtMoTa);
        }
    }
}
