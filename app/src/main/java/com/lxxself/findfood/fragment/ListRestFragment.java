package com.lxxself.findfood.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lxxself.findfood.R;
import com.lxxself.findfood.RecyclerItemClickListener;
import com.lxxself.findfood.activity.ItemDetailActivity;
import com.lxxself.findfood.adapter.RestaurantRecyclerAdapter;
import com.lxxself.findfood.model.ShopItem;
import com.lxxself.findfood.model.ShopItem;
import com.lxxself.findfood.model.VersionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

import static com.lxxself.findfood.util.ToastUtil.show;

/**
 * Created by Administrator on 2015/10/15.
 */
@SuppressLint("ValidFragment")
public class ListRestFragment extends Fragment {
    private int color;
    private String key;
    private RestaurantRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private Context mContext;
    private boolean localRequest;
    private List<ShopItem> list;
    public ListRestFragment(int color, String key) {
        this.color = color;
        this.key = key;
    }
    public ListRestFragment(int color) {
        this.color = color;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_rest_fragment, container, false);
        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.list_rest_frag_bg);
        frameLayout.setBackgroundColor(color);
        recyclerView = (RecyclerView) view.findViewById(R.id.list_rest_scrollableview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));

//        List<ShopItem> list = new ArrayList<ShopItem>();
//        list.addAll(VersionModel.getRsList());
//        adapter =new RestaurantRecyclerAdapter(getContext(),list);
//        recyclerView.setAdapter(adapter);
        requestLocalData();
        return view;
    }

    private void requestNetData() {
        BmobQuery<ShopItem> query = new BmobQuery<ShopItem>();
        String bql = "select * from ShopItem";
        //设置查询的SQL语句
        query.setSQL(bql);
        query.doSQLQuery(getContext(), new SQLQueryListener<ShopItem>() {

            @Override
            public void done(BmobQueryResult<ShopItem> result, BmobException e) {
                if (e == null) {
                    list = (List<ShopItem>) result.getResults();
                    if (list != null && list.size() > 0) {
                        adapter = new RestaurantRecyclerAdapter(getContext(), list);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.i("smile", "查询成功，无数据返回");
                    }
                } else {
                    Log.e("smile", "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                }
            }
        });
    }

    private void requestLocalData() {
        BmobQuery<ShopItem> query = new BmobQuery<ShopItem>();
        query.setLimit(10);
        query.order("createdAt");
//判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
        boolean isCache = query.hasCachedResult(getContext(),ShopItem.class);
        if(isCache){
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
        }else{
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
        }
        query.findObjects(getContext(), new FindListener<ShopItem>() {

            @Override
            public void onSuccess(List<ShopItem> object) {
                // TODO Auto-generated method stub
                Log.i("smile", "查询个数：" + object.size());
                list = object;
                adapter = new RestaurantRecyclerAdapter(getContext(), object);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                Log.i("smile", "查询失败：" + msg.toString());
            }
        });
    }

    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

            Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("shopItem",list.get(position));
            intent.putExtras(bundle);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            view.findViewById(R.id.iv_pic), getString(R.string.transition_item_main_img));

            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());

        }
    };
}
