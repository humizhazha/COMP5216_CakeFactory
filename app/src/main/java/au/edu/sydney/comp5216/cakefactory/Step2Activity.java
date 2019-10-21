package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Step2Activity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step2);
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

        BottomNavigationView navView = findViewById(R.id.nav_view);

    }
    public void goNext(View view) {
        Intent intent = new Intent(Step2Activity.this, Step3Activity.class);
        startActivity(intent);

    }
    public void goBack(View view) {
        Intent intent = new Intent(Step2Activity.this, Step1Activity.class);
        startActivity(intent);
    }
}
