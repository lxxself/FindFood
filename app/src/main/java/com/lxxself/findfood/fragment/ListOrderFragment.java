package com.lxxself.findfood.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lxxself.findfood.R;
import com.lxxself.findfood.activity.ItemDetailActivity;
import com.lxxself.findfood.adapter.RestaurantRecyclerAdapter;
import com.lxxself.findfood.model.RestaurantItem;
import com.lxxself.findfood.model.VersionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/17.
 */
@SuppressLint("ValidFragment")
public class ListOrderFragment extends Fragment {

    int color;
    RestaurantRecyclerAdapter adapter;
    public ListOrderFragment(int color) {
        this.color = color;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_order_fragment, container, false);
        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.list_order_frag_bg);
        frameLayout.setBackgroundColor(color);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_order_scrollableview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addOnItemTouchListener();

        List<RestaurantItem> list = new ArrayList<RestaurantItem>();
        list.addAll(VersionModel.getRsList());
        adapter = new RestaurantRecyclerAdapter(list);

        recyclerView.setAdapter(adapter);

        return view;
    }

}
