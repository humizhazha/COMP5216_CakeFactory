package au.edu.sydney.comp5216.cakefactory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import android.content.Intent;
import android.view.View;

import custom_font.ExpandableHeightListView;

public class Step0Activity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_design);

        BottomNavigationView navView = findViewById(R.id.nav_view);

    }
    public void startDesign(View view) {
        Intent intent = new Intent(Step0Activity.this, Step1Activity.class);
//        if (intent != null) {
//            // put "extras" into the bundle for access in the edit activity
//            startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
//            itemsAdapter.notifyDataSetChanged();
//            saveItemsToDatabase();
//        }
    }
}
