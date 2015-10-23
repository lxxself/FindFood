package com.lxxself.findfood.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxxself.findfood.R;

/**
 * Created by Administrator on 2015/10/17.
 */
public class ShouyeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shouye_fragment, null);
        getFragmentManager().beginTransaction().replace(R.id.frame_shouye, new ListRestFragment(getResources().getColor(R.color.accent_material_light))).commit();

        return view;

    }
}
