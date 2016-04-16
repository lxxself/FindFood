package com.lxxself.findfood.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.lxxself.findfood.R;
import com.lxxself.findfood.model.ShopItem;
import com.lxxself.findfood.util.SPUtils;
import com.lxxself.findfood.widget.RatingBar;
import com.socks.library.KLog;
import com.squareup.picasso.Picasso;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Administrator on 2015/10/15.
 */
public class RestaurantRecyclerAdapter extends RecyclerView.Adapter<RestaurantRecyclerAdapter.ViewHolder> {
    List<ShopItem> versionModels;
    private static final String[] tempTag = {"下沙", "美味", "好吃", "环境好"};
    Context context;
    OnItemClickListener clickListener;


    public RestaurantRecyclerAdapter(Context context) {
        this.context = context;
    }

    public RestaurantRecyclerAdapter(Context context, List<ShopItem> versionModels) {
        this.context = context;
        this.versionModels = versionModels;
    }

    public void addAll(List<ShopItem> versionModels) {
        this.versionModels.addAll(versionModels);
        notifyDataSetChanged();
    }

    public RestaurantRecyclerAdapter(List<ShopItem> versionModels) {
        this.versionModels = versionModels;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_shop, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(i);
        viewHolder.listitemName.setText(versionModels.get(i).getName());
        viewHolder.listitemPrice.setText("￥" + versionModels.get(i).getPrice() + "/人");
        viewHolder.ratingbar.setStar((int) versionModels.get(i).getRatingNum());

        viewHolder.distance.setText(getDistance(versionModels.get(i).getOriginal_latitude(), versionModels.get(i).getOriginal_longitude()) + "km");
        if (versionModels.get(i).getPicPath() != null) {
            Picasso.with(context).load(versionModels.get(i).getPicPath())
                    .resize(150, 100).centerCrop()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
                    .into(viewHolder.ivPic);
        }

        KLog.d("onBindViewHolder", viewHolder.tagGroup.getTags().length + "");
        String[] tags = versionModels.get(i).getTags().split(",");
        if (tags.length > 6) {
            viewHolder.tagGroup.setTags(Arrays.copyOf(tags, 6));
        } else {
            viewHolder.tagGroup.setTags(tags);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClick(v, (Integer) v.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return versionModels == null ? 0 : versionModels.size();
    }


    private String getDistance(float original_latitude, float original_longitude) {
        float latitude = (float) SPUtils.getShareData(context, "latitude", original_latitude);
        float longitude = (float) SPUtils.getShareData(context, "longitude", original_longitude);
        LatLng startLatlng = new LatLng(latitude, longitude);
        LatLng endLatlng = new LatLng(original_latitude, original_longitude);
        float distance = AMapUtils.calculateLineDistance(startLatlng, endLatlng) / 1000;
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(1);
        format.setRoundingMode(RoundingMode.UP);
        return format.format(distance);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.clickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void itemClick(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_pic)
        ImageView ivPic;
        @Bind(R.id.listitem_name)
        TextView listitemName;
        @Bind(R.id.distance)
        TextView distance;
        @Bind(R.id.ratingbar)
        RatingBar ratingbar;
        @Bind(R.id.listitem_price)
        TextView listitemPrice;
        @Bind(R.id.tag_group)
        TagGroup tagGroup;
        @Bind(R.id.cardlist_item)
        CardView cardlistItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
