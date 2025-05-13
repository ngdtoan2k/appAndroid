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
import com.example.myapplication.model.KhoaHoc;


import java.util.List;

public class KhoaHocAdapter extends RecyclerView.Adapter<KhoaHocAdapter.KhoaHocViewHolder> {
    private List<KhoaHoc> list;
    private int expandedPosition = -1;
    private Context context;
    private DatabaseHelper dbHelper;

    public KhoaHocAdapter(Context context, List<KhoaHoc> list) {
        this.context = context;
        this.list = list;
        this.dbHelper = new DatabaseHelper(context);
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

        // Bắt sự kiện click vào tên giảng viên
        holder.txtGiangVien.setOnClickListener(v -> {
            GiangVien gv = dbHelper.layGiangVienTheoTen(kh.giangVien);
            if (gv != null) {
                showGiangVienDialog(gv);
            } else {
                Toast.makeText(context, "Không tìm thấy giảng viên: " + kh.giangVien, Toast.LENGTH_SHORT).show();
            }
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

    private void showGiangVienDialog(GiangVien gv) {

        SharedPreferences sharedPref = context.getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
        int nguoiHocId = sharedPref.getInt("nguoiHocId", -1);
        String tenNguoiHoc = sharedPref.getString("tenNguoiHoc", "Người dùng ẩn danh");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_giangvien_detail, null);

        ImageView imgAnh = view.findViewById(R.id.imgGiangVien);
        TextView tvTen = view.findViewById(R.id.tvTenGV);
        TextView tvEmail = view.findViewById(R.id.tvEmail);
        TextView tvGioiTinh = view.findViewById(R.id.tvGioiTinh);
        TextView tvThamNien = view.findViewById(R.id.tvThamNien);
        TextView tvMoTa = view.findViewById(R.id.tvMoTa);
        TextView tvMonDay = view.findViewById(R.id.tvChuyenMon);

//        imgAnh.setImageResource(gv.getAnhMinhHoaResId());

        int anhMinhHoaResId = gv.getAnhMinhHoaResId();

        // Kiểm tra giá trị của ID tài nguyên ảnh
        if (anhMinhHoaResId != 0) {
            try {
                // Gán hình ảnh nếu ID hợp lệ
                ((ImageView) view.findViewById(R.id.imgGiangVien)).setImageResource(anhMinhHoaResId);
            } catch (Resources.NotFoundException e) {
                // Nếu không tìm thấy tài nguyên, gán hình ảnh mặc định
                Log.e("GiangVienAdapter", "Không tìm thấy tài nguyên hình ảnh với ID: " + anhMinhHoaResId);
                ((ImageView) view.findViewById(R.id.imgGiangVien)).setImageResource(R.drawable.userimg); // Hình ảnh mặc định
            }
        } else {
            // Nếu ID hình ảnh là 0, gán hình ảnh mặc định
            Log.e("GiangVienAdapter", "ID hình ảnh không hợp lệ: " + anhMinhHoaResId);
            ((ImageView) view.findViewById(R.id.imgGiangVien)).setImageResource(R.drawable.userimg); // Hình ảnh mặc định
        }


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
        CommentAdapter commentAdapter = new CommentAdapter(context, danhSachBL);
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

