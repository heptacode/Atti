package com.example.justbreathe.atti.Activity.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.justbreathe.atti.Activity.Activities.Recommend.RecommendActiivity;
import com.example.justbreathe.atti.R;

public class MainActivity extends AppCompatActivity {
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll = findViewById(R.id.intent1);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //일단 누르면 자동로그인 풀림
                SharedPreferences mprefs = getSharedPreferences("Profile_Data",MODE_PRIVATE);
                SharedPreferences.Editor mEditor = mprefs.edit();
                mEditor.clear();
                mEditor.apply();

                Intent intent = new Intent(MainActivity.this, RecommendActiivity.class);
                startActivity(intent);
            }
        });
    }
}
