package com.lxxself.findfood.adapter;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.squareup.picasso.Picasso;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import me.kaede.tagview.TagView;

/**
 * Created by Administrator on 2015/10/15.
 */
public class RestaurantRecyclerAdapter extends RecyclerView.Adapter<RestaurantRecyclerAdapter.VersionViewHolder>{
    List<ShopItem> versionModels;
    private static final String[] tempTag = {"下沙","美味","好吃","环境好"};
    Context context;
    OnItemClickListener clickListener;


    public RestaurantRecyclerAdapter(Context context) {
        this.context = context;
    }
    public RestaurantRecyclerAdapter(Context context,List<ShopItem> versionModels) {
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
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerlist_item, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {
        Log.d("onBindViewHolder", versionViewHolder.title.getText().toString() + "--" + i);
        versionViewHolder.title.setText(versionModels.get(i).getName());
        versionViewHolder.price.setText("￥" + versionModels.get(i).getPrice() + "/人");
        versionViewHolder.ratingBar.setRating((int) versionModels.get(i).getRatingNum());

        versionViewHolder.distance.setText(getDistance(versionModels.get(i).getOriginal_latitude(), versionModels.get(i).getOriginal_longitude()) + "km");
        if (versionModels.get(i).getPicPath() != null) {
            Picasso.with(context).load(versionModels.get(i).getPicPath())
                    .resize(150, 100).centerCrop()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
                    .into(versionViewHolder.imageView);
        }

        Log.d("onBindViewHolder", versionViewHolder.tagView.getTags().size() + "");
        versionViewHolder.tagView.setTags(versionModels.get(i).getTags().split(","));


    }

    private String getDistance(float original_latitude, float original_longitude) {
        float latitude= (float)SPUtils.get(context, "localtionInfo", "latitude", original_latitude);
        float longitude = (float)SPUtils.get(context, "localtionInfo", "longitude", original_longitude);
        LatLng startLatlng = new LatLng(latitude,longitude);
        LatLng endLatlng = new LatLng(original_latitude,original_longitude);
        float distance = AMapUtils.calculateLineDistance(startLatlng, endLatlng)/1000;
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(1);
        format.setRoundingMode(RoundingMode.UP);
        return format.format(distance);
    }

    @Override
    public int getItemCount() {
        return versionModels == null ? 0 : versionModels.size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardItemLayout;
        TextView title;
        TextView distance;
        TextView price;
        io.techery.properratingbar.ProperRatingBar ratingBar;
        ImageView imageView;
        me.kaede.tagview.TagView tagView;

        public VersionViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
            title = (TextView) itemView.findViewById(R.id.listitem_name);
            price = (TextView) itemView.findViewById(R.id.listitem_price);
            distance = (TextView) itemView.findViewById(R.id.distance);
            ratingBar = ( io.techery.properratingbar.ProperRatingBar) itemView.findViewById(R.id.ratingbar);
            imageView = (ImageView) itemView.findViewById(R.id.iv_pic);
            tagView = (TagView) itemView.findViewById(R.id.sometagView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
