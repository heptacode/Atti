package com.example.justbreathe.atti.Activity.Activities.Main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.justbreathe.atti.R;

public class MainActivity_Detail extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);
        tv=findViewById(R.id.main_detail_tv);
        Intent intent = getIntent();
        String a = intent.getStringExtra("ID");
        tv.setText(a);
    }
}
