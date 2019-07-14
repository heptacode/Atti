package app.atti.Activities.Main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.atti.Activities.Profile.ProfileActivity;
import app.atti.Adapter.MainAC_RecyclerAdapter;
import app.atti.R;


public class MainActivity_Detail extends AppCompatActivity {
    FirebaseFirestore db;
    String ID;
    JSONObject jsonObject;
    boolean db_korean;
    String db_name, db_date, db_title, db_content, db_image_url;

    String writer_email, myemail;

    ImageView korean, image;
    LinearLayout like;
    TextView name, date, title, content;
    JSONArray array, array1;

    ImageView delimg;

    SharedPreferences prefs;
    Context context;
    ImageView likeimg;
    RelativeLayout pro_img;
    boolean pressed = false, db_i_like = false;
    TextView like_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_detail);
        delimg = findViewById(R.id.main_item_img_del);
        korean = findViewById(R.id.main_dt_img_korean);
        image = findViewById(R.id.main_dt_img_mainimg);
        name = findViewById(R.id.main_dt_tv_name);
        date = findViewById(R.id.main_dt_tv_date);
        title = findViewById(R.id.main_dt_tv_title);
        content = findViewById(R.id.main_dt_tv_content);
        like = findViewById(R.id.main_dt_LL_like);
        likeimg = findViewById(R.id.main_dt_like_img);
        like_num = findViewById(R.id.main_dt_like_num);
        pro_img = findViewById(R.id.main_dt_relative);

        context = this;
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        writer_email = intent.getStringExtra("Writer_email");

        prefs = getSharedPreferences("Profile_Data", MODE_PRIVATE);
        myemail = prefs.getString("S_email", "");

        DocumentReference docRef = db.collection("recommend").document(ID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        jsonObject = new JSONObject(document.getData());
                        try {
                            db_korean = jsonObject.getBoolean("korean");
                            db_name = jsonObject.getString("name");
                            db_date = jsonObject.getString("date");
                            db_title = jsonObject.getString("title");
                            db_content = jsonObject.getString("desc");
                            db_image_url = jsonObject.getJSONArray("images").getString(0);
                            for (int i = 1; i < jsonObject.getJSONArray("likes").length(); i++) {
                                if (jsonObject.getJSONArray("likes").get(i).equals(myemail)) {
                                    db_i_like = true;
                                    likeimg.setImageResource(R.drawable.ic_liked);
                                    break;
                                }
                            }
                            like_num.setText(jsonObject.getJSONArray("likes").length() - 1 + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (db_korean) {
                            korean.setImageResource(R.drawable.ic_korean_flag);
                        } else {
                            korean.setImageResource(R.drawable.ic_foreigner_flag);
                        }

                        name.setText(db_name);
                        date.setText(db_date);
                        title.setText(db_title);
                        content.setText(db_content);
                        Glide.with(getApplicationContext()).load(db_image_url).into(image);
                    }
                } else {
                    Log.e("DB", "get failed with ", task.getException());
                }
            }
        });
        if (writer_email.equals(myemail)) {
            delimg.setVisibility(View.VISIBLE);
        } else {
            delimg.setVisibility(View.GONE);
        }
        delimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //데이터 삭제
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(view.getContext());
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle("Atti");
                builder.setMessage("정말로 게시물을 삭제하시겠습니까?");
                builder.setNegativeButton("아니오", null);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //삭제하기!!
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("recommend").document(ID).delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "게시물을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "게시물 삭제 실패", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });
                builder.show();
            }
        });
        pro_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_Detail.this, ProfileActivity.class);
                intent.putExtra("toprofile_email", writer_email);
                startActivity(intent);
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pressed) {
                    pressed = true;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pressed = false;
                        }
                    }, 500);

                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("recommend").document(ID)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (!db_i_like) {  //좋아요 과거에 안눌었다면
                                        //like_people에 자신 추가
                                        ArrayList<String> tmp = new ArrayList<>();
                                        try {
                                            array = new JSONObject(task.getResult().getData()).getJSONArray("likes");
                                            for (int i = 0; i < array.length(); i++) {//문자열 배열만큼 반복
                                                tmp.add(array.getString(i));
                                            }
                                            tmp.add(myemail);
                                            //서버로 전송
                                            db.collection("recommend").document(ID).update("likes", tmp);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
//                                    items.get(position).setLike(Integer.parseInt(task.getResult().get("like").toString()) + 1);
                                        like_num.setText(tmp.size() - 1 + "");
                                        db_i_like = true;
                                        likeimg.setImageResource(R.drawable.ic_liked);

                                    } else { //좋아요 과거에 눌렀다면
                                        //like_people에 자신 삭제
                                        ArrayList<String> tmp = new ArrayList<>();
                                        try {
                                            array1 = new JSONObject(task.getResult().getData()).getJSONArray("likes");
                                            for (int i = 0; i < array1.length(); i++) {//문자열 배열만큼 반복
                                                if (array1.getString(i).equals(myemail)) {//좋아요 삭제
                                                    continue;
                                                }
                                                tmp.add(array1.getString(i));
                                            }
                                            //서버로 전송
                                            db.collection("recommend").document(ID).update("likes", tmp);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
//                                    items.get(position).setLike(Integer.parseInt(task.getResult().get("like").toString()) - 1);
                                        like_num.setText(tmp.size() - 1 + "");

                                        db_i_like = false;
                                        likeimg.setImageResource(R.drawable.ic_liked_x);

                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("좋아요", "누르기 실패");
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
