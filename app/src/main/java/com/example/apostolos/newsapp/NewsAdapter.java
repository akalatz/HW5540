package com.example.apostolos.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Apostolos on 6/26/2017.
 */

public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.AdapterViewHolder> {

    private ArrayList<NewsItems> mNewsData;
    private final NewsAdapterOnClickHandler mClickHandler;

    public interface NewsAdapterOnClickHandler {
        void onClick(String url);
    }

    public NewsAdapter(NewsAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public final TextView title;
        public final TextView time;
        public final TextView description;



        public AdapterViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.tv_title);
            description = (TextView) view.findViewById(R.id.tv_description);
            time = (TextView) view.findViewById(R.id.tv_time);
        }
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mNewsData.get(adapterPosition).getUrl());
        }

    }


    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_items;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder adapterViewHolder, int position) {
        adapterViewHolder.title.setText(mNewsData.get(position).getTitle());
        adapterViewHolder.time.setText(mNewsData.get(position).getAuthor() + "    " + mNewsData.get(position).getPublishedAt());
        adapterViewHolder.description.setText(mNewsData.get(position).getDescription());

    }


    @Override
    public int getItemCount() {
        if (null == mNewsData) return 0;
        return mNewsData.size();
    }

    public void setNewsData(ArrayList<NewsItems> newsData) {
        mNewsData = newsData;
        notifyDataSetChanged();
    }

}
