package com.example.justbreathe.atti.Activity.Activities.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.justbreathe.atti.Activity.Activities.LoginActivity;
import com.example.justbreathe.atti.Activity.Activities.Recommend.RecommendActiivity;
import com.example.justbreathe.atti.Activity.Adapter.MainAC_RecyclerAdapter;
import com.example.justbreathe.atti.Activity.Adapter.Recommend_RecyclerAdapter;
import com.example.justbreathe.atti.Activity.Object.MainAC_Post;
import com.example.justbreathe.atti.Activity.Object.RecAC_list;
import com.example.justbreathe.atti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //drawer 관련
    ImageView menu;
    DrawerLayout drawerLayout;
    LinearLayout chat,recommend,med_conv;
    TextView profile_name;
    TextView profile_korean;
    ImageView profile_flag;
    ImageView logout;

    //메인화면 Recyclerview
    RecyclerView rcv;
    MainAC_RecyclerAdapter adapter;
    ArrayList<MainAC_Post> items;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    JSONObject jsonObject;

    //recyclerview item
    String db_date,db_content,db_writer,db_title,db_image0;
    int db_like,db_ID;
    boolean db_korean;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chat=findViewById(R.id.drawer_chat);
        recommend=findViewById(R.id.drawer_recommend);
        med_conv=findViewById(R.id.drawer_med_conv);
        profile_name=findViewById(R.id.drawer_name);
        profile_korean=findViewById(R.id.drawer_korean);
        profile_flag=findViewById(R.id.drawer_flag);
        logout=findViewById(R.id.drawer_logout);
        items=new ArrayList<>();
        adapter = new MainAC_RecyclerAdapter(items);
        DrawerAppearance();

        //리스트 띄우기
        db.collection("recommend")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    jsonObject = new JSONObject(document.getData());
                                    //image = jsonObject.getJSONArray("images").getString(0);
                                    db_image0 = jsonObject.getJSONArray("images").getString(0);
                                    db_writer = jsonObject.getString("name");
                                    db_date = jsonObject.getString("date");
                                    db_content = jsonObject.getString("desc");
                                    db_korean = jsonObject.getBoolean("korean");
                                    db_like = jsonObject.getInt("like");
                                    db_title = jsonObject.getString("title");
                                    if(db_content.length()>60) {
                                        db_content = db_content.substring(0, 60) + "...";
                                    }
                                    if(db_title.length()>10) {
                                        db_title = db_title.substring(0, 10) + "...";
                                    }
                                    db_ID= Integer.parseInt(document.getId());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                items.add(new MainAC_Post(db_title,db_writer,db_content,db_image0,db_like,0,db_korean));
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.e("DB", "Error getting documents: ", task.getException());
                        }
                    }
                });
        //rcv.setLayoutManager(new LinearLayoutManager(this));
        //rcv.setAdapter(adapter);









        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(MainActivity.this,RecommendActiivity.class);
                startActivity(intent);
            }
        });
        menu = findViewById(R.id.main_menu);
        drawerLayout=findViewById(R.id.drawer_layout);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //일단 누르면 자동로그인 풀림
                SharedPreferences mprefs = getSharedPreferences("Profile_Data",MODE_PRIVATE);
                SharedPreferences.Editor mEditor = mprefs.edit();
                mEditor.clear();
                mEditor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void DrawerAppearance(){
        SharedPreferences mprefs = getSharedPreferences("Profile_Data",MODE_PRIVATE);
        String name = mprefs.getString("S_name","Null");
        if(mprefs.getBoolean("S_korean",false)){//한국인이면
            profile_korean.setText("한국인");
            profile_flag.setImageResource(R.drawable.ic_korean_flag);
        }else{
            profile_korean.setText("Foreigner");
            profile_flag.setImageResource(R.drawable.ic_location);//외국인 전용 그림 넣기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
        profile_name.setText(name);
    }

}
