package app.atti.Activities.Main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import app.atti.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity_Write extends AppCompatActivity {

    EditText et_title, et_content;
    ImageView write;
    AlertDialog.Builder builder;
    FirebaseFirestore db;
    Map<String, Object> user;
    ProgressDialog asyncDialog;
    String name;
    boolean korean;
    String ID;
    ArrayList<String> images, likes;
    ImageView prev;
    ImageView image_add;
    Uri filePath = null;
    SimpleDateFormat formatter;
    String filename;
    StorageReference storageReference;
    FirebaseStorage storage;
    boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__write);
        et_content = findViewById(R.id.main_write_content);
        et_title = findViewById(R.id.main_write_title);
        write = findViewById(R.id.main_write_write);
        image_add = findViewById(R.id.main_write_btn_float);
        prev = findViewById(R.id.main_write_previmg);

        asyncDialog = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        user = new HashMap<>();

        builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        images = new ArrayList<>();
        likes = new ArrayList<>();
        likes.add("");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://atti-core.appspot.com");

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clicked == true) {
                } else {
                    clicked = true;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clicked=false;
                        }
                    },5000);

                    SharedPreferences mprefs = getSharedPreferences("Profile_Data", MODE_PRIVATE);
                    korean = mprefs.getBoolean("S_korean", true);
                    name = mprefs.getString("S_name", "Error");
                    Date time = new Date(System.currentTimeMillis());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                    String getTime = sdf.format(time);

                    asyncDialog.setMessage("요청중입니다.");
                    asyncDialog.show();

                    if (filePath != null) {
                        formatter = new SimpleDateFormat("yyyyMMHH_mmss");
                        filename = formatter.format(time) + ".jpg";
                        images.add("https://firebasestorage.googleapis.com/v0/b/atti-core.appspot.com/o/images%2Frecommend%2F" + filename + "?alt=media");
                        StorageReference ref = storageReference.child("images/recommend/" + filename);

                        ref.putFile(filePath)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Log.e("이미지", "업로드 성공");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("이미지", "업로드 실패");
                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                        asyncDialog.setMessage("Uploaded " + ((int) progress + "%..."));
                                    }
                                });
                    }
                    if (images.size() == 0) {
                        images.add("");
                    }

                    user.put("date", getTime);
                    user.put("title", et_title.getText().toString());
                    user.put("desc", et_content.getText().toString());
                    user.put("images", images);
                    user.put("korean", korean);
                    user.put("like", 0);
                    user.put("likes", likes);
                    user.put("name", name);
                    db.collection("recommend")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    int int_id = Integer.parseInt(task.getResult().getDocuments().get(task.getResult().getDocuments().size() - 1).getId()) + 1;
                                    if (int_id < 10) {
                                        ID = "00" + int_id;
                                    } else if (int_id < 100) {
                                        ID = "0" + int_id;
                                    } else {
                                        ID = "" + int_id;
                                    }
                                    db.collection("recommend").document(ID)
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    asyncDialog.dismiss();
                                                    Toast.makeText(MainActivity_Write.this, "글 작성을 완료하였습니다.", Toast.LENGTH_SHORT).show();
                                                    setResult(RESULT_OK);
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    asyncDialog.dismiss();
                                                    Toast.makeText(MainActivity_Write.this, "서버에 문제가 생겼습니다. 잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                }
            }
        });

        image_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 123);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 123:
                    filePath = data.getData();
                    Log.e("경로", "uri: " + filePath);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        prev.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (!et_content.getText().toString().equals("") || !et_title.getText().toString().equals("")) {
            builder.setTitle("Atti");
            builder.setMessage("저장되지 않았습니다.\n정말로 나가시겠습니까?");
            builder.setNegativeButton("아니오", null);
            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
            builder.setCancelable(false);
        } else {
            finish();
        }
    }
}

