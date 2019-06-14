package com.example.justbreathe.atti.Activity.Activities.Recommend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.justbreathe.atti.Activity.Adapter.Recommend_RecyclerAdapter;
import com.example.justbreathe.atti.Activity.Object.RecAC_list;
import com.example.justbreathe.atti.R;

import java.util.ArrayList;

public class RecommendActiivity extends AppCompatActivity {
    RecyclerView rcv;
    ArrayList<RecAC_list> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_activity);
        rcv = findViewById(R.id.recommend_recycler);
        Recommend_RecyclerAdapter adapter = new Recommend_RecyclerAdapter(items);

        items.add(new RecAC_list("https://firebasestorage.googleapis.com/v0/b/atti-core.appspot.com/o/images%2Fcompressed%2Fimage_001_00.jpg?alt=media&token=c39ec978-c2b9-442e-a34b-4d9174b41e40","적당한 제목"));
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(adapter);
    }
}
