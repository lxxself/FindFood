package com.lxxself.findfood.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.PayListener;
import com.lxxself.findfood.R;
import com.socks.library.KLog;

import java.awt.font.TextAttribute;
import java.util.ArrayList;

public class PayActivity extends AppCompatActivity {

    private TextView tvPayMoney;
    private Float money;
    public static final String TAG = "PayActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        tvPayMoney = (TextView) findViewById(R.id.pay_money);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        if (getIntent != null) {
            money = getIntent.getFloatExtra("totalPrice",0);
            tvPayMoney.setText(money+"元");
        }

    }

    public void payIt(View view) {
        new BmobPay(PayActivity.this).pay(0.01, "某商品", new PayListener() {
            @Override
            public void orderId(String s) {
                KLog.d("orderId",s);
            }

            @Override
            public void succeed() {
                KLog.d("payIt", "succeed");
                Toast.makeText(PayActivity.this,"支付成功",Toast.LENGTH_LONG).show();
                Intent backItemDetail = new Intent(PayActivity.this, ItemDetailActivity.class);
                startActivity(backItemDetail);
            }

            @Override
            public void fail(int i, String s) {
                KLog.d("payIt","fail");
                Toast.makeText(PayActivity.this,"支付失败",Toast.LENGTH_LONG).show();
            }

            @Override
            public void unknow() {
                KLog.d("payIt","unknow");
            }
        });
    }
}
