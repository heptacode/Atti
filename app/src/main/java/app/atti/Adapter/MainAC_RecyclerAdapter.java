package app.atti.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.atti.Activities.Main.MainActivity_Detail;
import app.atti.Activities.Profile.ProfileActivity;
import app.atti.Object.MainAC_Post;
import app.atti.R;

public class MainAC_RecyclerAdapter extends RecyclerView.Adapter<MainAC_RecyclerAdapter.ViewHolder> {
    ArrayList<MainAC_Post> items;
    String email;
    JSONArray array, array1;
    boolean pressed = false;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content, date, name, like_num;
        LinearLayout like, LL_intent;
        ImageView korean;
        ImageView mainimg;
        ImageView likeimg;
        SharedPreferences mprefs = itemView.getContext().getSharedPreferences("Profile_Data", itemView.getContext().MODE_PRIVATE);
        RelativeLayout RL;


        public ViewHolder(View itemView) {
            super(itemView);
            RL = itemView.findViewById(R.id.main_item_relative);
            likeimg = itemView.findViewById(R.id.main_item_like_img);
            like_num = itemView.findViewById(R.id.main_item_like_num);
            LL_intent = itemView.findViewById(R.id.main_item_LL_intent);
            content = itemView.findViewById(R.id.main_item_tv_content);
            title = itemView.findViewById(R.id.main_item_tv_title);
            date = itemView.findViewById(R.id.main_item_tv_date);
            name = itemView.findViewById(R.id.main_item_tv_name);
            like = itemView.findViewById(R.id.main_item_LL_like);
            korean = itemView.findViewById(R.id.main_item_img_korean);
            mainimg = itemView.findViewById(R.id.main_item_img_mainimg);
            email = mprefs.getString("S_email", "");
        }
    }

    public MainAC_RecyclerAdapter(ArrayList<MainAC_Post> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MainAC_RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.recycler_view_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final String ID = items.get(position).getID();

        holder.RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ProfileActivity.class);
                intent.putExtra("toprofile_email", items.get(position).getWriter_email());
                holder.itemView.getContext().startActivity(intent);
            }
        });


        holder.LL_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //document 이름 값
                Intent maindetail = new Intent(holder.itemView.getContext(), MainActivity_Detail.class);
                maindetail.putExtra("ID", ID);
                maindetail.putExtra("Writer_email",items.get(position).getWriter_email());
//                holder.itemView.getContext().startActivity(maindetail);
                ((Activity)holder.itemView.getContext()).startActivityForResult(maindetail,1233);
            }
        });
        if (items.get(position).isI_like()) {
            holder.likeimg.setImageResource(R.drawable.ic_liked);

        } else {
            holder.likeimg.setImageResource(R.drawable.ic_liked_x);
        }

        String title = items.get(position).getTitle();
        String content = items.get(position).getContent();
        String date = items.get(position).getDate();
        String name = items.get(position).getWriter();
        int like = items.get(position).getLike();

        boolean korean = items.get(position).isKorean();
        String imgurl = items.get(position).getImage_url();
        holder.title.setText(title);
        holder.content.setText(content);
        holder.date.setText(date);
        holder.name.setText(name);
        holder.like_num.setText("" + like);

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pressed) {
                    pressed=true;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pressed=false;
                        }
                    },500);

                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("recommend").document(ID)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (!items.get(position).isI_like()) {  //좋아요 과거에 안눌었다면
                                        //like_people에 자신 추가
                                        ArrayList tmp = new ArrayList();
                                        try {
                                            array = new JSONObject(task.getResult().getData()).getJSONArray("likes");
                                            for (int i = 0; i < array.length(); i++) {//문자열 배열만큼 반복
                                                tmp.add(array.getString(i));
                                            }
                                            tmp.add(email);
                                            //서버로 전송
                                            db.collection("recommend").document(ID).update("likes", tmp);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
//                                    items.get(position).setLike(Integer.parseInt(task.getResult().get("like").toString()) + 1);
                                        items.get(position).setLike(tmp.size() - 1);
                                        Log.e("array", tmp.size() - 1 + "");
                                        items.get(position).setI_like(true);
                                        MainAC_RecyclerAdapter.this.notifyDataSetChanged();
                                        holder.likeimg.setImageResource(R.drawable.ic_liked);

                                    } else { //좋아요 과거에 눌렀다면
                                        //like_people에 자신 삭제
                                        ArrayList tmp = new ArrayList();
                                        try {
                                            array1 = new JSONObject(task.getResult().getData()).getJSONArray("likes");
                                            for (int i = 0; i < array1.length(); i++) {//문자열 배열만큼 반복
                                                if (array1.getString(i).equals(email)) {//좋아요 삭제
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
                                        items.get(position).setLike(tmp.size() - 1);
                                        Log.e("array", tmp.size() - 1 + "");

                                        items.get(position).setI_like(false);
                                        MainAC_RecyclerAdapter.this.notifyDataSetChanged();
                                        holder.likeimg.setImageResource(R.drawable.ic_liked_x);

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

        Glide.with(holder.itemView.getContext()).setDefaultRequestOptions(new RequestOptions().override(400)).load(imgurl).into(holder.mainimg);

        if (korean) {
            holder.korean.setImageResource(R.drawable.ic_korean_flag);
        } else {
            //외국인용 국기
            holder.korean.setImageResource(R.drawable.ic_foreigner_flag);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
