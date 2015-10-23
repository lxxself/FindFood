package com.lxxself.findfood.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lxxself.findfood.R;
import com.lxxself.findfood.RecyclerItemClickListener;
import com.lxxself.findfood.activity.ItemDetailActivity;
import com.lxxself.findfood.adapter.RestaurantRecyclerAdapter;
import com.lxxself.findfood.model.RestaurantItem;
import com.lxxself.findfood.model.VersionModel;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2015/10/15.
 */
@SuppressLint("ValidFragment")
public class ListRestFragment extends Fragment {
    int color;
    RestaurantRecyclerAdapter adapter;
    public ListRestFragment(int color) {
        this.color = color;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_rest_fragment, container, false);
        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.list_rest_frag_bg);
        frameLayout.setBackgroundColor(color);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_rest_scrollableview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));

        List<RestaurantItem> list = new ArrayList<RestaurantItem>();
        list.addAll(VersionModel.getRsList());
        adapter = new RestaurantRecyclerAdapter(list);
        adapter.SetOnItemClickListener(new RestaurantRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }
    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

            Intent intent = new Intent(getActivity(), ItemDetailActivity.class);

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            view.findViewById(R.id.iv_pic), getString(R.string.transition_item_main_img));

            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());

        }
    };
}
