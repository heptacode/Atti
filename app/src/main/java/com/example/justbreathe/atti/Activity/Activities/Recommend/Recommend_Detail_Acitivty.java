package com.example.justbreathe.atti.Activity.Activities.Recommend;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.justbreathe.atti.Activity.Object.RecAC_list;
import com.example.justbreathe.atti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Recommend_Detail_Acitivty extends AppCompatActivity {
    int Pagenum = 0;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    JSONObject jsonObject;

    TextView tv;
    ArrayList<String> image_url;
    String title, content, site_url, time, day, etc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend__detail__acitivty);
        tv = findViewById(R.id.rec_dt_ac_tv);
        Intent intent = getIntent();
        Pagenum = intent.getIntExtra("PageNum", 0) + 1;


    }
}
