package au.edu.sydney.comp5216.cakefactory;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;

/**
 * MainActivity page
 * Include bottom nav bar and fragment transfer
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup bottom navigation view for 3 fragment pages
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Bundle extras = getIntent().getExtras();

        if(extras != null && extras.containsKey("Profile")) {
            loadFragment(new ProfileFragment());
        } else if (extras != null && extras.containsKey("Design")){
            loadFragment(new Step0Activity());
        } else {
            // Default goes to Discovery fragment
            loadFragment(new Discovery());
        }
    }

    /**
     * Setup fragment transaction listen for bottom navigation bar
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_discover:
                    loadFragment(new Discovery());
                    return true;
                case R.id.navigation_design:
                    loadFragment(new Step0Activity());
                    return true;
                case R.id.navigation_me:
                    loadFragment(new ProfileFragment());
                    return true;
            }
            return false;
        }
    };

    /**
     * Loading fragment into container
     * @param fragment
     */
    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}


