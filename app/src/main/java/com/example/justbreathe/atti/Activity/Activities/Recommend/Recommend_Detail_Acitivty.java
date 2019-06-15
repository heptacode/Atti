package com.example.justbreathe.atti.Activity.Activities.Recommend;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justbreathe.atti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.util.ArrayList;

public class Recommend_Detail_Acitivty extends AppCompatActivity {
    int tmp = 0;
    String Pagenum="0";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    JSONObject jsonObject;

    TextView tv;
    ArrayList<String> image_url;
    String title, content, site_url, time, day, etc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_detail_acitivty);
        tv = findViewById(R.id.rec_dt_ac_tv);
        Intent intent = getIntent();
        tmp = intent.getIntExtra("PageNum", 0);
        if(tmp<10&&0<=tmp){
            Pagenum=Pagenum+"0"+tmp;
        }else if (tmp<100&&10<=tmp){
            Pagenum=Pagenum+tmp;
        }else if (tmp<1000&&100<=tmp){
            Pagenum=""+tmp;
        }else{
            finish();
        }
        Log.e("Pagenum=",Pagenum);
        DocumentReference docRef = db.collection("places").document(Pagenum);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String datas = String.valueOf(document.getData());
                        Log.e("datas",datas);
                        //셋텍스트, 글라이드


                    }else{
                        Toast.makeText(Recommend_Detail_Acitivty.this, "정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.e("DB", "get failed with ", task.getException());
                }
            }
        });

    }
}
