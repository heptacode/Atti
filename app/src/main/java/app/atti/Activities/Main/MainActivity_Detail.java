package app.atti.Activities.Main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import app.atti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity_Detail extends AppCompatActivity {
    FirebaseFirestore db;
    String ID;
    JSONObject jsonObject;
    boolean db_korean;
    String db_name, db_date, db_title, db_content, db_image_url;

    ImageView korean, image;
    TextView name, date, title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);
        korean = findViewById(R.id.main_dt_img_korean);
        image = findViewById(R.id.main_dt_img_mainimg);
        name = findViewById(R.id.main_dt_tv_name);
        date = findViewById(R.id.main_dt_tv_date);
        title = findViewById(R.id.main_dt_tv_title);
        content = findViewById(R.id.main_dt_tv_content);

        Intent intent = getIntent();
        db = FirebaseFirestore.getInstance();
        ID = intent.getStringExtra("ID");
        DocumentReference docRef = db.collection("recommend").document(ID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        jsonObject = new JSONObject(document.getData());
                        try {
                            db_korean=jsonObject.getBoolean("korean");
                            db_name=jsonObject.getString("name");
                            db_date=jsonObject.getString("date");
                            db_title=jsonObject.getString("title");
                            db_content=jsonObject.getString("desc");
                            db_image_url=jsonObject.getJSONArray("images").getString(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(db_korean){
                            korean.setImageResource(R.drawable.ic_korean_flag);
                        }else{
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
    }
}
