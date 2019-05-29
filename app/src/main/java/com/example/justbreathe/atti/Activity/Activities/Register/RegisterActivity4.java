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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity4 extends AppCompatActivity {
    LinearLayout next;
    EditText pw;
    String email;
    Boolean korean;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register4);
        next = findViewById(R.id.reg4_LL_next);
        pw = findViewById(R.id.reg4_et_pw);
        Intent reg3ac = getIntent();
        korean = reg3ac.getBooleanExtra("korean", false);
        email = reg3ac.getStringExtra("email");
        name = reg3ac.getStringExtra("name");

        Log.e("ac4", String.valueOf(korean));
        Log.e("ac4", String.valueOf(name));
        Log.e("ac4", String.valueOf(email));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pw.length() >= 8 && pw.length() <= 16) {  //패스워드 길이 체크

//                Call<Boolean> call = NetworkHelper.getInstance().signup(korean,name,email,pw.getText().toString());
//                call.enqueue(new Callback<Boolean>() {
//                    @Override
//                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                        if(response.code()==123){
//                            if(response.body().booleanValue()){
                    Intent next = new Intent(RegisterActivity4.this, RegisterActivity5.class);
                    startActivity(next);
                    finish();
//                            }
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<Boolean> call, Throwable t) {
//                        Toast.makeText(RegisterActivity4.this, "서버 요청에 문제가 생겼습니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
//                    }
//                });
                }else{
                    Toast.makeText(RegisterActivity4.this, "비밀번호는 8자에서 16자로 설정해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
