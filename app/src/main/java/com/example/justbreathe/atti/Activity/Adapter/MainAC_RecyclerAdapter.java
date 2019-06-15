package com.example.justbreathe.atti.Activity.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.justbreathe.atti.Activity.Object.MainAC_Post;
import com.example.justbreathe.atti.R;

import java.util.ArrayList;

public class MainAC_RecyclerAdapter extends RecyclerView.Adapter<MainAC_RecyclerAdapter.ViewHolder> {
    ArrayList<MainAC_Post> items;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        TextView title;
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            content = itemView.findViewById(R.id.Recycler_content);
            title = itemView.findViewById(R.id.Recylcer_title);
        }
    }

    public MainAC_RecyclerAdapter(ArrayList<MainAC_Post> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MainAC_RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.recycler_view_item,viewGroup,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //아이템 클릭
            }
        });
        String title = items.get(position).getTitle();
        String content = items.get(position).getContent();
        holder.title.setText(title);
        holder.content.setText(content);
        //추가중
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
