package com.lxxself.findfood.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxxself.findfood.R;
import com.lxxself.findfood.activity.CommentActivity;
import com.lxxself.findfood.activity.PayActivity;
import com.lxxself.findfood.adapter.OrderRecyclerAdapter;
import com.lxxself.findfood.model.FFUser;
import com.lxxself.findfood.model.OrderItem;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import me.drakeet.materialdialog.MaterialDialog;

import static com.lxxself.findfood.util.AppUtil.createLoadingDialog;


/**
 * Created by Administrator on 2015/10/17.
 */
@SuppressLint("ValidFragment")
public class ListOrderFragment extends Fragment {


    @Bind(R.id.order_recyclerView)
    RecyclerView orderRecyclerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private Context mContext;
    private MaterialDialog progressDialog;
    private OrderRecyclerAdapter adapter;
    private List<OrderItem> orderList = new ArrayList<>();
    private boolean mIsFinished;
    public ListOrderFragment() {

    }
    public ListOrderFragment(Context context, boolean isFinished) {
        this.mContext = context;
        this.mIsFinished = isFinished;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_order_fragment, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        orderRecyclerView.setLayoutManager(linearLayoutManager);
        orderRecyclerView.setHasFixedSize(true);
        orderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new OrderRecyclerAdapter(getContext(),orderList);
        orderRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OrderRecyclerAdapter.onItemClickListener<OrderRecyclerAdapter.VersionViewHolder>() {
            @Override
            public void itemClick(View view, int position) {
                Intent intent;
                switch (orderList.get(position).getStatus()) {
                    case 0:
                        intent = new Intent(mContext, PayActivity.class);
                        intent.putExtra("orderItem", orderList.get(position));
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(mContext, CommentActivity.class);
                        intent.putExtra("orderItem", orderList.get(position));
                        startActivity(intent);
                        break;
                    case 2:
                        break;
                }

            }
        });
//        orderRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));

        refreshLayout.setColorSchemeResources(R.color.primary,R.color.accent,R.color.lite_blue);
        refreshLayout.setOnRefreshListener(mRefreshListener);
        progressDialog = createLoadingDialog(getContext());
        requestLocalData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            requestLocalData();
        }
    };

    private void requestLocalData() {
        progressDialog.show();
        BmobQuery<OrderItem> query = new BmobQuery<OrderItem>();
        FFUser user = BmobUser.getCurrentUser(getContext(), FFUser.class);
        query.addWhereEqualTo("customer", new BmobPointer(user));
        query.include("restaurant");
        query.setLimit(10);
        query.order("createdAt");
        if (mIsFinished) {
            query.addWhereEqualTo("status", 2);
        } else {
            query.addWhereNotEqualTo("status", 2);
        }
        query.findObjects(mContext, new FindListener<OrderItem>() {

            @Override
            public void onSuccess(List<OrderItem> object) {
                KLog.i("查询个数：" + object.size());
                orderList.clear();
                orderList.addAll(object);
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
                progressDialog.dismiss();
            }

            @Override
            public void onError(int code, String msg) {
                refreshLayout.setRefreshing(false);
                progressDialog.dismiss();
                KLog.i("smile", "查询失败：" + msg.toString());
            }

        });
    }
}
