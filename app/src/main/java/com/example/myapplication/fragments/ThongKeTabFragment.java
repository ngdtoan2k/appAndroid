package com.example.myapplication.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class ThongKeTabFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ThongKePagerAdapter adapter;

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

        adapter = new ThongKePagerAdapter(getActivity());
        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(2); // Giữ luôn cả 2 fragment trong bộ nhớ

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

    // Cho phép MainActivity2 gọi ViewPager
    public ViewPager2 getViewPager() {
        return viewPager;
    }

    // Trả về fragment đang hiển thị theo vị trí
    public Fragment getFragmentAt(int position) {
        return adapter.getFragmentAt(position);
    }

    // Adapter lưu các fragment để truy cập lại
    private class ThongKePagerAdapter extends FragmentStateAdapter {
        private final ArrayList<Fragment> fragments = new ArrayList<>();

        public ThongKePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            fragments.add(new ThongKeFragment());
            fragments.add(new GiangVienFragment());
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }

        public Fragment getFragmentAt(int position) {
            return fragments.get(position);
        }
    }
}

