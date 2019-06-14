package com.example.justbreathe.atti.Activity.Activities.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.justbreathe.atti.R;

public class RegisterActivity4 extends AppCompatActivity {
    private Button korean,foreigner;
    private String str_email, str_pw, str_name;
    private boolean prevent_duplication=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register4);
        korean = findViewById(R.id.reg4_btn_korean);
        foreigner = findViewById(R.id.reg4_btn_foreigner);

        Intent prev = getIntent();
        str_email = prev.getStringExtra("email");
        str_pw = prev.getStringExtra("pw");
        str_name = prev.getStringExtra("name");
        if(!prevent_duplication) {
            prevent_duplication=true;
            korean.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intention(true);

                }
            });
            foreigner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intention(false);
                }
            });
        }
    }

    private void Intention(boolean korean){
        Intent next = new Intent(RegisterActivity4.this,RegisterActivity5.class);

        next.putExtra("korean",korean);
        next.putExtra("name",str_name);
        next.putExtra("email",str_email);
        next.putExtra("pw",str_pw);

        startActivity(next);
        finish();
    }

}
