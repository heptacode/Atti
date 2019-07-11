package app.atti.Activities.Profile.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.atti.Adapter.MainAC_RecyclerAdapter;
import app.atti.Object.MainAC_Post;
import app.atti.R;

public class RecommendFragment extends Fragment {

    ArrayList<MainAC_Post> items = new ArrayList<>();
    MainAC_RecyclerAdapter madapter;
    RecyclerView rcv;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private JSONObject jsonObject;
    String db_date, db_content, db_writer, db_title, db_image0, db_ID;
    int db_like;
    boolean db_korean, db_i_like = false;
    private String db_writer_email;
    String email;

    public RecommendFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recommend, null);
        rcv = v.findViewById(R.id.frag_rec_rcv);

        if(getArguments() != null){
            email = getArguments().getString("fragment_email");
        }else{
            Toast.makeText(getContext(), "데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        ListLoading();
        madapter = new MainAC_RecyclerAdapter(items);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(madapter);


        return v;
    }

    public void ListLoading() {
        items.clear();
        db.collection("recommend")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                JSONObject tmp = new JSONObject(document.getData());
                                try {
                                    if (tmp.getString("email").equals(email)) {
                                        try {
                                            jsonObject = new JSONObject(document.getData());
                                            db_image0 = jsonObject.getJSONArray("images").getString(0);
                                            db_writer = jsonObject.getString("name");
                                            db_date = jsonObject.getString("date");
                                            db_content = jsonObject.getString("desc");
                                            db_korean = jsonObject.getBoolean("korean");
                                            db_title = jsonObject.getString("title");
                                            db_like = jsonObject.getJSONArray("likes").length()-1;
                                            db_writer_email = jsonObject.getString("email");

                                            for (int i = 0; i < jsonObject.getJSONArray("likes").length(); i++) {
                                                if (jsonObject.getJSONArray("likes").get(i).equals(email)) {
                                                    db_i_like = true;
                                                }
                                            }
                                            db_ID = document.getId();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        items.add(new MainAC_Post(db_title, db_date, db_writer, db_content, db_image0, db_like, db_ID, db_korean, db_i_like, db_writer_email));
                                        madapter.notifyDataSetChanged();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Log.e("DB", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
