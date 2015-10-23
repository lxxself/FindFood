package com.lxxself.findfood.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lxxself.findfood.R;
import com.lxxself.findfood.adapter.ViewPagerAdapter;

/**
 * Created by Administrator on 2015/10/17.
 */
public class DingdanFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.faxian_fragment, null);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        showToast("tab0");
                        break;
                    case 1:
                        showToast("tab1");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }
    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new ListOrderFragment(getResources().getColor(R.color.accent_material_light)), "未完成订单");
        adapter.addFrag(new ListOrderFragment(getResources().getColor(R.color.ripple_material_light)), "已完成");
        viewPager.setAdapter(adapter);
    }
}
