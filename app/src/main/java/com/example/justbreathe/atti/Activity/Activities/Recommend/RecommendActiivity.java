package com.example.justbreathe.atti.Activity.Activities.Recommend;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.justbreathe.atti.Activity.Adapter.Recommend_RecyclerAdapter;
import com.example.justbreathe.atti.Activity.Object.RecAC_list;
import com.example.justbreathe.atti.Activity.Object.RecAC_list_Data;
import com.example.justbreathe.atti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class RecommendActiivity extends AppCompatActivity {
    RecyclerView rcv;
    ArrayList<RecAC_list> items = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    JSONObject jsonObject;
    RecAC_list_Data tmp;
    String image;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_activity);
        rcv = findViewById(R.id.recommend_recycler);
        Recommend_RecyclerAdapter adapter = new Recommend_RecyclerAdapter(items);

        //데이터 받기 - 서버
        db.collection("places")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.e("DB", document.getId() + " => " + document.getData());

                                jsonObject=
                                try {
                                    image = jsonObject.getJSONArray("images").getString(0);
                                    name=jsonObject.getString("name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.e("DB", "name: "+name);
                            }
                        } else {
                            Log.e("DB", "Error getting documents: ", task.getException());
                        }
                    }
                });

        //데이터 추가 - 클라
//        items.add(new RecAC_list("https://firebasestorage.googleapis.com/v0/b/atti-core.appspot.com/o/images%2Fcompressed%2Fimage_001_00.jpg?alt=media&token=f29e6a06-a93b-40f8-8083-b39b987e4ebc", "적당한 제목"));
//        for(int i = 0; i < jsonObject.length(); i++){
//            items.add(new RecAC_list(image, name));
//        }
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(adapter);
    }
}
