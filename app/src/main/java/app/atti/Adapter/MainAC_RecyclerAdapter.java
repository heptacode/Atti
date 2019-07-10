package app.atti.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import app.atti.Activities.Main.MainActivity;
import app.atti.Activities.Main.MainActivity_Detail;
import app.atti.Object.MainAC_Post;
import app.atti.R;

import java.util.ArrayList;

public class MainAC_RecyclerAdapter extends RecyclerView.Adapter<MainAC_RecyclerAdapter.ViewHolder> {
    ArrayList<MainAC_Post> items;
    String email;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content, date, name,like_num;
        LinearLayout like, LL_intent;
        ImageView korean;
        ImageView mainimg;
        ImageView likeimg,delimg;
        SharedPreferences mprefs = itemView.getContext().getSharedPreferences("Profile_Data",itemView.getContext().MODE_PRIVATE);


        public ViewHolder(View itemView) {
            super(itemView);
            delimg=itemView.findViewById(R.id.main_item_img_del);
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
            email = mprefs.getString("S_email","");
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
        if(items.get(position).getWriter_email().equals(email)) {
            holder.delimg.setVisibility(View.VISIBLE);
        }else{
            holder.delimg.setVisibility(View.GONE);
        }
        holder.delimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //데이터 삭제
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle("Atti");
                builder.setMessage("정말로 게시물을 삭제하시겠습니까?");
                builder.setNegativeButton("아니오", null);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //삭제하기!!
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("recommend").document(items.get(position).getID()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(holder.itemView.getContext(), "게시물을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                                ((MainActivity) holder.itemView.getContext()).ListLoading();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(holder.itemView.getContext(), "게시물 삭제 실패", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                builder.show();
            }
        });
        holder.LL_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //document 이름 값
                Intent maindetail = new Intent(holder.itemView.getContext(), MainActivity_Detail.class);
                maindetail.putExtra("ID", ID);
                holder.itemView.getContext().startActivity(maindetail);
            }
        });
        if(items.get(position).isI_like()){
            holder.likeimg.setImageResource(R.drawable.ic_liked);
        }else{
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
        holder.like_num.setText(""+like);

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                        JSONArray array = new JSONObject(task.getResult().getData()).getJSONArray("likes");
                                        for(int i=0;i<array.length();i++){//문자열 배열만큼 반복
                                            tmp.add(array.getString(i));
                                        }
                                        tmp.add(email);
                                        //서버로 전송
                                        db.collection("recommend").document(ID).update("likes",tmp);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    db.collection("recommend").document(ID).update("like", Integer.parseInt(task.getResult().get("like").toString()) + 1);
                                    items.get(position).setLike(Integer.parseInt(task.getResult().get("like").toString()) + 1);
                                    items.get(position).setI_like(true);
                                    MainAC_RecyclerAdapter.this.notifyDataSetChanged();
                                    holder.likeimg.setImageResource(R.drawable.ic_liked);

                                }else { //좋아요 과거에 눌렀다면
                                    //like_people에 자신 삭제
                                    ArrayList tmp = new ArrayList();
                                    try {
                                        JSONArray array = new JSONObject(task.getResult().getData()).getJSONArray("likes");
                                        for(int i=0;i<array.length();i++){//문자열 배열만큼 반복
                                            if(array.getString(i).equals(email)){//좋아요 삭제
                                                continue;
                                            }
                                            tmp.add(array.getString(i));
                                        }
                                        //서버로 전송
                                        db.collection("recommend").document(ID).update("likes",tmp);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    db.collection("recommend").document(ID).update("like", Integer.parseInt(task.getResult().get("like").toString()) - 1);
                                    items.get(position).setLike(Integer.parseInt(task.getResult().get("like").toString()) - 1);
                                    items.get(position).setI_like(false);
                                    MainAC_RecyclerAdapter.this.notifyDataSetChanged();
                                    holder.likeimg.setImageResource(R.drawable.ic_liked_x);

                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("좋아요","누르기 실패");
                            }
                        });
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
