package com.example.justbreathe.atti.Activity.Activities.Register;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.justbreathe.atti.Activity.Object.User;
import com.example.justbreathe.atti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class RegisterActivity1 extends AppCompatActivity {
    private LinearLayout next;
    private EditText email;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


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
                    final String str_email = email.getText().toString();


                    DocumentReference docRef = db.collection("accounts").document(str_email);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (!document.exists()) {

                                    Intent next = new Intent(RegisterActivity1.this, RegisterActivity2.class);
                                    next.putExtra("email", str_email);
                                    startActivity(next);
                                    finish();

                                } else {
                                    Toast.makeText(RegisterActivity1.this, "이메일에 해당하는 계정이 이미 있습니다.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.d("DB", "get failed with ", task.getException());
                            }
                        }
                    });

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


