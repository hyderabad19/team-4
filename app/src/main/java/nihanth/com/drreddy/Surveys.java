package nihanth.com.drreddy;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Surveys extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveys);
    }

    public void survey1(View view) {
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSdH8eb1qP_sfperli18zDJbwGP3n47OVxJca8cd_0jgeN-ivQ/viewform?vc=0&c=0&w=1/"));
        startActivity(browserIntent);
    }

    public void survey2(View view) {
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSegAlCWUvm-I6Tal1MvGIisyhXPnlI2qWGoV4s1LvA6nT36vA/viewform?vc=0&c=0&w=1"));
        startActivity(browserIntent);
    }
}
