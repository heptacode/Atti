package com.example.justbreathe.atti.Activity.Activities.Recommend;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.justbreathe.atti.Activity.Adapter.Recommend_Detail_RecyclerAdapter;
import com.example.justbreathe.atti.Activity.Adapter.Recommend_RecyclerAdpater_Tag;
import com.example.justbreathe.atti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Recommend_Detail_Acitivty extends AppCompatActivity {
    int tmp = 0;
    String Pagenum = "0";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<String> image_urls;
    ArrayList<String> tags;

    ImageView mainimg;
    RecyclerView rcv_img,rcv_tag;
    TextView title, content, site_url, time, day, etc, location,etc_title;
    String str_title, str_content, str_url = null, str_time, str_day, str_etc = null, str_location, str_mainimg_url;
    JSONObject jsonObject;
    boolean isETCEmpty = false, isURLEmpty = false;
    Recommend_Detail_RecyclerAdapter adapter;
    Recommend_RecyclerAdpater_Tag tag_adapter;
    JSONArray tagtmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_detail_acitivty);
        mainimg = findViewById(R.id.rec_dt_ac_mainimg);
        rcv_img = findViewById(R.id.rec_dt_ac_recyclerview);
        title = findViewById(R.id.rec_dt_ac_title);
        content = findViewById(R.id.rec_dt_ac_content);
        site_url = findViewById(R.id.rec_dt_ac_siteurl);
        time = findViewById(R.id.rec_dt_ac_time);
        day = findViewById(R.id.rec_dt_ac_day);
        etc = findViewById(R.id.rec_dt_ac_etc);
        location = findViewById(R.id.rec_dt_ac_location);
        rcv_tag=findViewById(R.id.rec_dt_ac_rv_tag);
        etc_title=findViewById(R.id.rec_dt_ac_etc_title);

        tags=new ArrayList<>();
        image_urls = new ArrayList<>();

        tag_adapter=new Recommend_RecyclerAdpater_Tag(tags);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.HORIZONTAL);
        rcv_tag.setLayoutManager(layoutManager);
        rcv_tag.setAdapter(tag_adapter);

        adapter = new Recommend_Detail_RecyclerAdapter(image_urls);

        Intent intent = getIntent();
        tmp = intent.getIntExtra("PageNum", 0);

        if (tmp < 10 && 0 <= tmp) {
            Pagenum = Pagenum + "0" + tmp;
        } else if (tmp < 100 && 10 <= tmp) {
            Pagenum = Pagenum + tmp;
        } else if (tmp < 1000 && 100 <= tmp) {
            Pagenum = "" + tmp;
        } else {
            finish();
        }
        DocumentReference docRef = db.collection("places").document(Pagenum);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        try {
                            jsonObject = new JSONObject(document.getData());
                            JSONArray array = jsonObject.getJSONArray("images");
                            str_mainimg_url = array.getString(0);
                            tagtmp = jsonObject.getJSONArray("tags");
                            if(!tagtmp.getString(0).equals("")) {
                                for (int i = 0; i < tagtmp.length(); i++) {
                                    tags.add(tagtmp.getString(i));
                                }
                            }

                            for (int i = 1; i < array.length(); i++) {
                                image_urls.add(array.getString(i));
                            }
                            tag_adapter.notifyDataSetChanged();
                            adapter.notifyDataSetChanged();

                            str_title = jsonObject.getString("name");
                            str_content = jsonObject.getString("desc");
                            str_day = jsonObject.getString("day");
                            str_time = jsonObject.getString("time");
                            str_etc = jsonObject.getString("etc");
                            isETCEmpty = jsonObject.getString("etc").isEmpty();
                            str_location = jsonObject.getString("location");
                            str_url = jsonObject.getString("url");
                            isURLEmpty = jsonObject.getString("url").isEmpty();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //셋텍스트, 글라이드
                        String[] timetmp=str_time.split(";");
                        str_time="";
                        for(int i=0;i<timetmp.length;i++){
                            if(i==timetmp.length-1){
                                str_time= str_time+timetmp[i];
                            }else{
                                str_time = str_time + timetmp[i] + "\n";
                            }
                        }
                        String[] etctmp=str_etc.split(";");
                        str_etc="";
                        for(int i=0;i<etctmp.length;i++){
                            if(i==etctmp.length-1){
                                str_etc=str_etc+etctmp[i];
                            }else{
                                str_etc= str_etc+ etctmp[i] + "\n";
                            }
                        }
                        title.setText(str_title);
                        content.setText(str_content);
                        day.setText(str_day);
                        time.setText(str_time);
                        location.setText(str_location);
                        if(image_urls.isEmpty()){
                            rcv_img.setVisibility(View.GONE);
                        }
                        if (!isURLEmpty) {
                            site_url.setText(str_url);
                        } else {
                            site_url.setVisibility(View.GONE);
                        }
                        if (!isETCEmpty) {
                            etc.setText(str_etc);
                        } else {
                            etc_title.setVisibility(View.GONE);
                            etc.setVisibility(View.GONE);
                        }
                        Glide.with(getApplicationContext()).load(str_mainimg_url).into(mainimg);

                    } else {
                        Toast.makeText(Recommend_Detail_Acitivty.this, "정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("DB", "get failed with ", task.getException());
                }
            }
        });
        rcv_img.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv_img.setAdapter(adapter);
    }
}
