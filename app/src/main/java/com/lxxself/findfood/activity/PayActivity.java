package com.lxxself.findfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.PayListener;
import com.lxxself.findfood.R;
import com.lxxself.findfood.model.OrderItem;
import com.lxxself.findfood.util.ToastUtil;
import com.socks.library.KLog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.UpdateListener;

public class PayActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.et_pay_money)
    EditText etPayMoney;
    @Bind(R.id.tv_pay_money)
    TextView tvPayMoney;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    private float mPayMoney = 0;
    private OrderItem orderItem;
    private String mShopName;
    private String mOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);

        toolbar.setTitle("提交订单");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent getIntent = getIntent();
        orderItem = (OrderItem) getIntent.getSerializableExtra("orderItem");
        mShopName = orderItem.getRestaurant().getName();
        mOrderId = orderItem.getObjectId();

        tvShopName.setText(mShopName);
        etPayMoney.addTextChangedListener(mTextChangeListener);
    }

    private TextWatcher mTextChangeListener=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() == 0) {
                mPayMoney = 0;
            } else {
                mPayMoney = Float.parseFloat(s.toString());
            }

            tvPayMoney.setText(getString(R.string.yuan, mPayMoney));
        }
    };

    @OnClick(R.id.btn_confirm)
    public void onClick() {
        if (TextUtils.isEmpty(btnConfirm.getText().toString())) {
            ToastUtil.show(PayActivity.this, "请输入金额");
            return;
        }
//        comfirmPay();

        upDateOrder();
    }

    private void comfirmPay() {
        new BmobPay(PayActivity.this).pay(0.01, mShopName, new PayListener() {
            @Override
            public void orderId(String s) {
                KLog.d("orderId", s);
            }

            @Override
            public void succeed() {
                KLog.d("payIt", "succeed");
                ToastUtil.show(PayActivity.this, "支付成功");
                upDateOrder();
            }

            @Override
            public void fail(int i, String s) {
                KLog.d("payIt", "fail");
                ToastUtil.show(PayActivity.this, "支付失败");
            }

            @Override
            public void unknow() {
                KLog.d("payIt", "unknow");
            }
        });
    }

    private void upDateOrder() {
        KLog.d("upDateOrder");
        orderItem.setActualAmount(mPayMoney);
        orderItem.setAmount(mPayMoney);
        orderItem.setStatus(1);
        orderItem.update(PayActivity.this, new UpdateListener() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(PayActivity.this, ItemDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("shopItem", orderItem.getRestaurant());
                intent.putExtras(bundle);
                startActivity(intent);
                KLog.d("保存成功");
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                KLog.d("保存失败 "+s);
            }
        });
    }
}
