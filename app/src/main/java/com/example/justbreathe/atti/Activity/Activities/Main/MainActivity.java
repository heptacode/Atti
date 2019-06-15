package com.example.justbreathe.atti.Activity.Activities.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.justbreathe.atti.Activity.Activities.LoginActivity;
import com.example.justbreathe.atti.Activity.Activities.Recommend.RecommendActiivity;
import com.example.justbreathe.atti.R;

public class MainActivity extends AppCompatActivity {
    ImageView menu;
    DrawerLayout drawerLayout;
    LinearLayout chat,recommend,med_conv;
    TextView profile_name;
    TextView profile_korean;
    ImageView profile_flag;
    ImageView logout;
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
        DrawerAppearance();

        //리스트 띄우기










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
