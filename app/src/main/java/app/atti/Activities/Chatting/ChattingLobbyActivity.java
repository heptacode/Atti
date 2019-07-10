package app.atti.Activities.Chatting;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.atti.Adapter.Chatting_Lobby_RecyclerAdapter;
import app.atti.Adapter.Chatting_RecyclerAdapter;
import app.atti.Object.Chat;
import app.atti.Object.Chat_Lobby;
import app.atti.R;

public class ChattingLobbyActivity extends AppCompatActivity {
    SharedPreferences prefs;

    RecyclerView rcv;
    Chatting_Lobby_RecyclerAdapter madapter;

    ArrayList<String> items;
    String email;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("chat");

    LinearLayout LL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting__lobby);
        rcv = findViewById(R.id.chattingLobby_rcv);
        LL = findViewById(R.id.chatting_lobby_tab);

        items = new ArrayList<>();

        prefs = getSharedPreferences("Profile_Data", MODE_PRIVATE);
        email = prefs.getString("S_email", "");

        madapter = new Chatting_Lobby_RecyclerAdapter(items);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv.setAdapter(madapter);

        showChatList();

        LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date time = new Date(System.currentTimeMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("a hh:mm");
                SimpleDateFormat sdfd = new SimpleDateFormat("yyyymmdd");
                String current_time = sdf.format(time);
                String date = sdfd.format(time);

                Chat chatData = new Chat("", "asd", current_time, date);  // 유저 이름과 메세지로 chatData 만들기
                Chat_Lobby cat = new Chat_Lobby("sender","email","a@aa,a@aa");
                databaseReference.child("a@aa,a@aa").setValue(cat);
                databaseReference.child("a@aa,a@aa").child("chatLog").push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
            }
        });
    }

    private void showChatList() {
        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("LOG", "dataSnapshot.getKey() : " + dataSnapshot.child("chatName").getValue());
                items.add(dataSnapshot.getKey());
                madapter.notifyItemInserted(items.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
