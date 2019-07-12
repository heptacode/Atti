package app.atti.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.atti.Object.QNA_Comment;
import app.atti.R;

public class QNA_Comment_RecyclerAdapter extends RecyclerView.Adapter<QNA_Comment_RecyclerAdapter.ViewHolder>{
    ArrayList<QNA_Comment> items;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView comment, name, date;
        ImageView profile,korean;

        public ViewHolder(View itemView) {
            super(itemView);
            profile=itemView.findViewById(R.id.qna_dt_comment_img_profile);
            korean =itemView.findViewById(R.id.qna_dt_comment_img_korean);
            comment=itemView.findViewById(R.id.qna_dt_comment_tv_comment);
            name=itemView.findViewById(R.id.qna_dt_comment_tv_name);
            date=itemView.findViewById(R.id.qna_dt_comment_tv_date);

        }
    }

    public QNA_Comment_RecyclerAdapter(ArrayList<QNA_Comment> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public QNA_Comment_RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.qna_rcv_listitem, viewGroup, false);
        QNA_Comment_RecyclerAdapter.ViewHolder vh = new QNA_Comment_RecyclerAdapter.ViewHolder(v);

        return vh;
    }
    @Override
    public void onBindViewHolder(final @NonNull QNA_Comment_RecyclerAdapter.ViewHolder vh, int i) {
        if(items.get(i).isKorean()){
            vh.korean.setImageResource(R.drawable.ic_korean_flag);
        }else{
            vh.korean.setImageResource(R.drawable.ic_foreigner_flag);
        }
        vh.name.setText(items.get(i).getName());
        vh.date.setText(items.get(i).getDate());
        vh.comment.setText(items.get(i).getComment());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
