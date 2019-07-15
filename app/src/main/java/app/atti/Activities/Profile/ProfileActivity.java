package app.atti.Activities.Profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import app.atti.Activities.Chatting.ChattingActivity;
import app.atti.Activities.Profile.Fragment.MeasuredViewPager;
import app.atti.Adapter.Profile_ViewpagerAdapter;
import app.atti.R;

public class ProfileActivity extends AppCompatActivity {
    Button tochat;
    String profile_email;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView tv_name;
    ImageView flag;
    String profile_name;
    boolean profile_korean;
    JSONObject jsonObject;
    SharedPreferences prefs;
    String myemail, email_exept_dot, myname;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("chat");
    MeasuredViewPager viewPager;
    Profile_ViewpagerAdapter pageradapter;
    TabLayout tab;
    String tmpchatname;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tochat = findViewById(R.id.profile_btn_tochat);
        flag = findViewById(R.id.profile_profile_img_flag);
        tv_name = findViewById(R.id.profile_profile_tv_name);
        viewPager = findViewById(R.id.profile_viewpager);
        tab = findViewById(R.id.profile_tablayout);

        prefs = getSharedPreferences("Profile_Data", MODE_PRIVATE);
        myemail = prefs.getString("S_email", "");
        email_exept_dot = myemail.split("\\.")[0] + myemail.split("\\.")[1];
        myname = prefs.getString("S_name", "");

        Intent intent = getIntent();
        profile_email = intent.getStringExtra("toprofile_email");

        pageradapter = new Profile_ViewpagerAdapter(getSupportFragmentManager(), profile_email);
        viewPager.setAdapter(pageradapter);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        tab.setSelectedTabIndicatorColor(Color.parseColor("#ffeaa6"));
        tab.addTab(tab.newTab().setText("추천"));
        tab.addTab(tab.newTab().setText("Q&A"));

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (profile_email.equals(myemail)) {
            tochat.setVisibility(View.GONE);
        }

        DocumentReference docRef = db.collection("accounts").document(profile_email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String datas = String.valueOf(document.getData());
                    try {
                        jsonObject = new JSONObject(datas);
                        profile_name = jsonObject.getString("name");
                        profile_korean = jsonObject.getBoolean("korean");
                    } catch (JSONException e) {
                    }
                    if (profile_korean) {
                        flag.setImageResource(R.drawable.ic_korean_flag);
                    } else {
                        flag.setImageResource(R.drawable.ic_foreigner_flag);
                    }

                    tv_name.setText(profile_name);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        tochat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //채팅방 만들거나 채팅방이 존재하면 채팅방 이동
                String tmp_opemail = profile_email.split("\\.")[0] + profile_email.split("\\.")[1];
                String tmp_myemail = email_exept_dot;
                String[] array = new String[]{tmp_myemail, tmp_opemail};
                Arrays.sort(array);

                tmpchatname = array[0] + "," + array[1];
                //만약 채팅방이 존재하면 안하면
                databaseReference.child(tmpchatname).child("name1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
                        } else {
                            //존재 안하면 채팅방 새로 만들기
                            databaseReference.child(tmpchatname).setValue("");
                            databaseReference.child(tmpchatname).child("name1").setValue(myname);
                            databaseReference.child(tmpchatname).child("name2").setValue(profile_name);
                        }
                        Intent tochat = new Intent(ProfileActivity.this, ChattingActivity.class);
                        tochat.putExtra("ChatName", tmpchatname);
                        if(!profile_name.equals("")) {
                            tochat.putExtra("opname", profile_name);////////////////
                        }
                        startActivity(tochat);
                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });



            }
        });
    }
}
