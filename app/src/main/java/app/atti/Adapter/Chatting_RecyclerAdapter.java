package app.atti.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        public ViewHolder(View itemView) {
            super(itemView);
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
            if (chat.getSender().equals(items.get(i - 1).getSender())) {
                vh.name.setVisibility(View.GONE);
            } else {
                vh.name.setVisibility(View.VISIBLE);
            }
        } else {
            vh.name.setVisibility(View.VISIBLE);
        }


        if (chat.getSender().equals(myname)) { //내가 보냈나?
            vh.name.setVisibility(View.GONE);//내이름은 안떠도 댐

            vh.LL.setGravity(Gravity.RIGHT);//오른쪽에 붙임
            vh.timeleft.setVisibility(View.VISIBLE);
            vh.timeright.setVisibility(View.GONE);
            vh.timeleft.setText(chat.getTimestamp());
        } else {
            vh.LL.setGravity(Gravity.LEFT);//왼쪽에 붙임
            vh.timeleft.setVisibility(View.GONE);
            vh.timeright.setVisibility(View.VISIBLE);
            vh.timeright.setText(chat.getTimestamp());
        }
//        if (items.size() > 1) {
//            if (items.get(items.size() - 1).getDate().equals(items.get(items.size() - 2).getDate())
//                    && items.get(items.size() - 1).getTimestamp().equals(items.get(items.size() - 2).getTimestamp())
//                    && items.get(items.size() - 1).getSender().equals(items.get(items.size() - 2).getSender())) {//같은사람, 같은 시간, 같은 날짜
//            }
//        }

        vh.message.setText(chat.getMessage());
        vh.name.setText(chat.getSender());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
