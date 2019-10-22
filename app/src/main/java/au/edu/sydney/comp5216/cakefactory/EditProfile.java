package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Profile");

        ImageView goBack = findViewById(R.id.backArrow);
        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, MainActivity.class);
                intent.putExtra("Profile",true);
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });
    }
}
