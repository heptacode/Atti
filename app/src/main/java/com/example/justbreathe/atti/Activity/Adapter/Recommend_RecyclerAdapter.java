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
import com.example.justbreathe.atti.Activity.Activities.Recommend.Recommend_Detail_Acitivty;
import com.example.justbreathe.atti.Activity.Object.RecAC_list;
import com.example.justbreathe.atti.R;

import java.util.ArrayList;

public class Recommend_RecyclerAdapter extends RecyclerView.Adapter<Recommend_RecyclerAdapter.ViewHolder> {

    ArrayList<RecAC_list> items;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            image = itemView.findViewById(R.id.recom_rec_img);
            title = itemView.findViewById(R.id.recom_rec_title);
        }
    }

    public Recommend_RecyclerAdapter(ArrayList<RecAC_list> items) {
        this.items = items;
        Log.e("No error", "Adapter created");
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_recommend_listitem, viewGroup, false);
        Recommend_RecyclerAdapter.ViewHolder vh = new Recommend_RecyclerAdapter.ViewHolder(v);
        Log.e("No error", "Inflating");
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
        vh.title.setText(str_title);
        Log.e("No error", str_url);
        Glide.with(vh.itemView.getContext()).load(str_url).into(vh.image);
        Log.e("Glide","Finish");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
