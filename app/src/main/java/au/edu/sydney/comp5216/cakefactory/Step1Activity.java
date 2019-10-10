package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Step1Activity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step1);

        BottomNavigationView navView = findViewById(R.id.nav_view);

    }
    public void goNext(View view) {
        Intent intent = new Intent(Step1Activity.this, Step2Activity.class);
        startActivity(intent);
//        if (intent != null) {
//            // put "extras" into the bundle for access in the edit activity
//            startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
//            itemsAdapter.notifyDataSetChanged();
//            saveItemsToDatabase();
//        }
    }
}
