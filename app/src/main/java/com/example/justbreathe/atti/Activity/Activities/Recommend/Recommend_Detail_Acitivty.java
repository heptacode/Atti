package com.example.justbreathe.atti.Activity.Activities.Recommend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.justbreathe.atti.R;

public class Recommend_Detail_Acitivty extends AppCompatActivity {
    int Pagenum=0;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend__detail__acitivty);
        tv= findViewById(R.id.rec_dt_ac_tv);
        Intent intent = getIntent();
        Pagenum = intent.getIntExtra("PageNum",0);
        tv.setText(""+Pagenum);
    }
}
