package com.jscheng.rssreader.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jscheng.rssreader.R;
import com.jscheng.rssreader.model.RssInfo;
import com.jscheng.rssreader.ui.activity.WebActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 16-7-24.
 */
public class RssAdapter extends RecyclerView.Adapter {
    List<RssInfo> rssInfos;
    Context context;
    public RssAdapter(Context context) {
        this.context = context;
        rssInfos = new ArrayList<RssInfo>();
    }

    public void setRssInfos(List<RssInfo> list) {
        rssInfos = list;
        notifyDataSetChanged();
    }

    @Override
    public RssViewholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rss_item, viewGroup, false);
        RssViewholder viewholder = new RssViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RssInfo rssInfo = rssInfos.get(position);
        RssViewholder rssViewholder = (RssViewholder)holder;
        rssViewholder.titleView.setText(rssInfo.getTitle());
        rssViewholder.descriptionView.setText(rssInfo.getDescription());
        String imageUrl = rssInfo.getImg();
        if (TextUtils.isEmpty(imageUrl)) {
            rssViewholder.imageView.setVisibility(View.GONE);
        } else {
            rssViewholder.imageView.setVisibility(View.VISIBLE);
            //int height = ((int)context.getResources().getDimension(R.dimen.item_image_height)) % 1000;
            //Picasso.with(context).load(imageUrl).resize(height,300).centerCrop().into(rssViewholder.imageView);
            Picasso.with(context).load(imageUrl).fit().centerCrop().into(rssViewholder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return rssInfos.size();
    }

    private class RssViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public LinearLayout layout;
        public TextView titleView;
        public TextView descriptionView;
        public ImageView imageView;
        public RssViewholder(View itemView) {
            super(itemView);
            layout = (LinearLayout)itemView.findViewById(R.id.item_layout);
            titleView = (TextView) itemView.findViewById(R.id.item_title);
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
            descriptionView = (TextView)itemView.findViewById(R.id.item_desc);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("RssInfo", rssInfos.get(getAdapterPosition()));
            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.setClass(context, WebActivity.class);
            context.startActivity(intent);
        }
    }
}