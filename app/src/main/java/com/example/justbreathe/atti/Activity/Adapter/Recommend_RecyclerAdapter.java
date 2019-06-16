package com.example.justbreathe.atti.Activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.justbreathe.atti.Activity.Activities.Recommend.Recommend_Detail_Acitivty;
import com.example.justbreathe.atti.Activity.Object.RecAC_list;
import com.example.justbreathe.atti.R;

import java.util.ArrayList;

public class Recommend_RecyclerAdapter extends RecyclerView.Adapter<Recommend_RecyclerAdapter.ViewHolder> {

    ArrayList<RecAC_list> items;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView location;
        TextView day;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            image = itemView.findViewById(R.id.recom_rec_img);
            title = itemView.findViewById(R.id.recom_rec_title);
            location = itemView.findViewById(R.id.recom_rec_location);
            day = itemView.findViewById(R.id.recom_rec_day);
        }
    }

    public Recommend_RecyclerAdapter(ArrayList<RecAC_list> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_recommend_listitem, viewGroup, false);
        Recommend_RecyclerAdapter.ViewHolder vh = new Recommend_RecyclerAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh,final int i) {
        vh.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itemIntent = new Intent(view.getContext(), Recommend_Detail_Acitivty.class);
                itemIntent.putExtra("PageNum",i+1);
                view.getContext().startActivity(itemIntent);
                //세부사항 화면 넘어가는거
            }
        });
        String str_title = items.get(i).getTitle();
        String str_url = items.get(i).getUrl();
        String str_loc = items.get(i).getLocation();
        String str_day = items.get(i).getDay();
        vh.title.setText(str_title);
        vh.day.setText(str_day);
        vh.location.setText(str_loc);

        Glide.with(vh.itemView.getContext()).load(str_url).into(vh.image);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
