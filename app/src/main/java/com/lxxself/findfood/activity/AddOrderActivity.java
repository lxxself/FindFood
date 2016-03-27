package com.lxxself.findfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxxself.findfood.R;
import com.lxxself.findfood.model.FFUser;
import com.lxxself.findfood.model.OrderItem;
import com.lxxself.findfood.model.ShopItem;
import com.lxxself.findfood.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import me.drakeet.materialdialog.MaterialDialog;

import static com.lxxself.findfood.util.AppUtil.getDefaultString;

public class AddOrderActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.item_name)
    TextView itemName;
    @Bind(R.id.tv_ordertime)
    TextView tvOrdertime;
    @Bind(R.id.et_people_num)
    EditText etPeopleNum;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_bind_phone)
    TextView tvBindPhone;

    private String mShopId;
    private String mItemName;
    private String mPhone;
    private int mPeopleNum;
    private String mOrderTime;
    private FFUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        ButterKnife.bind(this);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mOrderTime = simpleDateFormat.format(new Date());

        Intent getIntent = getIntent();
        if (getIntent != null) {
            mShopId = getIntent.getStringExtra("shopId");
            mItemName = getIntent.getStringExtra("name");
        }

        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BmobUser.getCurrentUser(this, FFUser.class) != null) {
            user= BmobUser.getCurrentUser(this, FFUser.class);
            mPhone = getDefaultString(user.getMobilePhoneNumber(), "15757124564");
        }
    }

    private void initView() {
        toolbar.setTitle("提交订单");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        itemName.setText(mItemName);
        tvPhone.setText(mPhone);
        tvOrdertime.setText(mOrderTime);
    }


    public void sumbitOrder(View view) {
        if (user == null) {
            MaterialDialog mMaterialDialog = new MaterialDialog(this)
                    .setTitle(R.string.dialog_hint)
                    .setMessage(R.string.to_login)
                    .setCanceledOnTouchOutside(true)
                    .setPositiveButton(R.string.OK, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(AddOrderActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });

            mMaterialDialog.show();
            return;
        }

        ShopItem shop = new ShopItem();
        shop.setObjectId(mShopId);
        final OrderItem order = new OrderItem();
        order.setContactPhone(mPhone);
        order.setCustomer(user);
        order.setNumPeople(mPeopleNum);
        order.setOrderTime(mOrderTime);
        order.setRestaurant(shop);
        order.save(this, new SaveListener() {

            @Override
            public void onSuccess() {
                ToastUtil.show(AddOrderActivity.this, "add success");
                Intent sumbitIntent = new Intent(AddOrderActivity.this, MainActivity.class);
                sumbitIntent.putExtra("flag", MainActivity.DINGDAN);
                startActivity(sumbitIntent);
            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });
    }

    @OnClick({R.id.tv_ordertime, R.id.tv_bind_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_ordertime:
                break;
            case R.id.tv_bind_phone:
                break;
        }
    }
}
