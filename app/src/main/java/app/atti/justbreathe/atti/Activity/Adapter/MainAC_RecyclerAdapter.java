package app.atti.justbreathe.atti.Activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import app.atti.justbreathe.atti.Activity.Activities.Main.MainActivity_Detail;
import app.atti.justbreathe.atti.Activity.Object.MainAC_Post;
import app.atti.justbreathe.atti.R;

import java.util.ArrayList;

public class MainAC_RecyclerAdapter extends RecyclerView.Adapter<MainAC_RecyclerAdapter.ViewHolder> {
    ArrayList<MainAC_Post> items;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,content,date,name,more;
        LinearLayout like,LL_intent;
        ImageView korean;
        ImageView mainimg;
        public ViewHolder(View itemView) {
            super(itemView);
            LL_intent=itemView.findViewById(R.id.main_item_LL_intent);
            content = itemView.findViewById(R.id.main_item_tv_content);
            title = itemView.findViewById(R.id.main_item_tv_title);
            date = itemView.findViewById(R.id.main_item_tv_date);
            name = itemView.findViewById(R.id.main_item_tv_name);
            like = itemView.findViewById(R.id.main_item_LL_like);
            korean = itemView.findViewById(R.id.main_item_img_korean);
            mainimg= itemView.findViewById(R.id.main_item_img_mainimg);
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.LL_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = items.get(position).getID();
                Intent maindetail = new Intent(holder.itemView.getContext(), MainActivity_Detail.class);
                maindetail.putExtra("ID",ID);
                holder.itemView.getContext().startActivity(maindetail);
            }
        });


        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //좋아요 클릭
            }
        });
        String title = items.get(position).getTitle();
        String content = items.get(position).getContent();
        String date = items.get(position).getDate();
        String name = items.get(position).getWriter();
        int like = items.get(position).getLike();
        boolean korean = items.get(position).isKorean();
        String imgurl = items.get(position).getImage_url();
        holder.title.setText(title);
        holder.content.setText(content);
        holder.date.setText(date);
        holder.name.setText(name);
        Glide.with(holder.itemView.getContext()).load(imgurl).into(holder.mainimg);
        if(korean){
            holder.korean.setImageResource(R.drawable.ic_korean_flag);
        }else{
            //외국인용 국기
            holder.korean.setImageResource(R.drawable.ic_foreigner_flag);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
