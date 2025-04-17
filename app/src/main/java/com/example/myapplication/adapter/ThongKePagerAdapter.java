package com.example.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.fragments.GiangVienFragment;
import com.example.myapplication.fragments.ThongKeFragment;

public class ThongKePagerAdapter extends FragmentStateAdapter {
    public ThongKePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ThongKeFragment();
        } else {
            return new GiangVienFragment(); // fragment hiển thị danh sách giảng viên
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
