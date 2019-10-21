package au.edu.sydney.comp5216.cakefactory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ViewArticleAcitivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        BottomNavigationView navView = findViewById(R.id.nav_view);

    }
}
