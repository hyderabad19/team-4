package nihanth.com.drreddy;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private android.support.v7.widget.Toolbar mainToolbar;
    private FloatingActionButton addpostButton;
    private FirebaseFirestore db;
    private String curid;
    private BottomNavigationView mainbottomNav;
    private GridView simpleGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setContentView(R.layout.activity_main);
        db= FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();
        mainToolbar=(android.support.v7.widget.Toolbar) findViewById(R.id.main_toolbar);
       // simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView

    }
}
