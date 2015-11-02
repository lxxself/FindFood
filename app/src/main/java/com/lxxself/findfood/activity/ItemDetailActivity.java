package com.lxxself.findfood.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lxxself.findfood.R;
import com.lxxself.findfood.adapter.ViewPagerAdapter;
import com.lxxself.findfood.fragment.ListRestFragment;

public class ItemDetailActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private String locationName;
    private String phoneNum = "88209803";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("青木餐厅");
        collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.primary));

        ImageView ivImage = (ImageView)findViewById(R.id.ivImage);
        ivImage.setImageResource(R.drawable.qingmu);


    }

    public void openMap(View view) {
        Intent mapIntent = new Intent(this, GDMapActivity.class);
        startActivity(mapIntent);
    }

    public void callPhone(View view) {
        String uriString = "tel:" + phoneNum;
        Uri uri = Uri.parse(uriString);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(callIntent);
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
