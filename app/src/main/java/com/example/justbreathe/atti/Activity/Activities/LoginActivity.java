package com.example.justbreathe.atti.Activity.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.justbreathe.atti.Activity.Activities.Main.MainActivity;
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
    ProgressDialog asyncDialog;

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
        asyncDialog = new ProgressDialog(this);

        //쉐어드프리퍼런스로 자동로그인
        SharedPreferences mprefs = getSharedPreferences("Profile_Data", MODE_PRIVATE);
        Boolean Auto_Login = mprefs.getBoolean("S_Login", false);
        Log.e("Auto_Login", String.valueOf(Auto_Login));
        if (Auto_Login) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lid = id.getText().toString();
                String lpw = pw.getText().toString();
                Login(lid, lpw, "", false);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_intent = new Intent(LoginActivity.this, RegisterActivity1.class);
                startActivityForResult(register_intent, 2323);
            }
        });
    }

    void Login(final String sid, final String spw, final String sname, final boolean skorean) {

        if (sid.equals("") || spw.equals("")) {
            Toast.makeText(this, "이메일 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(sid).matches()) {
                if (spw.length() >= 8) {
                    asyncDialog.setMessage("요청중입니다.");
                    asyncDialog.show();
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
                                        if (jsonObject.getString("passwd").equals(spw)) {
                                            name = jsonObject.getString("name");
                                            korean = Boolean.parseBoolean(jsonObject.getString("korean"));

                                            //쉐어드로 데이터 저장
                                            if (sname.equals("")) {//로그인버튼 클릭시
                                                SaveProfileData(id.getText().toString(), pw.getText().toString(), name, korean);
                                            } else {//회원가입하고 왔을시
                                                SaveProfileData(sid, spw, sname, skorean);
                                            }

                                            asyncDialog.dismiss();

                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
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
                            asyncDialog.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(this, "8자 이상의 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "이메일 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            Log.e("requestCode", String.valueOf(requestCode));
            switch (requestCode) {
                case 2323:
                    String thisname = data.getStringExtra("name");
                    String thisemail = data.getStringExtra("email");
                    String thispasswd = data.getStringExtra("pw");
                    boolean thiskorean = data.getBooleanExtra("korean", false);

                    Login(thisemail, thispasswd, thisname, thiskorean);
                    break;
            }
        }

    }

    private void SaveProfileData(String _email, String _passwd, String _name, boolean _korean) {
        //쉐어드 저장
        SharedPreferences mprefs = getSharedPreferences("Profile_Data", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mprefs.edit();
        mEditor.putString("S_email", _email);
        mEditor.putString("S_passwd", _passwd);
        mEditor.putString("S_name", _name);
        mEditor.putBoolean("S_korean", _korean);
        mEditor.putBoolean("S_Login", true);//자동로그인 값
        mEditor.apply();
    }
}
