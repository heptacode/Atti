package app.atti.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import app.atti.Object.Chat;
import app.atti.R;

public class Chatting_RecyclerAdapter extends RecyclerView.Adapter<Chatting_RecyclerAdapter.ViewHolder> {
    ArrayList<Chat> items;
    String myname;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, timeright, timeleft, message;
        LinearLayout LL;
        ImageView pro_img;
        View view_onimg;

        public ViewHolder(View itemView) {
            super(itemView);
            pro_img = itemView.findViewById(R.id.chat_item_img_profile);
            view_onimg = itemView.findViewById(R.id.chat_item_img_profile_topview);
            LL = itemView.findViewById(R.id.chat_item_LL);
            name = itemView.findViewById(R.id.chat_item_tv_name);
            timeleft = itemView.findViewById(R.id.chat_item_tv_timeleft);
            timeright = itemView.findViewById(R.id.chat_item_tv_timeright);
            message = itemView.findViewById(R.id.chat_item_tv_message);
        }
    }

    public Chatting_RecyclerAdapter(ArrayList<Chat> items, String myname) {
        this.items = items;
        this.myname = myname;

    }


    @NonNull
    @Override
    public Chatting_RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatting_listitem, viewGroup, false);
        Chatting_RecyclerAdapter.ViewHolder vh = new Chatting_RecyclerAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final @NonNull Chatting_RecyclerAdapter.ViewHolder vh, int i) {
        Chat chat = items.get(i);

        if (i > 0) { //메시지 같은사람이 보냈을때 사람이름 중복처리
            if (chat.getSender().equals(items.get(i - 1).getSender())) {//같은사람이면
                vh.name.setVisibility(View.GONE);
                vh.view_onimg.setVisibility(View.GONE);
                vh.pro_img.setVisibility(View.INVISIBLE);
                if (items.get(i).getDate().equals(items.get(i-1).getDate()) && items.get(i).getTimestamp().substring(0, chat.getTimestamp().length() - 2).equals(items.get(i-1).getTimestamp().substring(0, chat.getTimestamp().length() - 2)))
                    items.get(i - 1).setPrev(true);
                else
                    items.get(i - 1).setPrev(false);
            } else {//다른사람이면
                vh.view_onimg.setVisibility(View.VISIBLE);
                items.get(i - 1).setPrev(false);
                vh.pro_img.setVisibility(View.VISIBLE);
                vh.name.setVisibility(View.VISIBLE);
            }
        } else {//첫번쨰 항목이면
            items.get(i).setPrev(false);
            vh.view_onimg.setVisibility(View.VISIBLE);
            vh.pro_img.setVisibility(View.VISIBLE);
            vh.name.setVisibility(View.VISIBLE);
        }


        if (chat.getSender().equals(myname)) { //내가 보냈나?
            vh.name.setVisibility(View.GONE);//내이름은 안떠도 댐
            vh.pro_img.setVisibility(View.GONE);
            vh.view_onimg.setVisibility(View.GONE);
            vh.message.setBackgroundResource(R.drawable.chat_my_background);
            vh.LL.setGravity(Gravity.RIGHT);//오른쪽에 붙임
            vh.timeleft.setVisibility(View.VISIBLE);
            vh.timeright.setVisibility(View.GONE);
            vh.timeleft.setText(chat.getTimestamp().substring(0, chat.getTimestamp().length() - 2));
        } else {
            vh.message.setBackgroundResource(R.drawable.prac_chat_background);
            vh.LL.setGravity(Gravity.LEFT);//왼쪽에 붙임
            vh.timeleft.setVisibility(View.GONE);
            vh.timeright.setVisibility(View.VISIBLE);
            vh.timeright.setText(chat.getTimestamp().substring(0, chat.getTimestamp().length() - 2));
        }
//        if (items.size() > 1) {
//            if (items.get(items.size() - 1).getDate().equals(items.get(items.size() - 2).getDate())
//                    && items.get(items.size() - 1).getTimestamp().equals(items.get(items.size() - 2).getTimestamp())
//                    && items.get(items.size() - 1).getSender().equals(items.get(items.size() - 2).getSender())) {//같은사람, 같은 시간, 같은 날짜
//            }
//        }
        if (items.get(i).isPrev()) {
            vh.timeleft.setVisibility(View.GONE);
            vh.timeright.setVisibility(View.GONE);
        }

        vh.message.setText(chat.getMessage());

        vh.name.setText(chat.getSender());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
