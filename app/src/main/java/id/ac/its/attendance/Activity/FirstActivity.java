package id.ac.its.attendance.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.TextView;

import id.ac.its.attendance.R;

public class FirstActivity extends AppCompatActivity {
    private TextView title;
    private CardView cardView1;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        title = (TextView) findViewById(R.id.tv_title);
        cardView1 = (CardView) findViewById(R.id.btn_login);
        cardView1.setOnClickListener(action);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LuckiestGuy.ttf");
        title.setTypeface(custom_font);
        cek();

    }

    View.OnClickListener action = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btn_login:
                    intent = new Intent(FirstActivity.this,LoginActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private void cek() {
        String[] permission = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.CLEAR_APP_CACHE,
                android.Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                android.Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_PHONE_NUMBERS,
                Manifest.permission.READ_SMS
        };

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),permission[0])== PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this.getApplicationContext(),permission[1])==PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this.getApplicationContext(),permission[2])==PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(),permission[3])==PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this.getApplicationContext(),permission[4])==PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this.getApplicationContext(),permission[5])==PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this.getApplicationContext(),permission[6])==PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this.getApplicationContext(),permission[7])==PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this.getApplicationContext(),permission[8])==PackageManager.PERMISSION_GRANTED)
        {

        }
        else
        {
            ActivityCompat.requestPermissions(FirstActivity.this,permission,1);
        }
    }
}
