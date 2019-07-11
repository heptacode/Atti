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

    ArrayList<Chat_Lobby> items;
    String email, email_exept_dot;
    String name;

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
        name = prefs.getString("S_name", "");
        email = prefs.getString("S_email", "");
        email_exept_dot = email.split("\\.")[0] + email.split("\\.")[1];

        madapter = new Chatting_Lobby_RecyclerAdapter(items);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv.setAdapter(madapter);

        showChatList();
    }

    private void showChatList() {
        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("LOG", "dataSnapshot.getKey() : " + dataSnapshot.getKey());
                String chatname = dataSnapshot.getKey();
                if (chatname.split(",")[0].equals(email_exept_dot) || chatname.split(",")[1].equals(email_exept_dot)) {//나의 채팅방인가?
                    String op_name;
                    if (dataSnapshot.child("name1").getValue().equals(name)) {//상대 이름은?
                        op_name = (String) dataSnapshot.child("name2").getValue();
                    } else {
                        op_name = (String) dataSnapshot.child("name1").getValue();
                    }
                    items.add(new Chat_Lobby(dataSnapshot.getKey(),op_name));//채팅방 이름을 상대 이름으로
                    madapter.notifyItemInserted(items.size() - 1);
                }
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
