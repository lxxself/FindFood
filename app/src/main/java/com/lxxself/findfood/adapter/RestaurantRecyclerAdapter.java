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

import com.lxxself.findfood.R;
import com.lxxself.findfood.model.RestaurantItem;

import java.util.ArrayList;
import java.util.List;

import me.kaede.tagview.Tag;
import me.kaede.tagview.TagView;

/**
 * Created by Administrator on 2015/10/15.
 */
public class RestaurantRecyclerAdapter extends RecyclerView.Adapter<RestaurantRecyclerAdapter.VersionViewHolder>{
    List<RestaurantItem> versionModels;
    List<Integer> addedList = new ArrayList<Integer>();
    public static List<String> titleList = new ArrayList<String>();
    public static List<String> priceList = new ArrayList<String>();
    private static final String[] tempTag = {"下沙","美味","好吃","环境好"};
    Context context;
    OnItemClickListener clickListener;


    public void setHomeActivitiesList(Context context) {
        String[] listArray = context.getResources().getStringArray(R.array.home_activities);
        String[] subTitleArray = context.getResources().getStringArray(R.array.home_activities_subtitle);
        for (int i = 0; i < listArray.length; ++i) {
            titleList.add(listArray[i]);
            priceList.add(subTitleArray[i]);
        }
    }

    public RestaurantRecyclerAdapter(Context context) {
        this.context = context;
        setHomeActivitiesList(context);
    }


    public RestaurantRecyclerAdapter(List<RestaurantItem> versionModels) {
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
        versionViewHolder.ratingBar.setStar((int) versionModels.get(i).getRatingNum());
        versionViewHolder.distance.setText(versionModels.get(i).getDistance() + "km");
        if (BitmapFactory.decodeFile(versionModels.get(i).getPicPath()) != null) {
            versionViewHolder.imageView.setImageBitmap(BitmapFactory.decodeFile(versionModels.get(i).getPicPath()));
        }
        Log.d("onBindViewHolder", versionViewHolder.tagView.getTags().size() + "");
        versionViewHolder.tagView.setTags(tempTag);


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
        com.hedgehog.ratingbar.RatingBar ratingBar;
        ImageView imageView;
        me.kaede.tagview.TagView tagView;

        public VersionViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
            title = (TextView) itemView.findViewById(R.id.listitem_name);
            price = (TextView) itemView.findViewById(R.id.listitem_price);
            distance = (TextView) itemView.findViewById(R.id.distance);
            ratingBar = (com.hedgehog.ratingbar.RatingBar) itemView.findViewById(R.id.ratingbar);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
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
