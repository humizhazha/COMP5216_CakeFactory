package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Step3Activity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step3);
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

        BottomNavigationView navView = findViewById(R.id.nav_view);

    }
    public void goNext(View view) {
        Intent intent = new Intent(Step3Activity.this, DecorationActivity.class);
        startActivity(intent);

    }
    public void goBack(View view) {
        Intent intent = new Intent(Step3Activity.this, Step2Activity.class);
        startActivity(intent);

    }
}
