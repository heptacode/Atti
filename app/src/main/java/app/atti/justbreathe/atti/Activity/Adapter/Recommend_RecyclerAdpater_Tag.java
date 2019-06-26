package app.atti.justbreathe.atti.Activity.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.atti.justbreathe.atti.R;

import java.util.ArrayList;

public class Recommend_RecyclerAdpater_Tag extends RecyclerView.Adapter<Recommend_RecyclerAdpater_Tag.ViewHolder> {
    ArrayList<String> items;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textview;

        public ViewHolder(View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.tag_text);
        }
    }

    public Recommend_RecyclerAdpater_Tag(ArrayList<String> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public Recommend_RecyclerAdpater_Tag.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recommend_listitem_tag, viewGroup, false);
        Recommend_RecyclerAdpater_Tag.ViewHolder vh = new Recommend_RecyclerAdpater_Tag.ViewHolder(v);

        return vh;
    }
    @Override
    public void onBindViewHolder(final @NonNull Recommend_RecyclerAdpater_Tag.ViewHolder vh, int i) {
        vh.textview.setText("#"+items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
