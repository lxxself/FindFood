package com.lxxself.findfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxxself.findfood.R;

public class AddOrderActivity extends AppCompatActivity {

    private TextView tvItemName;
    private TextView tvItemPrice;
    private LinearLayout mountlinear;
    private EditText etAmount;
    private TextView tvTotalCount;
    private String itemName;
    private Float itemPrice;
    private Float totalPrice;
    private int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        this.tvTotalCount = (TextView) findViewById(R.id.totalCount);
        this.mountlinear = (LinearLayout) findViewById(R.id.mount_linear);
        this.etAmount = (EditText) findViewById(R.id.amount);
        this.tvItemPrice = (TextView) findViewById(R.id.item_price);
        this.tvItemName = (TextView) findViewById(R.id.item_name);

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
            itemName = getIntent.getStringExtra("name");
            itemPrice = getIntent.getFloatExtra("price", 0);
            tvItemName.setText(itemName);
            tvItemPrice.setText("￥" + itemPrice);
            totalPrice = itemPrice;
            tvTotalCount.setText(itemPrice+"元");
        }
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAmount.getText().toString().equals("")) {
                    num = 0;
                } else {
                    num = Integer.parseInt(etAmount.getText().toString());
                }
                totalPrice = num * itemPrice;
                tvTotalCount.setText(totalPrice+"元");
            }
        });
    }

    public void minusNum(View view) {
        if (etAmount.getText().toString().equals("")||etAmount.getText().toString().equals("0")) {
            num = 0;
        } else {
            num = Integer.parseInt(etAmount.getText().toString())-1;
        }
        etAmount.setText(num+"");
        totalPrice = num * itemPrice;
        tvTotalCount.setText(totalPrice+"元");
    }
    public void addNum(View view) {
        if (etAmount.getText().toString().equals("")) {
            num = 0;
        } else {
            num = Integer.parseInt(etAmount.getText().toString())+1;
        }
        etAmount.setText(num+"");
        totalPrice = num * itemPrice;
        tvTotalCount.setText(totalPrice+"元");
    }

    public void sumbitOrder(View view) {
        Intent sumbitIntent = new Intent(this, PayActivity.class);
        sumbitIntent.putExtra("totalPrice", totalPrice);
        startActivity(sumbitIntent);
    }
}
