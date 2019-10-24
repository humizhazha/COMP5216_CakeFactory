package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Settings");

        ImageView goBack = findViewById(R.id.backArrow);
        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, MainActivity.class);
                intent.putExtra("Profile", true);
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });

        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Sign-out
                FirebaseAuth.getInstance().signOut();
                Intent it = new Intent(Setting.this, SignInActivity.class);
                finish();
                startActivity(it);
            }
        });

    }

}
