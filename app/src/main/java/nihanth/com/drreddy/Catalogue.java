package nihanth.com.drreddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Catalogue extends AppCompatActivity {
    private Button Button1;
    private Button Button2;
    private Button Button3;
    private Button Button4;
    private Button Button5;
    private Button Button6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);
        Button1=(Button) findViewById(R.id.button1);
        Button2=(Button) findViewById(R.id.button2);
        Button3=(Button) findViewById(R.id.button3);
        Button4=(Button) findViewById(R.id.button4);
        Button5=(Button) findViewById(R.id.button5);
        Button6=(Button) findViewById(R.id.button6);

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextint=new Intent(Catalogue.this,MainActivity.class);
                nextint.putExtra("course","Accounting");
                startActivity(nextint);
            }
        });

        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextint=new Intent(Catalogue.this,MainActivity.class);
                nextint.putExtra("course","Aptitude");
                startActivity(nextint);
            }
        });

        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextint=new Intent(Catalogue.this,MainActivity.class);
                nextint.putExtra("course","Coding");
                startActivity(nextint);
            }
        });

        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextint=new Intent(Catalogue.this,MainActivity.class);
                nextint.putExtra("course","Accounting");
                startActivity(nextint);
            }
        });


    }
}
