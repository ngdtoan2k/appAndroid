package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.GiangVien;

import java.util.ArrayList;
import java.util.List;

public class GiangVienAdapter extends RecyclerView.Adapter<GiangVienAdapter.ViewHolder> implements Filterable {
    private List<GiangVien> danhSach;
    private List<GiangVien> danhSachFiltered;
    private Context context;

    public GiangVienAdapter(Context context, List<GiangVien> danhSach) {
        this.context = context;
        this.danhSach = danhSach;
        this.danhSachFiltered = danhSach;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_giangvien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GiangVien gv = danhSachFiltered.get(position);
        holder.txtTen.setText(gv.getTenGV());
        holder.txtEmail.setText(gv.getEmail());
        holder.txtChuyenMon.setText(gv.getChuyenMon());
    }

    @Override
    public int getItemCount() {
        return danhSachFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return giangVienFilter;
    }

    private Filter giangVienFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<GiangVien> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(danhSach);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (GiangVien gv : danhSach) {
                    if (gv.getTenGV().toLowerCase().contains(filterPattern) ||
                            gv.getEmail().toLowerCase().contains(filterPattern) ||
                            gv.getChuyenMon().toLowerCase().contains(filterPattern)) {
                        filteredList.add(gv);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            danhSachFiltered = (List<GiangVien>) results.values;
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtEmail, txtChuyenMon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTenGV);
            txtEmail = itemView.findViewById(R.id.txtEmailGV);
            txtChuyenMon = itemView.findViewById(R.id.txtChuyenMonGV);
        }
    }
}

//package com.example.myapplication;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class GiangVienAdapter extends RecyclerView.Adapter<GiangVienAdapter.ViewHolder> {
//    private List<GiangVien> danhSach;
//    private Context context;
//
//    public GiangVienAdapter(Context context, List<GiangVien> danhSach) {
//        this.context = context;
//        this.danhSach = danhSach;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_giangvien, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        GiangVien gv = danhSach.get(position);
//        holder.txtTen.setText(gv.getTenGV());
//        holder.txtEmail.setText(gv.getEmail());
//        holder.txtChuyenMon.setText(gv.getChuyenMon());
//    }
//
//    @Override
//    public int getItemCount() {
//        return danhSach.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView txtTen, txtEmail, txtChuyenMon;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            txtTen = itemView.findViewById(R.id.txtTenGV);
//            txtEmail = itemView.findViewById(R.id.txtEmailGV);
//            txtChuyenMon = itemView.findViewById(R.id.txtChuyenMonGV);
//        }
//
//
//    }
//
//}
//
