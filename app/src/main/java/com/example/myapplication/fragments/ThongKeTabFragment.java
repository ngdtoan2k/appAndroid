package com.example.myapplication.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.fragments.GiangVienFragment;
import com.example.myapplication.fragments.ThongKeFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ThongKeTabFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    public ThongKeTabFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thongke_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        ThongKePagerAdapter adapter = new ThongKePagerAdapter(getActivity());
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Người học");
                    } else {
                        tab.setText("Giảng viên");
                    }
                }
        ).attach();
    }

    private static class ThongKePagerAdapter extends FragmentStateAdapter {

        public ThongKePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new ThongKeFragment(); // Fragment thống kê người học (cũ)
            } else {
                return new GiangVienFragment(); // Sẽ tạo sau
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
