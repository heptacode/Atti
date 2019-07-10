package app.atti.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.atti.Object.Chat;
import app.atti.R;

public class Chatting_RecyclerAdapter  extends RecyclerView.Adapter<Chatting_RecyclerAdapter.ViewHolder> {
    ArrayList<Chat> items;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,time,message;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.chat_item_tv_name);
            time=itemView.findViewById(R.id.chat_item_tv_time);
            message=itemView.findViewById(R.id.chat_item_tv_message);
        }
    }

    public Chatting_RecyclerAdapter(ArrayList<Chat> items) {
        this.items = items;
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

        vh.time.setText(chat.getTimestamp());
        vh.message.setText(chat.getMessage());
        vh.name.setText(chat.getSender());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
