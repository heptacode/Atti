package app.atti.Activities.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import app.atti.R;


public class RegisterActivity3 extends AppCompatActivity {
    private LinearLayout next;
    private EditText name;
    private String str_email, str_pw;
    private boolean prevent_duplication=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        next = findViewById(R.id.reg3_LL_next);
        name = findViewById(R.id.reg3_et_name);

        Intent prev = getIntent();
        str_email = prev.getStringExtra("email");
        str_pw = prev.getStringExtra("pw");
        if(!prevent_duplication) {
            prevent_duplication=true;
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent next = new Intent(RegisterActivity3.this, RegisterActivity4.class);
                    String str_name = name.getText().toString();

                    next.putExtra("name", str_name);
                    next.putExtra("email", str_email);
                    next.putExtra("pw", str_pw);
                    next.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);

                    startActivity(next);
                    finish();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
    }
}
