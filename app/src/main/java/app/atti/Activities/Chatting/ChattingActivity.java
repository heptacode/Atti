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
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.atti.Adapter.Chatting_RecyclerAdapter;
import app.atti.Object.Chat;
import app.atti.R;

public class ChattingActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("message");

    SharedPreferences prefs;

    Button send;
    EditText edt_message;
    String name,email;

    ArrayList<Chat> items;
    Chatting_RecyclerAdapter adapter;
    RecyclerView rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        send = findViewById(R.id.chat_btn_send);
        edt_message=findViewById(R.id.chat_edt_message);
        rcv=findViewById(R.id.chat_speech_bubble_recyclerview);
        prefs = getSharedPreferences("Profile_Data",MODE_PRIVATE);
        email = prefs.getString("S_email","");
        name = prefs.getString("S_name","Null");
        items=new ArrayList<>();

        adapter= new Chatting_RecyclerAdapter(items);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv.setAdapter(adapter);

        openChat("a@a.a:a@a.a");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date time = new Date(System.currentTimeMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("a hh:mm");
                String current_time = sdf.format(time);

                Chat chatData = new Chat( edt_message.getText().toString(),name,current_time);  // 유저 이름과 메세지로 chatData 만들기
                databaseReference.child("chat").child("a@a.a:a@a.a").push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
                edt_message.setText("");
            }
        });
    }
    private void openChat(String chatName) {
        // 리스트 어댑터 생성 및 세팅

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").child(chatName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat chat = dataSnapshot.getValue(Chat.class);
//                (ArrayList<String>임 adapter.add(chatDTO.getUserName() + " : " + chatDTO.getMessage());
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
