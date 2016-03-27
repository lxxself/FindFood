package com.lxxself.findfood.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.lxxself.findfood.R;
import com.lxxself.findfood.adapter.ListDropDownAdapter;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/10/17.
 */
public class ShouyeFragment extends Fragment {
    private int type = 0;
    private String key = "";
    private ListShopFragment listShopFragment;
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
        mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);
        frameLayout = (FrameLayout) getActivity().getLayoutInflater().inflate(R.layout.frame_layout_shouye, null);
        listShopFragment = new ListShopFragment(getContext(),getResources().getColor(R.color.bright_foreground_disabled_material_dark));
        ft.replace(R.id.frame_shouye, listShopFragment, key).commit();

        initView();
        return view;

    }

    private DropDownMenu mDropDownMenu;
    private FrameLayout frameLayout;
    private String headers[];
    private List<View> popupViews = new ArrayList<>();

    private ListDropDownAdapter placeAdapter;
    private ListDropDownAdapter categoryAdapter;
    private ListDropDownAdapter orderByAdapter;
    private ListDropDownAdapter situationAdapter;

    private String place[];
    private String category[];
    private String orderBy[];
    private String situation[];

    private int constellationPosition = 0;
    private void initView() {
        place = getResources().getStringArray(R.array.place);
        category = getActivity().getResources().getStringArray(R.array.category);
        orderBy = getActivity().getResources().getStringArray(R.array.orderBy);
        situation= getActivity().getResources().getStringArray(R.array.situation);
        headers = getActivity().getResources().getStringArray(R.array.headers);

        //init city menu
        final ListView placeView = new ListView(getContext());
        placeAdapter = new ListDropDownAdapter(getContext(), Arrays.asList(place));
        placeView.setDividerHeight(0);
        placeView.setAdapter(placeAdapter);


        final ListView categoryView = new ListView(getContext());
        categoryView.setDividerHeight(0);
        categoryAdapter = new ListDropDownAdapter(getContext(), Arrays.asList(category));
        categoryView.setAdapter(categoryAdapter);


        final ListView orderByView = new ListView(getContext());
        orderByView.setDividerHeight(0);
        orderByAdapter = new ListDropDownAdapter(getContext(), Arrays.asList(orderBy));
        orderByView.setAdapter(orderByAdapter);

        final ListView situationView = new ListView(getContext());
        situationView.setDividerHeight(0);
        situationAdapter = new ListDropDownAdapter(getContext(), Arrays.asList(situation));
        situationView.setAdapter(situationAdapter);

        popupViews.add(placeView);
        popupViews.add(categoryView);
        popupViews.add(orderByView);
        popupViews.add(situationView);

        //add item click event
        placeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                placeAdapter.setCheckItem(position);
                listShopFragment.querySelect(null, null, position, false);
                mDropDownMenu.setTabText(place[position]);
                mDropDownMenu.closeMenu();
            }
        });

        categoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryAdapter.setCheckItem(position);
                listShopFragment.querySelect(null,null,position, false);
                mDropDownMenu.setTabText(category[position]);
                mDropDownMenu.closeMenu();
            }
        });

        orderByView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orderByAdapter.setCheckItem(position);
                listShopFragment.querySelect(null, null, position, false);
                mDropDownMenu.setTabText(orderBy[position]);
                mDropDownMenu.closeMenu();
            }
        });

        situationView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                situationAdapter.setCheckItem(position);
                listShopFragment.querySelect(null,null,position,false);
                mDropDownMenu.setTabText(situation[position]);
                mDropDownMenu.closeMenu();
            }
        });


        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, frameLayout);
    }


}
