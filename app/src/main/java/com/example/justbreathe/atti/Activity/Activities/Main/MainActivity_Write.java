package com.example.justbreathe.atti.Activity.Activities.Main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.justbreathe.atti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity_Write extends AppCompatActivity {

    EditText et_title, et_content;
    ImageView write;
    AlertDialog.Builder builder;
    FirebaseFirestore db;
    Map<String, Object> user;
    ProgressDialog asyncDialog;
    String name;
    boolean korean;
    String ID;
    ArrayList<String> images,like_people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__write);
        et_content = findViewById(R.id.main_write_content);
        et_title = findViewById(R.id.main_write_title);
        write = findViewById(R.id.main_write_write);
        asyncDialog = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        user = new HashMap<>();

        builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        images=new ArrayList<>();
        like_people=new ArrayList<>();
        like_people.add("");
        //수정하기
        images.add("");



        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences mprefs = getSharedPreferences("Profile_Data",MODE_PRIVATE);
                korean=mprefs.getBoolean("S_korean",true);
                name=mprefs.getString("S_name","Error");
                Date time = new Date(System.currentTimeMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                String getTime = sdf.format(time);
                Log.e("current Time",getTime);

                asyncDialog.setMessage("요청중입니다.");
                asyncDialog.show();
                user.put("date", getTime);
                user.put("title",et_title.getText().toString());
                user.put("desc", et_content.getText().toString());
                user.put("images", images);
                user.put("korean", korean);
                user.put("like", 0);
                user.put("like_people", like_people);
                user.put("name", name);
                db.collection("recommend")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                int int_id = task.getResult().getDocuments().size()+1;
                                if(int_id<10){
                                    ID="00"+int_id;
                                }else if(int_id<100){
                                    ID="0"+int_id;
                                }else{
                                    ID=""+int_id;
                                }
                                db.collection("recommend").document(ID)
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                asyncDialog.dismiss();
                                                Toast.makeText(MainActivity_Write.this, "글 작성을 완료하였습니다.", Toast.LENGTH_SHORT).show();
                                                setResult(RESULT_OK);
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                asyncDialog.dismiss();
                                                Toast.makeText(MainActivity_Write.this, "서버에 문제가 생겼습니다. 잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });





            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!et_content.getText().toString().equals("") || !et_title.getText().toString().equals("")) {
            builder.setTitle("Atti");
            builder.setMessage("저장되지 않았습니다.\n정말로 나가시겠습니까?");
            builder.setNegativeButton("아니오", null);
            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
            builder.setCancelable(false);
        } else {
            finish();
        }
    }
}

