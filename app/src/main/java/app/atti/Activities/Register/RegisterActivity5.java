package app.atti.Activities.Register;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import app.atti.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity5 extends AppCompatActivity {
    private LinearLayout login;
    private String str_email, str_pw, str_name;
    private boolean korean;
    private boolean prevent_duplication=false;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register5);
        login = findViewById(R.id.reg5_LL_next);

        Intent prev = getIntent();
        str_email = prev.getStringExtra("email");
        str_pw = prev.getStringExtra("pw");
        str_name = prev.getStringExtra("name");
        korean = prev.getBooleanExtra("korean", false);

        if(!prevent_duplication) {
            prevent_duplication=true;
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Map<String, Object> datas = new HashMap<>();
                    datas.put("korean", korean);
                    datas.put("name", str_name);
                    datas.put("passwd", str_pw);

                    db.collection("accounts").document(str_email)
                            .set(datas)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    //지금은 로그인 화면으로 돌아감
                                    //쉐어드프리퍼런스에 위의 값 저장하기
                                    Intent intent = new Intent();
                                    intent.putExtra("email",str_email);
                                    intent.putExtra("pw",str_pw);
                                    intent.putExtra("name",str_name);
                                    intent.putExtra("korean",korean);//boolean
                                    setResult(RESULT_OK,intent);
                                    finish();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity5.this, "통신 오류가 발생했습니다. 다시 시도하십시오.", Toast.LENGTH_SHORT).show();
                                    prevent_duplication=false;
                                }
                            });
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
    }
}
