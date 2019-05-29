package com.example.justbreathe.atti.Activity.Activities.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.justbreathe.atti.R;

public class RegisterActivity2 extends AppCompatActivity {
    boolean korean;
    LinearLayout next;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        next = findViewById(R.id.reg2_LL_next);
        name = findViewById(R.id.reg2_et_name);

        Intent reg1ac = getIntent();
        korean = reg1ac.getBooleanExtra("korean",false);
        Log.e("ac2",String.valueOf(korean));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(RegisterActivity2.this,RegisterActivity3.class);
                String str_name = name.getText().toString();
                next.putExtra("korean",korean);
                next.putExtra("name",str_name);
                startActivity(next);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
