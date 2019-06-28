package app.atti.Activities.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.atti.Activities.LoginActivity;
import app.atti.Activities.Recommend.RecommendActiivity;
import app.atti.Adapter.MainAC_RecyclerAdapter;
import app.atti.Object.MainAC_Post;
import app.atti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //drawer 관련
    ImageView menu;
    DrawerLayout drawerLayout;
    LinearLayout chat,recommend,med_conv,drawer_background;
    TextView profile_name;
    TextView profile_korean;
    ImageView profile_flag;
    ImageView logout, write;
    String email="";

    //메인화면 Recyclerview
    RecyclerView rcv;
    MainAC_RecyclerAdapter adapter;
    ArrayList<MainAC_Post> items;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    JSONObject jsonObject;

    //recyclerview item
    String db_date,db_content,db_writer,db_title,db_image0,db_ID;
    int db_like;
    boolean db_korean,db_i_like=false;


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
        rcv=findViewById(R.id.recycler_view);
        drawer_background=findViewById(R.id.drawer_background);
        write = findViewById(R.id.main_write);

        items=new ArrayList<>();
        adapter = new MainAC_RecyclerAdapter(items);
        DrawerAppearance();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());
        dividerItemDecoration.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.rec_list_devieder_stroke));
        rcv.addItemDecoration(dividerItemDecoration);

        //리스트 띄우기
        ListLoading();

        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv.setAdapter(adapter);

        drawer_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //드로우어 배경 클릭시 비정상적인 이벤트 발생 방지를 위한 빈 클릭이벤트리스너
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
            }
        });
        med_conv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
            }
        });

        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(MainActivity.this, RecommendActiivity.class);
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
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainActivity_Write.class);
                startActivityForResult(intent,1999);
            }
        });
    }
    private void DrawerAppearance(){
        SharedPreferences mprefs = getSharedPreferences("Profile_Data",MODE_PRIVATE);
        email = mprefs.getString("S_email","");
        String name = mprefs.getString("S_name","Null");
        if(mprefs.getBoolean("S_korean",false)){//한국인이면
            profile_korean.setText("한국인");
            profile_flag.setImageResource(R.drawable.ic_korean_flag);
        }else{
            profile_korean.setText("Foreigner");
            profile_flag.setImageResource(R.drawable.ic_foreigner_flag);//외국인 전용 그림 넣기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
        profile_name.setText(name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 1999:
                    ListLoading();
                    break;
            }
        }
    }
    void ListLoading(){
        items.clear();
        db.collection("recommend")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    jsonObject = new JSONObject(document.getData());
                                    db_image0 = jsonObject.getJSONArray("images").getString(0);
                                    db_writer = jsonObject.getString("name");
                                    db_date = jsonObject.getString("date");
                                    db_content = jsonObject.getString("desc");
                                    db_korean = jsonObject.getBoolean("korean");
                                    db_title = jsonObject.getString("title");
                                    db_like=jsonObject.getInt("like");

                                    for(int i=0;i<jsonObject.getJSONArray("likes").length();i++){
                                        if(jsonObject.getJSONArray("likes").get(i).equals(email)){
                                            db_i_like=true;
                                        }
                                    }
                                    if(db_content.length()>81) {
                                        db_content = db_content.substring(0, 81) + "...";
                                    }
                                    if(db_title.length()>15) {
                                        db_title = db_title.substring(0, 15) + "...";
                                    }
                                    db_ID= document.getId();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                items.add(new MainAC_Post(db_title,db_date,db_writer,db_content,db_image0,db_like,db_ID,db_korean,db_i_like));
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.e("DB", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
