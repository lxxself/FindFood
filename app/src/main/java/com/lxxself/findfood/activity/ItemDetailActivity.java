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

import com.lxxself.findfood.R;
import com.lxxself.findfood.model.Comments;
import com.lxxself.findfood.model.ShopItem;
import com.lxxself.findfood.util.ToastUtil;
import com.lxxself.findfood.widget.RatingBar;
import com.socks.library.KLog;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import me.gujun.android.taggroup.TagGroup;

import static com.lxxself.findfood.util.AppUtil.loadImage;

public class ItemDetailActivity extends AppCompatActivity {

    @Bind(R.id.shop_main_image)
    ImageView shopMainImage;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.add_order)
    FloatingActionButton addOrder;
    @Bind(R.id.tag_group)
    TagGroup tagGroup;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.ratingbar)
    RatingBar ratingbar;
    @Bind(R.id.tv_pingjia)
    TextView tvPingjia;
    @Bind(R.id.iv_more_pj)
    ImageView ivMorePj;
    @Bind(R.id.CoordinatorLayout)
    android.support.design.widget.CoordinatorLayout CoordinatorLayout;
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.layout_comment)
    View commentLayout;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.iv_comment1)
    ImageView ivComment1;
    @Bind(R.id.iv_comment2)
    ImageView ivComment2;
    @Bind(R.id.iv_comment3)
    ImageView ivComment3;
    @Bind(R.id.item_comment)
    LinearLayout itemComment;
    @Bind(R.id.comment_rating)
    RatingBar ratingbarComment;

    private String phoneNum = "88209803";
    private ShopItem shopItem = new ShopItem();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);
        initView();

        Intent getIntent = getIntent();
        if (getIntent != null) {
            shopItem = (ShopItem) getIntent.getSerializableExtra("shopItem");
            setView();
        }


    }

    private void setView() {
        collapsingToolbar.setTitle(shopItem.getName());

        String[] tags = shopItem.getTags().split(",");
        tagGroup.setTags(Arrays.asList(tags));

        tvAddress.setText(shopItem.getAddress());
        tvPhone.setText(shopItem.getPhone());
        tvPrice.setText(getString(R.string.per_yuan, shopItem.getPrice()));
        loadImage(ItemDetailActivity.this, shopItem.getPicPath(), shopMainImage);
        setComment();
    }


    private void setComment() {
        BmobQuery<Comments> query = new BmobQuery<>();
        query.setLimit(1);
        query.order("createdAt");
        ShopItem item = new ShopItem();
        item.setObjectId(shopItem.getObjectId());
        query.addWhereEqualTo("restaurant", item);
        query.include("user");
        query.findObjects(ItemDetailActivity.this, new FindListener<Comments>() {
            @Override
            public void onSuccess(List<Comments> list) {
                KLog.d(list.toString());
                if (list.size() != 0) {
                    commentLayout.setVisibility(View.VISIBLE);
                    Comments comment = list.get(0);
                    loadImage(ItemDetailActivity.this, comment.getUser().getAvatar(), ivAvatar);
                    ratingbarComment.setStar(comment.getRatingNum());
                    tvUserName.setText(comment.getUser().getUsername());
                    tvContent.setText(comment.getContent());
                } else {
                    commentLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError(int i, String s) {
                ToastUtil.show(ItemDetailActivity.this,s);
            }
        });
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void openMap() {
        Intent mapIntent = new Intent(this, GDMapActivity.class);
        float[] point = {shopItem.getOriginal_latitude(), shopItem.getOriginal_longitude()};
        mapIntent.putExtra("point", point);
        startActivity(mapIntent);
    }

    private void callPhone() {
        String uriString = "tel:" + phoneNum;
        Uri uri = Uri.parse(uriString);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(callIntent);
    }

    private void addOrder() {
        Intent addOrderIntent = new Intent(this, AddOrderActivity.class);
        addOrderIntent.putExtra("name", shopItem.getName());
        addOrderIntent.putExtra("shopId", shopItem.getObjectId());
        startActivity(addOrderIntent);
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

    @OnClick({R.id.add_order, R.id.tv_address, R.id.tv_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_order:
                addOrder();
                break;
            case R.id.tv_address:
                openMap();
                break;
            case R.id.tv_phone:
                callPhone();
                break;
        }
    }
}
