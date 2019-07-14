package nihanth.com.drreddy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class update_profile extends AppCompatActivity {


    private CircleImageView setupimage;
    private Uri mainimage = null;
    private EditText setupName;
    private EditText setupphone;
    private TextView usermail;
    private Button setupButton;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private ProgressBar ibar;
    private FirebaseFirestore db ;
    private String eid;
    private String down_uri;
    private Boolean isChanged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Intent intent=getIntent();
        eid=intent.getStringExtra("email");
        /*android.support.v7.widget.Toolbar setupToolbar = findViewById(R.id.setupToolbar);
        setSupportActionBar(setupToolbar);
        getSupportActionBar().setTitle("Account Setup");*/
        setupimage = findViewById(R.id.setupimage);
        setupName=findViewById(R.id.username);
        setupphone=findViewById(R.id.userphone);
        setupButton=findViewById(R.id.update_button);
        usermail=findViewById(R.id.usermail);
        ibar=findViewById(R.id.ibar);

        mStorageRef= FirebaseStorage.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();


        db = FirebaseFirestore.getInstance();
        db.collection("Users").document(eid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        Toast.makeText(update_profile.this,"Data Exists:",Toast.LENGTH_LONG).show();

                        String email=task.getResult().getString("email");
                        String phone=task.getResult().getString("phone");
                        String name=task.getResult().getString("name");
                        String image=task.getResult().getString("image");

                        usermail.setText(email);
                        setupphone.setText(phone);
                        setupName.setText(name);
                        mainimage=Uri.parse(image);
                        RequestOptions placeholderRequest=new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.default_image);
                        Glide.with(update_profile.this).setDefaultRequestOptions(placeholderRequest).load(image).into(setupimage);
                    }
                    else{

                        Toast.makeText(update_profile.this,"Data doesn't exist:",Toast.LENGTH_LONG).show();


                    }
                    ibar.setVisibility(View.INVISIBLE);
                    setupButton.setEnabled(true);
                }
                else {
                    String err=task.getException().getMessage();
                    Toast.makeText(update_profile.this,"FIrestore  Retrieve Error:"+err,Toast.LENGTH_LONG).show();

                }
            }
        });

        setupimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(update_profile.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(update_profile.this, "PERMISSION DENIED", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(update_profile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);


                    } else {
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1,1)
                                .start(update_profile.this);

                    }
                }

            }


        });
        setupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uname=setupName.getText().toString();
                final String uphone=setupphone.getText().toString();
                if (!TextUtils.isEmpty(uname) && mainimage != null) {
                    if (isChanged) {

                        ibar.setVisibility(View.VISIBLE);
                        final String uid = mAuth.getCurrentUser().getUid();
                        final StorageReference image_path = mStorageRef.child("profile_pics").child(uid + ".jpg");
                        UploadTask uploadTask = image_path.putFile(mainimage);
                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }

                                // Continue with the task to get the download URL
                                return image_path.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    storetoFirestore(task, uname,uphone);

                                }
                            }
                        });
                    } else {
                        storetoFirestore(null, uname,uphone);
                    }
                }
            }
        });



    }
    private void storetoFirestore(@NonNull Task<Uri> task,String uname,String uphone){
        Uri downloadUri;
        if(task!=null) {
            downloadUri = task.getResult();
        }
        else{
            downloadUri=mainimage;
        }
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("email", eid);
        userMap.put("name", uname);
        userMap.put("image", downloadUri.toString());
        userMap.put("phone",uphone);

        db.collection("Users").document(eid).set(userMap).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(update_profile.this, "Settings finally updated", Toast.LENGTH_LONG).show();

                            ibar.setVisibility(View.INVISIBLE);
                        } else {
                            String err = task.getException().getMessage();
                            Toast.makeText(update_profile.this, "FIrestore Error:" + err, Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mainimage = result.getUri();
                setupimage.setImageURI(mainimage);
                isChanged=true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(update_profile.this, "QWERTYUIOP", Toast.LENGTH_LONG).show();

            }
        }
    }

}
