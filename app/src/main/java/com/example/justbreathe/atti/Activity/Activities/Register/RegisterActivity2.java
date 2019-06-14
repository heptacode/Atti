package com.example.justbreathe.atti.Activity.Activities.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.justbreathe.atti.R;

public class RegisterActivity2 extends AppCompatActivity {
    private LinearLayout next;
    private EditText pw;
    private String str_email;
    private boolean prevent_duplication=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        next = findViewById(R.id.reg2_LL_next);
        pw = findViewById(R.id.reg2_et_pw);
        Intent prev = getIntent();
        str_email = prev.getStringExtra("email");

        if(!prevent_duplication) {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prevent_duplication=true;
                    if (pw.length() >= 8 && pw.length() <= 16) {  //패스워드 길이 체크
                        Intent next = new Intent(RegisterActivity2.this, RegisterActivity3.class);
                        String str_pw = pw.getText().toString();

                        next.putExtra("email", str_email);
                        next.putExtra("pw", str_pw);
                        next.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);

                        startActivity(next);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity2.this, "비밀번호는 8자에서 16자로 설정해주세요", Toast.LENGTH_SHORT).show();
                    }
                    prevent_duplication=false;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
    }
}
