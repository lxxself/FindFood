package com.lxxself.findfood.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxxself.findfood.R;
import com.lxxself.findfood.adapter.ViewPagerAdapter;

/**
 * Created by Administrator on 2015/10/15.
 */
public class FaxianFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.faxian_fragment, null);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new ListShopFragment(getContext()), "全部");
        adapter.addFrag(new ListShopFragment(getContext()), "小吃快餐");
        adapter.addFrag(new ListShopFragment(getContext()), "咖啡厅");
        viewPager.setAdapter(adapter);
    }
}
