package nihanth.com.drreddy;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Notify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
    }
    public void sendmail(View view) {
        //String mailto = "mailto:bob@example.org" + "?cc=" + "alice@example.com" +"&subject=" + Uri.encode(subject) +"&body=" + Uri.encode(bodyText);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:lohithareddymallakuntla@gmail.com"));
        try {
            startActivity(emailIntent);
        } catch (Exception e) {
            //TODO: Handle case where no email app is available
        }
    }

}
