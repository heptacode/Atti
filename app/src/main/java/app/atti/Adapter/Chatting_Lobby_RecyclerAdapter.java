package app.atti.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.atti.Activities.Chatting.ChattingActivity;
import app.atti.Object.Chat_Lobby;
import app.atti.R;

public class Chatting_Lobby_RecyclerAdapter extends RecyclerView.Adapter<Chatting_Lobby_RecyclerAdapter.ViewHolder> {
    ArrayList<Chat_Lobby> items;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView chatname;
        LinearLayout LL;

        public ViewHolder(View itemView) {
            super(itemView);
            LL = itemView.findViewById(R.id.chatting_lobby_listitem_LL);
            chatname = itemView.findViewById(R.id.chattingLobby_listitem_tv_chatname);
        }
    }

    public Chatting_Lobby_RecyclerAdapter(ArrayList<Chat_Lobby> items) {
        this.items = items;
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
                intent.putExtra("ChatName",items.get(i).getchatName_Eng());
                intent.putExtra("opname",items.get(i).getchatName_Kor());
                vh.itemView.getContext().startActivity(intent);
            }
        });

        vh.chatname.setText(items.get(i).getchatName_Kor());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
