package com.lxxself.findfood.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lxxself.findfood.R;
import com.lxxself.findfood.fragment.DingdanFragment;
import com.lxxself.findfood.fragment.FaxianFragment;
import com.lxxself.findfood.fragment.ShouyeFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import me.kaede.tagview.OnTagClickListener;
import me.kaede.tagview.Tag;
import me.kaede.tagview.TagView;



public class MainActivity extends NetLocationActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private Toolbar toolbar;
    private ImageView ivAvatar;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public String TAG = "MainActivity";
    private Context mContext;
    private FloatingActionButton fab;
    private SearchView searchView;
    private MenuItem mMenuItem;
    private SharedPreferences sp;
    private boolean isSigned;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        Bmob.initialize(this, "8bb928ff8b09ee63a01e4c72a3090825");
        checkSignState();

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
        setAvatar();

    }

    private void checkSignState() {
        BmobUser bmobUser = BmobUser.getCurrentUser(this);
        sp = getSharedPreferences("baisc", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(bmobUser != null){
            editor.putBoolean("isSigned", true);
        }else{
            editor.putBoolean("isSigned", false);
        }

    }

    private void setAvatar() {
        //不知原因，在xml添加app:headerLayout="@layout/nav_header_main" 无法添加点击事件
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ivAvatar = (ImageView) headerView.findViewById(R.id.profile_image);
        if (isSigned) {
//            ivAvatar.setImageBitmap();
        }
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignIntent = new Intent();
                if (isSigned) {

                } else {
                    toSignIntent.setClass(MainActivity.this, LoginActivity.class);
                }
                startActivity(toSignIntent);
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void showSituation(View v) {
        Log.d(TAG, "showCutPopupWindows ");
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.situation_pop_window, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
              ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        TextView location = (TextView) contentView.findViewById(R.id.tv_location);
        TextView currentTime = (TextView) contentView.findViewById(R.id.tv_time);
        TextView weather = (TextView) contentView.findViewById(R.id.tv_weather);
        location.setText(mLocationLatlngText);
        currentTime.setText(getCurrentTime());
        weather.setText(mTemperature+"  "+mWeather);
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
        Tag luojitag = new Tag("+添加标签");
        luojitag.setIsAddButton(true);
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
//        popupWindow.showAtLocation((View) fab, Gravity.NO_GRAVITY, 0, fab.getHeight());
        popupWindow.showAtLocation(fab,Gravity.BOTTOM,0,-fab.getHeight());
    }

    private String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String currentTime = format.format(date)+" ";
        Log.d(TAG, currentTime);
        String dayOfWeek = "";
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                dayOfWeek = "星期天";
                break;
            case 2:
                dayOfWeek = "星期一";
                break;
            case 3:
                dayOfWeek = "星期二";
                break;
            case 4:
                dayOfWeek = "星期三";
                break;
            case 5:
                dayOfWeek = "星期四";
                break;
            case 6:
                dayOfWeek = "星期五";
                break;
            case 7:
                dayOfWeek = "星期六";
                break;
        }
        currentTime = currentTime +"  "+dayOfWeek;
        return currentTime;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mMenuItem = menu.findItem(R.id.ab_search);
        searchView = (SearchView) MenuItemCompat.getActionView(mMenuItem);
        searchView.setQueryHint("输入餐厅名、地点");
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("onQueryTextSubmit",query);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ShouyeFragment(2, query)).commit();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ShouyeFragment(1,"")).commit();
        return false;
    }

    @Override
    public boolean onClose() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ShouyeFragment(0,"")).commit();
        return false;
    }

}
