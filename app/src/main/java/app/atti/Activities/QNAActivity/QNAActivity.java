package app.atti.Activities.QNAActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.atti.Adapter.QNA_RecyclerAdapter;
import app.atti.Object.QNA_Post;
import app.atti.R;

public class QNAActivity extends AppCompatActivity {

    ImageView img_write;
    RecyclerView rcv;
    ArrayList<QNA_Post> items;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    QNA_RecyclerAdapter adapter;

    String db_date,db_content,db_writer,db_title,db_image0,db_ID;
    int db_like;
    boolean db_korean,db_i_like=false;
    private String db_writer_email;
    JSONObject jsonObject;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna);
        img_write = findViewById(R.id.qna_write);
        rcv = findViewById(R.id.qna_recycler_view);

        Intent intent = getIntent();
        email = intent.getStringExtra("myemail");


        items = new ArrayList<>();
        adapter = new QNA_RecyclerAdapter(items);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());
        dividerItemDecoration.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.rec_list_devieder_stroke));
        rcv.addItemDecoration(dividerItemDecoration);

        //리스트 띄우기
        ListLoading();

        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv.setAdapter(adapter);

        img_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(QNAActivity.this, QNA_Write.class);
                intent1.putExtra("tmp_email",email);
                startActivityForResult(intent1,2999);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 2999:
                case 2233:
                    ListLoading();
                    break;
            }
        }
    }
    public void ListLoading(){
        items.clear();
        db.collection("qna")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db_i_like=false;
                                try {
                                    jsonObject = new JSONObject(document.getData());
                                    db_image0 = jsonObject.getJSONArray("images").getString(0);
                                    db_writer = jsonObject.getString("name");
                                    db_date = jsonObject.getString("date");
                                    db_content = jsonObject.getString("desc");
                                    db_korean = jsonObject.getBoolean("korean");
                                    db_title = jsonObject.getString("title");
                                    db_like=jsonObject.getJSONArray("likes").length()-1;
                                    db_writer_email = jsonObject.getString("email");

                                    for(int i=1;i<jsonObject.getJSONArray("likes").length();i++){
                                        if(jsonObject.getJSONArray("likes").get(i).equals(email)){
                                            db_i_like=true;
                                        }
                                    }
                                    if(db_content.length()>81) {
                                        db_content = db_content.substring(0, 81) + "...";
                                    }
                                    if(db_title.length()>15) {
                                        db_title = db_title.substring(0, 15) + "...";
                                    }
                                    db_ID= document.getId();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                items.add(new QNA_Post(db_title,db_date,db_writer,db_content,db_image0,db_like,db_ID,db_korean,db_i_like,db_writer_email));
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.e("DB", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
