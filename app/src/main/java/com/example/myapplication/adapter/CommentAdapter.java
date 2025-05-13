package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.model.CommentGV;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context context;
    private List<CommentGV> list;
   private int nguoiHocId;


    public CommentAdapter(Context context, List<CommentGV> list,int nguoiHocId) {
        this.context = context;
        this.list = list;
        this.nguoiHocId = nguoiHocId;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNguoiCM, txtNoidung,txtThoigian;
        ImageButton btnXoaBinhLuan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNguoiCM = itemView.findViewById(R.id.txtNguoiCM);
            txtNoidung = itemView.findViewById(R.id.txtNoidung);
            txtThoigian = itemView.findViewById(R.id.txtThoiGian);
        btnXoaBinhLuan  = itemView.findViewById(R.id.btnXoaBL);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentGV cmt = list.get(position);

//        holder.txtNguoiCM.setText(cmt.getTenNguoiBinhLuan());
        holder.txtNguoiCM.setText(cmt.getTenNguoiBinhLuan());
        holder.txtNoidung.setText(cmt.getNoiDung());
        holder.txtThoigian.setText(cmt.getThoiGian());



//        if (cmt.getNguoiHocId() == nguoiHocId) {
//            holder.btnXoaBinhLuan.setVisibility(View.VISIBLE);
//            holder.btnXoaBinhLuan.setOnClickListener(v -> {
//                DatabaseHelper dbHelper = new DatabaseHelper(context);
//                dbHelper.xoaBinhLuan(cmt.getId(),nguoiHocId);
//                if (position >= 0 && position < list.size()) {
//                    list.remove(position);
//                    notifyDataSetChanged();
//                } else {
//                    Log.e("CommentAdapter", "Invalid position: " + position);
//                }
//            });
//        } else {
//            holder.btnXoaBinhLuan.setVisibility(View.GONE);
//            holder.btnXoaBinhLuan.setOnClickListener(null); // xoá listener cũ
//        }
        if (cmt.getNguoiHocId() == nguoiHocId) {
            holder.btnXoaBinhLuan.setVisibility(View.VISIBLE);
            holder.btnXoaBinhLuan.setOnClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc muốn xóa bình luận này?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            DatabaseHelper dbHelper = new DatabaseHelper(context);
                            dbHelper.xoaBinhLuan(cmt.getId(), nguoiHocId); // Gọi đúng 2 tham số
                            list.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, list.size());
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            });
        } else {
            // Nếu không có quyền, ẩn nút và clear click
            holder.btnXoaBinhLuan.setVisibility(View.GONE);
            holder.btnXoaBinhLuan.setOnClickListener(null);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
