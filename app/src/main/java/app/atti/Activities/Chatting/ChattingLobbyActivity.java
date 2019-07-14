package app.atti.Activities.Chatting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import app.atti.Adapter.Chatting_Lobby_RecyclerAdapter;
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

    JSONArray tmpArray;
    JSONObject tmpObject;

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

        Date time = new Date(System.currentTimeMillis());
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMdd");
        String date = sdfd.format(time);

        madapter = new Chatting_Lobby_RecyclerAdapter(items, date);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv.setAdapter(madapter);

        showChatList();
    }

    String recent_date;
    String recent_chat;
    String recent_time;
    String chatname;

    private void showChatList() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                chatname = dataSnapshot.getKey();

                if (chatname.split(",")[0].equals(email_exept_dot) || chatname.split(",")[1].equals(email_exept_dot)) {//나의 채팅방인가?
                    String op_name;
                    if (dataSnapshot.child("chatLog").getValue() != null) {
                        HashMap<String, String> b = (HashMap<String, String>) dataSnapshot.child("chatLog").getValue();
                        ArrayList<Chat> c = new ArrayList<>();
                        HashMap<String, String> a;
                        for (int i = 0; i < b.values().toArray().length; i++) {
                            a = (HashMap<String, String>) b.values().toArray()[i];
                            c.add(new Chat(a.get("message"), a.get("sender"), a.get("timestamp"), a.get("date"),false));
                        }
                        Collections.sort(c, new Comparator<Chat>() {
                            @Override
                            public int compare(Chat t1, Chat t2) {
                                if (t1.getDate().equals(t2.getDate())) {
                                    int t1_time_hour = Integer.parseInt(t1.getTimestamp().substring(3, 5));
                                    int t1_time_min = Integer.parseInt(t1.getTimestamp().substring(6, 8));
                                    int t1_time_sec = Integer.parseInt(t1.getTimestamp().substring(8, 10));
                                    int t2_time_hour = Integer.parseInt(t2.getTimestamp().substring(3, 5));
                                    int t2_time_min = Integer.parseInt(t2.getTimestamp().substring(6, 8));
                                    int t2_time_sec = Integer.parseInt(t2.getTimestamp().substring(8, 10));

                                    if (t1.getTimestamp().substring(0, 2).equals("오후") || t1.getTimestamp().substring(0, 2).equals("PM")) {
                                        if (t1_time_hour != 12)
                                            t1_time_hour += 12;
                                    } else {
                                        if (t1_time_hour == 12) {
                                            t1_time_hour -= 12;
                                        }
                                    }
                                    if (t2.getTimestamp().substring(0, 2).equals("오후") || t2.getTimestamp().substring(0, 2).equals("PM")) {
                                        if (t2_time_hour != 12)
                                            t2_time_hour += 12;
                                    } else {
                                        if (t2_time_hour == 12) {
                                            t2_time_hour -= 12;
                                        }
                                    }

                                    if (t1_time_hour > t2_time_hour) {
                                        return -1;
                                    } else if (t1_time_hour < t2_time_hour) {
                                        return 1;
                                    } else {
                                        if (t1_time_min > t2_time_min) {
                                            return -1;
                                        } else if (t1_time_min < t2_time_min) {
                                            return 1;
                                        } else {
                                            if (t1_time_sec > t2_time_sec) {
                                                return -1;
                                            } else if (t1_time_sec < t2_time_sec) {
                                                return 1;
                                            }
                                            return 0;
                                        }
                                    }
                                }
                                if (Integer.parseInt(t1.getDate()) > Integer.parseInt(t1.getDate())) {
                                    return -1;
                                } else if (Integer.parseInt(t1.getDate()) < Integer.parseInt(t1.getDate())) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            }
                        });

                        recent_time = c.get(0).getTimestamp();
                        recent_chat = c.get(0).getMessage();
                        recent_date = c.get(0).getDate();
                    }

                    if (dataSnapshot.child("name1").exists() && dataSnapshot.child("name2").exists()) {
                        if (dataSnapshot.child("name1").getValue().equals(name)) {//상대 이름은?
                            op_name = (String) dataSnapshot.child("name2").getValue();
                        } else {
                            op_name = (String) dataSnapshot.child("name1").getValue();
                        }
                        if (dataSnapshot.child("chatLog").exists()) {
                            items.add(new Chat_Lobby(dataSnapshot.getKey(), op_name, recent_chat, recent_date, recent_time));//채팅방 이름을 상대 이름으로
                            Collections.sort(items, new Comparator<Chat_Lobby>() {
                                @Override
                                public int compare(Chat_Lobby t1, Chat_Lobby t2) {
                                    if (t1.getRecent_date().equals(t2.getRecent_date())) {
                                        int t1_time_hour = Integer.parseInt(t1.getRecent_time().substring(3, 5));
                                        int t1_time_min = Integer.parseInt(t1.getRecent_time().substring(6, 8));
                                        int t1_time_sec = Integer.parseInt(t1.getRecent_time().substring(8, 10));
                                        int t2_time_hour = Integer.parseInt(t2.getRecent_time().substring(3, 5));
                                        int t2_time_min = Integer.parseInt(t2.getRecent_time().substring(6, 8));
                                        int t2_time_sec = Integer.parseInt(t2.getRecent_time().substring(8, 10));

                                        if (t1.getRecent_time().substring(0, 2).equals("오후") || t1.getRecent_time().substring(0, 2).equals("PM")) {
                                            if (t1_time_hour != 12)
                                                t1_time_hour += 12;
                                        } else {
                                            if (t1_time_hour == 12) {
                                                t1_time_hour -= 12;
                                            }
                                        }
                                        if (t2.getRecent_time().substring(0, 2).equals("오후") || t2.getRecent_time().substring(0, 2).equals("PM")) {
                                            if (t2_time_hour != 12)
                                                t2_time_hour += 12;
                                        } else {
                                            if (t2_time_hour == 12) {
                                                t2_time_hour -= 12;
                                            }
                                        }

                                        if (t1_time_hour > t2_time_hour) {
                                            return -1;
                                        } else if (t1_time_hour < t2_time_hour) {
                                            return 1;
                                        } else {
                                            if (t1_time_min > t2_time_min) {
                                                return -1;
                                            } else if (t1_time_min < t2_time_min) {
                                                return 1;
                                            } else {
                                                if (t1_time_sec > t2_time_sec) {
                                                    return -1;
                                                } else if (t1_time_sec < t2_time_sec) {
                                                    return 1;
                                                }
                                                return 0;
                                            }
                                        }
                                    }
                                    if (Integer.parseInt(t1.getRecent_date()) > Integer.parseInt(t1.getRecent_date())) {
                                        return -1;
                                    } else if (Integer.parseInt(t1.getRecent_date()) < Integer.parseInt(t1.getRecent_date())) {
                                        return 1;
                                    } else {
                                        return 0;
                                    }
                                }
                            });
                        }
                    }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 7655:
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    break;
            }
        }
    }
}
