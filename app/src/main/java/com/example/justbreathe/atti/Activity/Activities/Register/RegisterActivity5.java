package com.example.justbreathe.atti.Activity.Activities.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.justbreathe.atti.Activity.Activities.LoginActivity;
import com.example.justbreathe.atti.R;

public class RegisterActivity5 extends AppCompatActivity {

    private LinearLayout login;
    private String str_email, str_pw, str_name;
    private boolean korean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register5);
        login = findViewById(R.id.reg5_LL_next);

        Intent prev = getIntent();
        str_email = prev.getStringExtra("email");
        str_pw = prev.getStringExtra("pw");
        str_name = prev.getStringExtra("name");
        korean = prev.getBooleanExtra("korean",false);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //지금은 로그인 화면으로 돌아감
                //쉐어드프리퍼런스에 위의 값 저장하기
                Intent intent = new Intent(RegisterActivity5.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
    }
}
