package com.lxxself.findfood.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bmob.pay.tool.BmobPay;
import com.lxxself.findfood.R;
import com.lxxself.findfood.model.ShopItem;
import com.squareup.picasso.Picasso;

import io.techery.properratingbar.ProperRatingBar;

public class ItemDetailActivity extends AppCompatActivity {

    private String phoneNum = "88209803";
    private ShopItem shopItem = new ShopItem();
    private ImageView shopMainImage;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingtoolbar;
    private android.support.design.widget.AppBarLayout appbar;
    private ImageView ivlocal;
    private android.widget.TextView tvaddress;
    private ImageView ivcall;
    private android.widget.TextView tvphone;
    private ImageView ivexpense;
    private android.widget.TextView tvprice;
    private ImageView ivstarNum;
    private io.techery.properratingbar.ProperRatingBar ratingbar;
    private ImageView ivmorepj;
    private android.widget.TextView tvpingjia;
    private ImageView ivavatar;
    private android.widget.TextView tvusername;
    private android.widget.TextView textView2;
    private android.widget.LinearLayout pjlayout;
    private android.support.design.widget.CoordinatorLayout CoordinatorLayout;
    private FloatingActionButton addOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        initView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
            shopItem = (ShopItem) getIntent.getSerializableExtra("shopItem");
            setView();
        }


    }

    private void setView() {
        collapsingtoolbar.setTitle(shopItem.getName());
        tvaddress.setText(shopItem.getAddress());
        tvphone.setText(shopItem.getPhone());
        tvprice.setText("￥" + shopItem.getPrice() + "/人");
        Picasso.with(ItemDetailActivity.this).load(shopItem.getPicPath())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(shopMainImage);

    }

    private void initView() {
        this.CoordinatorLayout = (android.support.design.widget.CoordinatorLayout) findViewById(R.id.CoordinatorLayout);
        this.pjlayout = (LinearLayout) findViewById(R.id.pj_layout);
        this.textView2 = (TextView) findViewById(R.id.textView2);
        this.tvusername = (TextView) findViewById(R.id.tv_user_name);
        this.ivavatar = (ImageView) findViewById(R.id.iv_avatar);
        this.tvpingjia = (TextView) findViewById(R.id.tv_pingjia);
        this.ivmorepj = (ImageView) findViewById(R.id.iv_more_pj);
        this.ratingbar = (ProperRatingBar) findViewById(R.id.ratingbar);
        this.ivstarNum = (ImageView) findViewById(R.id.iv_starNum);
        this.tvprice = (TextView) findViewById(R.id.tv_price);
        this.ivexpense = (ImageView) findViewById(R.id.iv_expense);
        this.tvphone = (TextView) findViewById(R.id.tv_phone);
        this.ivcall = (ImageView) findViewById(R.id.iv_call);
        this.tvaddress = (TextView) findViewById(R.id.tv_address);
        this.ivlocal = (ImageView) findViewById(R.id.iv_local);
        this.appbar = (AppBarLayout) findViewById(R.id.appbar);
        this.collapsingtoolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.shopMainImage = (ImageView) findViewById(R.id.shop_mian_image);
        this.addOrder = (FloatingActionButton) findViewById(R.id.add_order);
    }

    public void openMap(View view) {
        Intent mapIntent = new Intent(this, GDMapActivity.class);
        float[] point = {shopItem.getOriginal_latitude(), shopItem.getOriginal_longitude()};
        mapIntent.putExtra("point", point);
        startActivity(mapIntent);
    }

    public void callPhone(View view) {
        String uriString = "tel:" + phoneNum;
        Uri uri = Uri.parse(uriString);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(callIntent);
    }

    public void addOrder(View view) {
        Intent addOrder = new Intent(this, AddOrderActivity.class);
        addOrder.putExtra("name", shopItem.getName());
        addOrder.putExtra("price", shopItem.getPrice());
        startActivity(addOrder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
