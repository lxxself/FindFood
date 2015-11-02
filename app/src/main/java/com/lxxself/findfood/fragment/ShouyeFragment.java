package com.lxxself.findfood.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxxself.findfood.R;

/**
 * Created by Administrator on 2015/10/17.
 */
public class ShouyeFragment extends Fragment {
    private int type = 0;
    private String key = "";
    public ShouyeFragment() {

    }

    @SuppressLint("ValidFragment")
    public ShouyeFragment(int type, String key) {
        this.key = key;
        this.type = type;
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shouye_fragment, null);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (type) {
            case 0:
                ft.replace(R.id.frame_shouye, new ListRestFragment(getResources().getColor(R.color.accent_material_light)),key).commit();
                break;
            case 1:

                break;
            case 2:
                ft.replace(R.id.frame_shouye, new ListRestFragment(getResources().getColor(R.color.accent_material_light)),key).commit();
                Log.d("ShouyeFragment", key);
                break;
        }

        return view;

    }
}
