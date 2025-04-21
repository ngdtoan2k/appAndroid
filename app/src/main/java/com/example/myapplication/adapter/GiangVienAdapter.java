package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
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
//    private void showGiangVienDetailDialog(Context context, GiangVien gv) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View view = LayoutInflater.from(context).inflate(R.layout.dialog_giangvien_detail, null);
//        builder.setView(view);
//
//        ((ImageView) view.findViewById(R.id.imgGiangVien)).setImageResource(gv.getAnhMinhHoaResId());
//        ((TextView) view.findViewById(R.id.tvTenGV)).setText("Tên: " + gv.getTenGV());
//        ((TextView) view.findViewById(R.id.tvEmail)).setText("Email: " + gv.getEmail());
//        ((TextView) view.findViewById(R.id.tvChuyenMon)).setText("Chuyên môn: " + gv.getChuyenMon());
//        ((TextView) view.findViewById(R.id.tvGioiTinh)).setText("Giới tính: " + gv.getGioiTinh());
//        ((TextView) view.findViewById(R.id.tvThamNien)).setText("Thâm niên: " + gv.getThamNien() + " năm");
//        ((TextView) view.findViewById(R.id.tvMoTa)).setText("Mô tả: " + gv.getMoTa());
//        ((TextView) view.findViewById(R.id.tvDanhSachMon)).setText("Các môn giảng dạy:\n- " + String.join("\n- ", gv.getDanhSachMon()));
//
//        builder.setPositiveButton("Đóng", null);
//        builder.create().show();
//    }
private void showGiangVienDetailDialog(Context context, GiangVien gv) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    View view = LayoutInflater.from(context).inflate(R.layout.dialog_giangvien_detail, null);
    builder.setView(view);

    // Lấy giá trị ảnh minh họa từ đối tượng GiangVien
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

    // Gán các giá trị khác cho TextView
    ((TextView) view.findViewById(R.id.tvTenGV)).setText("Tên: " + (gv.getTenGV() != null ? gv.getTenGV() : "Chưa có tên"));
    ((TextView) view.findViewById(R.id.tvEmail)).setText("Email: " + (gv.getEmail() != null ? gv.getEmail() : "Chưa có email"));
    ((TextView) view.findViewById(R.id.tvChuyenMon)).setText("Chuyên môn: " + (gv.getChuyenMon() != null ? gv.getChuyenMon() : "Chưa có chuyên môn"));
    ((TextView) view.findViewById(R.id.tvGioiTinh)).setText("Giới tính: " + (gv.getGioiTinh() != null ? gv.getGioiTinh() : "Chưa có giới tính"));
    ((TextView) view.findViewById(R.id.tvThamNien)).setText("Thâm niên: " + gv.getThamNien() + " năm");
    ((TextView) view.findViewById(R.id.tvMoTa)).setText("Mô tả: " + (gv.getMoTa() != null ? gv.getMoTa() : "Chưa có mô tả"));

    // Kiểm tra danh sách môn giảng dạy
    if (gv.getDanhSachMon() != null && !gv.getDanhSachMon().isEmpty()) {
        ((TextView) view.findViewById(R.id.tvDanhSachMon)).setText("Các môn giảng dạy:\n- " + String.join("\n- ", gv.getDanhSachMon()));
    } else {
        ((TextView) view.findViewById(R.id.tvDanhSachMon)).setText("Không có môn giảng dạy.");
    }

    // Thêm nút "Đóng" cho dialog
    builder.setPositiveButton("Đóng", null);

    // Tạo và hiển thị dialog
    builder.create().show();
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
