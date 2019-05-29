package com.example.justbreathe.atti.Activity.Activities.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.justbreathe.atti.R;

public class RegisterAcitivty1 extends AppCompatActivity {
    Button korean,foreigner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        korean = findViewById(R.id.reg1_btn_korean);
        foreigner = findViewById(R.id.reg1_btn_foreigner);

        korean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent korean = new Intent(RegisterAcitivty1.this,RegisterActivity2.class);
                korean.putExtra("korean",true);
                startActivity(korean);
                finish();
            }
        });
        foreigner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent foreigner = new Intent(RegisterAcitivty1.this,RegisterActivity2.class);
                foreigner.putExtra("korean",false);
                startActivity(foreigner);
                finish();
            }
        });

    }
}
