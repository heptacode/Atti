package com.example.justbreathe.atti.Activity.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.justbreathe.atti.Activity.Activities.Register.RegisterActivity1;
import com.example.justbreathe.atti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button login, register;
    EditText id, pw;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    JSONObject jsonObject;
    String name;
    boolean korean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id = findViewById(R.id.login_et_id);
        pw = findViewById(R.id.login_et_pw);
        login = findViewById(R.id.login_btn_login);
        register = findViewById(R.id.login_btn_register);


        //쉐어드프리퍼런스로 자동로그인
        //
        //
        //
        //

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sid = id.getText().toString();
                final String spw = pw.getText().toString();
                //아이디 비번 서버 전송
                //서버에서 이름(String) 한국인 여부(boolean) 받기

                DocumentReference docRef = db.collection("accounts").document(sid);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) { // 이메일에 해당하는 레코드가 존재할 때
                                String datas = String.valueOf(document.getData());
                                try {
                                    jsonObject = new JSONObject(datas);
                                    if(jsonObject.getString("passwd").equals(spw)){
                                        name = jsonObject.getString("name");
                                        korean = Boolean.parseBoolean(jsonObject.getString("korean"));
                                        Log.e(name, String.valueOf(korean));
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "계정 정보가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "계정 정보가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("DB", "get failed with ", task.getException());
                        }
                    }
                });
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_intent = new Intent(LoginActivity.this, RegisterActivity1.class);
                startActivity(register_intent);
                //회원가입 후 다시 돌아오기 위해 finish() 안함
            }
        });
    }
}
