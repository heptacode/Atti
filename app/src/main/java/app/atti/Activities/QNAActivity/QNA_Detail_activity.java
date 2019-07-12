package app.atti.Activities.QNAActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import app.atti.Adapter.QNA_Comment_RecyclerAdapter;
import app.atti.Object.QNA_Comment;
import app.atti.R;

public class QNA_Detail_activity extends AppCompatActivity {

    String db_name, db_date, db_title, db_content, db_image_url;
    boolean db_korean;

    ImageView image, delimg, korean;
    TextView name, date, title, content, send;
    String writer_email;
    String ID;
    EditText edt_comment;

    JSONObject jsonObject;
    FirebaseFirestore db;

    SharedPreferences prefs;
    String myemail;
    String myname;
    boolean mykorean;
    String mydate;
    ArrayList<QNA_Comment> items;
    QNA_Comment tmp;

    QNA_Comment_RecyclerAdapter adapter;
    RecyclerView rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna_detail);

        image = findViewById(R.id.qna_dt_img_qnaimg);
        delimg = findViewById(R.id.qna_item_img_del);
        korean = findViewById(R.id.qna_dt_img_korean);
        name = findViewById(R.id.qna_dt_tv_name);
        date = findViewById(R.id.qna_dt_tv_date);
        title = findViewById(R.id.qna_dt_tv_title);
        content = findViewById(R.id.qna_dt_tv_content);
        send = findViewById(R.id.qna_dt_comment_tv_send);
        edt_comment = findViewById(R.id.qna_dt_comment_edt);
        rcv = findViewById(R.id.qna_detail_comment);

        prefs = getSharedPreferences("Profile_Data", MODE_PRIVATE);
        items = new ArrayList<>();
        adapter = new QNA_Comment_RecyclerAdapter(items);

        myemail = prefs.getString("S_email", "");
        myname = prefs.getString("S_name", "");
        mykorean = prefs.getBoolean("S_korean", false);

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        writer_email = intent.getStringExtra("Writer_email");

        db = FirebaseFirestore.getInstance();

        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv.setAdapter(adapter);
        CommentLoading();

        DocumentReference docRef = db.collection("qna").document(ID);
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


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String written_commend = edt_comment.getText().toString();
                edt_comment.setText("");

                Date time = new Date(System.currentTimeMillis());
                SimpleDateFormat sdfd = new SimpleDateFormat("MM.dd");
                mydate = sdfd.format(time);

                tmp = new QNA_Comment(myname, myemail, mydate, mykorean, written_commend);
//                db.collection("qna").document(ID)
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    JSONArray jsonArray;
//                                    JSONObject tmpjsonobject;
//                                    try {
//                                        jsonArray = new JSONObject(task.getResult().getData()).getJSONArray("comments");
//                                        for (int i = 0; i < jsonArray.length(); i++) {//문자열 배열만큼 반복
//                                            tmpjsonobject=jsonArray.getJSONObject(i);
//                                            String tmpname = tmpjsonobject.getString("name");
//                                            String tmpemail = tmpjsonobject.getString("email");
//                                            String tmpdate = tmpjsonobject.getString("date");
//                                            String tmpcomment= tmpjsonobject.getString("comment");
//                                            boolean tmpkorean = tmpjsonobject.getBoolean("korean");
//                                            tmparray.add(new QNA_Comment(tmpname,tmpemail,tmpdate,tmpkorean,tmpcomment));
//                                        }
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//
//                                }
//                            }
//                        });
                items.add(0,tmp);
                adapter.notifyDataSetChanged();
                db.collection("qna").document(ID).update("comments", items);

            }
        });


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
                        db.collection("qna").document(ID).delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(), "게시물을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "게시물 삭제 실패", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });
                builder.show();
            }
        });
    }

    private void CommentLoading() {
        items.clear();
        db.collection("qna").document(ID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            try {
                                JSONArray jsonArray;
                                JSONObject tmpjsonobject;
                                jsonArray = new JSONObject(task.getResult().getData()).getJSONArray("comments");
                                for (int i = 0; i < jsonArray.length(); i++) {//문자열 배열만큼 반복
                                    tmpjsonobject=jsonArray.getJSONObject(i);
                                    String tmpname = tmpjsonobject.getString("name");
                                    String tmpemail = tmpjsonobject.getString("email");
                                    String tmpdate = tmpjsonobject.getString("date");
                                    String tmpcomment= tmpjsonobject.getString("comment");
                                    boolean tmpkorean = tmpjsonobject.getBoolean("korean");
                                    items.add(new QNA_Comment(tmpname,tmpemail,tmpdate,tmpkorean,tmpcomment));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();

                        }
                    }
                });


    }
}
