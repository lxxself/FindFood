package com.lxxself.findfood.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.lxxself.findfood.R;
import com.lxxself.findfood.fragment.DingdanFragment;
import com.lxxself.findfood.fragment.FaxianFragment;
import com.lxxself.findfood.fragment.ShouyeFragment;

import java.util.ArrayList;
import java.util.List;

import me.kaede.tagview.OnTagClickListener;
import me.kaede.tagview.TagView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public String TAG = "MainActivity";
    private Context mContext;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("觅食");
        
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.CoordinatorLayout);
        fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSituation(view);
//                Snackbar.make(coordinatorLayout, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        switchToShouye();


    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void showSituation(View v) {
        Log.d(TAG, "showCutPopupWindows ");
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.situation_pop_window, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
              ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        final TagView wuliTagGroup = (TagView) contentView.findViewById(R.id.wuli_tags);
        wuliTagGroup.addTags(new String[]{"十六街区商业街", "下午", "晴朗"});
        wuliTagGroup.setOnTagClickListener(new OnTagClickListener() {

            @Override
            public void onTagClick(me.kaede.tagview.Tag tag, int position) {
//                wuliTagGroup.defaultTags();

//                tag.tagSelect();
//                wuliTagGroup.drawTags();


//                Toast.makeText(MainActivity.this,tag.text,Toast.LENGTH_LONG).show();
            }
        });


        TagView renshuTagGroup = (TagView) contentView.findViewById(R.id.renshu_tags);
        renshuTagGroup.addTags(new String[]{"1-2人", "3-4人", "5-8人", "8人以上"});

        TagView tongbanTagGroup = (TagView) contentView.findViewById(R.id.tongban_tags);
        tongbanTagGroup.addTags(new String[]{"朋友", "家人", "伴侣", "同事"});

        TagView mudiTagGroup = (TagView) contentView.findViewById(R.id.mudi_tags);
        mudiTagGroup.addTags(new String[]{"聚会", "果腹", "宴请", "休闲"});

        TagView luojiTagGroup = (TagView) contentView.findViewById(R.id.luoji_tags);
        luojiTagGroup.addTags(new String[]{"开心", "兴奋", "肚子饿", "玩乐", "肚子饿", "玩乐", "肚子饿",
                "玩乐", "肚子饿", "玩乐", "肚子饿", "玩乐", "肚子饿", "玩乐", "肚子饿", "玩乐", "肚子饿", "玩乐"});
        me.kaede.tagview.Tag luojitag = new me.kaede.tagview.Tag("+添加标签");

        luojiTagGroup.addTag(luojitag);

        popupWindow.setAnimationStyle(R.style.popwin_anim_style);

//        popupWindow.setTouchable(false);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }

        });
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                android.R.drawable.screen_background_light));
        popupWindow.showAtLocation((View) fab.getParent(), Gravity.BOTTOM, 0, 100);

//        popupWindow.showAsDropDown(fab,-100,-(fab.getHeight()+300));

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.shouye) {
           switchToShouye();
        } else if (id == R.id.faxian) {
            switchToFaxian();
        } else if (id == R.id.dingdan) {
            switchToDingdan();
        } else if (id == R.id.shezhi) {
            switchToShezhi();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchToShouye() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ShouyeFragment()).commit();
        toolbar.setTitle("首页");
    }
    private void switchToFaxian() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FaxianFragment()).commit();
        toolbar.setTitle("发现");
    }
    private void switchToDingdan() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new DingdanFragment()).commit();
        toolbar.setTitle("订单");
    }
    private void switchToShezhi() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FaxianFragment()).commit();
        toolbar.setTitle("设置");
    }
}
