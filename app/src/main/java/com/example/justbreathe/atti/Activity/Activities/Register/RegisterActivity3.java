package com.example.justbreathe.atti.Activity.Activities.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.justbreathe.atti.Activity.Server.NetworkHelper;
import com.example.justbreathe.atti.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity3 extends AppCompatActivity {
    LinearLayout next;
    EditText email;
    Boolean korean;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        next = findViewById(R.id.reg3_LL_next);
        email = findViewById(R.id.reg3_et_email);

        Intent reg2ac = getIntent();
        korean = reg2ac.getBooleanExtra("korean",false);
        name = reg2ac.getStringExtra("name");
        Log.e("ac3",String.valueOf(korean));
        Log.e("ac3",String.valueOf(name));
        //


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) { //이메일 형식 확인 ejrlwael@sdafjlk.rewaj  이렇게 쳐도 인식 되긴 함
                    Log.e("email_valid","Success");
                    /////////이메일 중복 확인 하기
                    String str_email = email.getText().toString();


//                    Call<Boolean> call = NetworkHelper.getInstance().email(email.getText().toString());
//                    call.enqueue(new Callback<Boolean>() {
//                        @Override
//                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                            if (response.code() == 123) {
//                                boolean valid = response.body();
//                                if (valid) {
                                    Intent next = new Intent(RegisterActivity3.this, RegisterActivity4.class);
                                    next.putExtra("korean", korean);
                                    next.putExtra("name", name);
                                    next.putExtra("email", str_email);
                                    startActivity(next);
                                    finish();
//                                }
//                            } else if (response.code() == 123) {
//                                Toast.makeText(RegisterActivity3.this, "이미 존재하는 이메일입니다.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Boolean> call, Throwable t) {
//                            Toast.makeText(RegisterActivity3.this, "서버 요청에 문제가 생겼습니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                }else{
                    Toast.makeText(RegisterActivity3.this, "이메일 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}


