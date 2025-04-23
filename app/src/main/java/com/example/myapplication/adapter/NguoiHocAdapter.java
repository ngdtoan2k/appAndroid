package com.example.myapplication.adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.NguoiHoc;

import java.util.ArrayList;

public class NguoiHocAdapter extends RecyclerView.Adapter<NguoiHocAdapter.NguoiHocViewHolder> {
    private ArrayList<NguoiHoc> list;

    public NguoiHocAdapter(ArrayList<NguoiHoc> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NguoiHocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nguoihoc, parent, false);
        return new NguoiHocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiHocViewHolder holder, int position) {
        NguoiHoc nh = list.get(position);
        holder.txtTen.setText(nh.getTenNH());
//        holder.txtEmail.setText(nh.email);
        holder.txtEmail.setText(nh.getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class NguoiHocViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtEmail;

        public NguoiHocViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTenNguoiHoc);
            txtEmail = itemView.findViewById(R.id.txtEmailNguoiHoc);
        }
    }
}
