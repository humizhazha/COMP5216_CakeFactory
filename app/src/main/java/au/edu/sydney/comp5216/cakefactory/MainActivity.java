package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import custom_font.ExpandableHeightListView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private ExpandableHeightListView listview;
    private ArrayList<Article> Bean;
    private ListBaseAdapter baseAdapter;


    private int[] IMAGE1 = {R.drawable.newsname1, R.drawable.newsname1, R.drawable.newsname1};
    private int[] IMAGE2 = {R.drawable.img1, R.drawable.img1, R.drawable.img1};
    private int[] IMAGE3 = {R.drawable.more, R.drawable.more, R.drawable.more};
    private String[] NEWSNAME = {"Fox News .", "Fox News .", "Fox News ."};
    private String[] TITLE = {"1 day ago", "1 day ago", "1 day ago"};
    private String[] NEWS = {"Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous. Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous",
            "Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous. Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous",
            "Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous. Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous"};
    private String[] NEWSSUB = {"Why even a President Trump couldn’t make Apple manufacture iPhone in the state.","Why even a President Trump couldn’t make Apple manufacture iPhone in the state.",
            "Why even a President Trump couldn’t make Apple manufacture iPhone in the state."};
    private String[] INTREST = {"You've shown interest in iPhone","You've shown interest in iPhone","You've shown interest in iPhone"};



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_discover:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_design:
                    Intent it = new Intent(MainActivity.this,Step0Activity.class);
                    startActivity(it);
                    return true;
                case R.id.navigation_me:
                    it = new Intent(MainActivity.this, Profile.class);
                    startActivity(it);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        listview = (ExpandableHeightListView)findViewById(R.id.listview);

        Bean = new ArrayList<Article>();

        for (int i= 0; i< IMAGE1.length; i++){

            Article bean = new Article(IMAGE1[i], IMAGE2[i], IMAGE3[i], NEWSNAME[i], TITLE[i], NEWS[i], NEWSSUB[i], INTREST[i]);
            Bean.add(bean);

        }

        baseAdapter = new ListBaseAdapter(MainActivity.this, Bean) {
        };

        listview.setAdapter(baseAdapter);
    }

}
