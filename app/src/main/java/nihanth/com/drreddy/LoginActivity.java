package nihanth.com.drreddy;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail;
    private EditText password;
    private Button login;
    private Button reg;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db ;
    private ProgressBar pbar;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail=(EditText) findViewById(R.id.regEmail);
        password =(EditText) findViewById(R.id.regPassword);
        login=(Button) findViewById(R.id.regSigninbtn);
        pbar=(ProgressBar) findViewById(R.id.regProgress);
        mAuth=FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String eid=loginEmail.getText().toString();
                String pwd=password.getText().toString();
                if(!TextUtils.isEmpty(eid) && !TextUtils.isEmpty(pwd))
                {

                    pbar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(eid,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            Log.i("err",mAuth.getCurrentUser().getUid());
                            final String uid=mAuth.getCurrentUser().getUid();
                            if(task.isSuccessful()){
                                db.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            if(!task.getResult().exists()){
                                                Map<String, Object> userMap = new HashMap<>();
                                                userMap.put("email",eid);
                                                userMap.put("name","");
                                                userMap.put("phone","");
                                                userMap.put("image"," ");
                                                db.collection("Users").document(uid).set(userMap).addOnCompleteListener(
                                                        new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    sendToHome(eid);
                                                                    Toast.makeText(LoginActivity.this, "Stored in Firestore ", Toast.LENGTH_LONG).show();

                                                                } else {
                                                                    String err = task.getException().getMessage();
                                                                    Toast.makeText(LoginActivity.this, "FIrestore Error:" + err, Toast.LENGTH_LONG).show();
                                                                }
                                                            }
                                                        });

                                            }
                                            else{
                                                sendToHome(eid);
                                            }
                                        }

                                    }
                                });



                            }
                            else
                            {
                                String error=task.getException().getMessage();
                                Toast.makeText(LoginActivity.this,"Error:"+error,Toast.LENGTH_LONG).show();
                            }

                            pbar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });
    }

    private void sendToHome(String eid){
        Intent mainintent=new Intent(LoginActivity.this,Catalogue.class);
        mainintent.putExtra("email",eid);
        startActivity(mainintent);
        finish();
    }}


