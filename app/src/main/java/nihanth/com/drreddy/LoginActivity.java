package nihanth.com.drreddy;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail;
    private EditText password;
    private Button login;
    private Button reg;
    private FirebaseAuth mAuth;
    private ProgressBar pbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail=(EditText) findViewById(R.id.regEmail);
        password =(EditText) findViewById(R.id.regPassword);
        login=(Button) findViewById(R.id.regSigninbtn);
        pbar=(ProgressBar) findViewById(R.id.regProgress);
        mAuth=FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eid=loginEmail.getText().toString();
                String pwd=password.getText().toString();
                if(!TextUtils.isEmpty(eid) && !TextUtils.isEmpty(pwd))
                {
                    pbar.setVisibility(View.INVISIBLE);
                    mAuth.signInWithEmailAndPassword(eid,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {

                            if(task.isSuccessful()){
                                Log.i("dsjfdsgh","hzdgvhdschXZBjb");

                                sendToHome();

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

    private void sendToHome(){
        Intent mainintent=new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(mainintent);
        finish();
    }}

