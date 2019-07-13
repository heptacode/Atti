package app.atti.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.atti.Activities.Chatting.ChattingActivity;
import app.atti.Object.Chat_Lobby;
import app.atti.R;

public class Chatting_Lobby_RecyclerAdapter extends RecyclerView.Adapter<Chatting_Lobby_RecyclerAdapter.ViewHolder> {
    ArrayList<Chat_Lobby> items;
    String date;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView chatname;
        TextView chat;
        TextView time;
        LinearLayout LL;

        public ViewHolder(View itemView) {
            super(itemView);
            LL = itemView.findViewById(R.id.chatting_lobby_listitem_LL);
            chatname = itemView.findViewById(R.id.chattingLobby_listitem_tv_chatname);
            chat = itemView.findViewById(R.id.chattingLobby_listitem_tv_recentchat);
            time=itemView.findViewById(R.id.chattingLobby_listitem_tv_recenttime);

        }
    }

    public Chatting_Lobby_RecyclerAdapter(ArrayList<Chat_Lobby> items,String date) {
        this.items = items;
        this.date=date;

    }


    @NonNull
    @Override
    public Chatting_Lobby_RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatting_lobby_listitem, viewGroup, false);
        Chatting_Lobby_RecyclerAdapter.ViewHolder vh = new Chatting_Lobby_RecyclerAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final Chatting_Lobby_RecyclerAdapter.ViewHolder vh, final int i) {
        vh.LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //채팅방 클릭
                Intent intent = new Intent(view.getContext(), ChattingActivity.class);
                intent.putExtra("ChatName",items.get(i).getChatName_Eng());
                intent.putExtra("opname",items.get(i).getChatName_Kor());
                ((Activity)vh.itemView.getContext()).startActivityForResult(intent,7655);
            }
        });

        vh.chat.setText(items.get(i).getRecent_chat());
        vh.chatname.setText(items.get(i).getChatName_Kor());
        if(date.equals(items.get(i).getRecent_date())){
            vh.time.setText(items.get(i).getRecent_time());
        }else{
            vh.time.setText(items.get(i).getRecent_date());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
