package com.example.justbreathe.atti.Activity.Activities.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.justbreathe.atti.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;

public class RegisterActivity1 extends AppCompatActivity {
    private LinearLayout next;
    private EditText email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        next = findViewById(R.id.reg1_LL_next);
        email = findViewById(R.id.reg1_et_email);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) { //이메일 형식 확인 ejrlwael@sdafjlk.rewaj  이렇게 쳐도 인식 되긴 함
                    Log.e("email_valid", "Success");
                    /////////이메일 중복 확인 하기
                    String str_email = email.getText().toString();

                    Intent next = new Intent(RegisterActivity1.this, RegisterActivity2.class);
                    next.putExtra("email",str_email);
                    startActivity(next);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity1.this, "이메일 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}


