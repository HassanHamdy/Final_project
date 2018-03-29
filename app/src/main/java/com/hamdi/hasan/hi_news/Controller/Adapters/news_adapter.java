package com.hamdi.hasan.hi_news.Controller.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hamdi.hasan.hi_news.Model.articles;
import com.hamdi.hasan.hi_news.R;
import com.hamdi.hasan.hi_news.View.FavouriteActivity;
import com.hamdi.hasan.hi_news.View.MainActivity;
import com.hamdi.hasan.hi_news.View.fragment.DetailsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class news_adapter extends RecyclerView.Adapter<news_adapter.news_viewHolder>{

    private ArrayList<articles> Data;
    private Context context;

    public news_adapter(Context cont, ArrayList<articles> data){
        Data = data;
        context = cont;
    }

    @Override
    public news_viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new news_viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final news_viewHolder holder, int position) {
        Picasso.with(context).load(Data.get(holder.getAdapterPosition()).getUrlToImage())
                .into(holder.newsImage);
        holder.newsTitle.setText(Data.get(holder.getAdapterPosition()).getTitle());
        holder.newsPublishDate.setText(Data.get(holder.getAdapterPosition()).getPublishedAt());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("newsDetails",  Data.get(holder.getAdapterPosition()));
                Fragment fragment = new DetailsFragment();
                fragment.setArguments(bundle);
                if(context.getClass().getSimpleName().equals("MainActivity")){
                    ((MainActivity)context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .addToBackStack(null).commit();
                }else {
                    context.startActivity(new Intent(context,MainActivity.class)
                            .putExtra("frag", "Detail")
                    .putExtra("title", Data.get(holder.getAdapterPosition()).getTitle())
                    .putExtra("image",Data.get(holder.getAdapterPosition()).getUrlToImage())
                    .putExtra("publishAt", Data.get(holder.getAdapterPosition()).getPublishedAt())
                    .putExtra("source_Name",Data.get(holder.getAdapterPosition()).getSource().getName())
                    .putExtra("description",Data.get(holder.getAdapterPosition()).getDescription()));

                    ((FavouriteActivity)context).finish();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    class news_viewHolder extends RecyclerView.ViewHolder{

        ImageView newsImage;
        TextView newsTitle, newsPublishDate;
        ConstraintLayout constraintLayout;

        public news_viewHolder(View itemView) {
            super(itemView);

            newsImage = itemView.findViewById(R.id.img_news);
            newsTitle = itemView.findViewById(R.id.txt_news_title);
            //TODO: parse Date ro match known date
            newsPublishDate = itemView.findViewById(R.id.txt_publish_date);
            constraintLayout = itemView.findViewById(R.id.constraint_layout);
        }
    }
}
