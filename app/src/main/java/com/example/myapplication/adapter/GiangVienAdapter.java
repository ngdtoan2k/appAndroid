package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.model.CommentGV;
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


        holder.itemView.setOnClickListener(v -> {
            showGiangVienDetailDialog(context, gv);
        });
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


private void showGiangVienDetailDialog(Context context, GiangVien gv) {
//USER_PREF
    SharedPreferences sharedPref = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);
    int nguoiHocId = sharedPref.getInt("nguoiHocId", -1);  // Kiểm tra ID người học
    String tenNguoiHoc = sharedPref.getString("tenNguoiHoc", "Người dùng ẩn danh");

    Log.d("TEST_ID", "nguoiHocId truyền vào CommentAdapter: " + nguoiHocId);

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    View view = LayoutInflater.from(context).inflate(R.layout.dialog_giangvien_detail, null);

    ImageView imgAnh = view.findViewById(R.id.imgGiangVien);

    TextView tvTen = view.findViewById(R.id.tvTenGV);
        TextView tvEmail = view.findViewById(R.id.tvEmail);
        TextView tvGioiTinh = view.findViewById(R.id.tvGioiTinh);
        TextView tvThamNien = view.findViewById(R.id.tvThamNien);
        TextView tvMoTa = view.findViewById(R.id.tvMoTa);
        TextView tvMonDay = view.findViewById(R.id.tvChuyenMon);

    tvTen.setText(gv.getTenGV());
        tvEmail.setText("Email: " + gv.getEmail());
        tvGioiTinh.setText("Giới tính: " + gv.getGioiTinh());
        tvThamNien.setText("Thâm niên: " + gv.getThamNien() + " năm");
        tvMoTa.setText("Mô tả: " + gv.getMoTa());
        tvMonDay.setText("Môn giảng dạy: " + gv.getDanhSachMon());
        EditText edtBinhLuan = view.findViewById(R.id.edtBinhLuan);
        Button btnGuiBinhLuan = view.findViewById(R.id.btnGuiBinhLuan);
    RecyclerView recyclerViewComment = view.findViewById(R.id.recyclerViewComment);

    DatabaseHelper dbHelper = new DatabaseHelper(context);

    // Load bình luận ban đầu
    List<CommentGV> danhSachBL = dbHelper.layBinhLuanTheoGiangVien(gv.getId());
    CommentAdapter commentAdapter = new CommentAdapter(context, danhSachBL,nguoiHocId);
    recyclerViewComment.setLayoutManager(new LinearLayoutManager(context));
    recyclerViewComment.setAdapter(commentAdapter);

    btnGuiBinhLuan.setOnClickListener(v -> {
        String noiDung = edtBinhLuan.getText().toString().trim();
        if (!noiDung.isEmpty()) {
            SharedPreferences pref = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);
            String tenNguoiHoc1 = pref.getString("tenNguoiHoc", "Người dùng");
            Log.d("SharedPreferences", "Tên người dùng: " + tenNguoiHoc1);
            // Lấy tên người học từ SharedPreferences
//            dbHelper.themBinhLuan(gv.getId(), nguoiHocId, noiDung, tenNguoiHoc);
            dbHelper.themBinhLuan(gv.getId(), nguoiHocId, noiDung, tenNguoiHoc1);
            danhSachBL.clear();
            danhSachBL.addAll(dbHelper.layBinhLuanTheoGiangVien(gv.getId()));
            commentAdapter.notifyDataSetChanged();
            edtBinhLuan.setText("");
        } else {
            Toast.makeText(context, "Vui lòng nhập nội dung", Toast.LENGTH_SHORT).show();
        }
    });

    builder.setView(view);
    builder.setPositiveButton("Đóng", null);
    builder.show();
}


    }



