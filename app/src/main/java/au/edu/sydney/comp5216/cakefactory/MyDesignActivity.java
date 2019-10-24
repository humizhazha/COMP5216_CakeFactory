package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import adapter.DesignAdapter;

public class MyDesignActivity extends AppCompatActivity implements
        View.OnClickListener,
        DesignAdapter.OnDesignSelectedListener {

    // Layout
    private DesignAdapter mAdapter;
    private RecyclerView recyclerView;

    private ArrayList<String> userDesigns = new ArrayList<>();

    // Firebase
    private FirebaseFirestore mFirestore;
    private Query mQuery;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_design);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("My Designs");

        findViewById(R.id.backArrow).setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler);

        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        userId = preferences.getString("user_id", "0");
        if (userId == null) {
            throw new IllegalArgumentException("Must pass extra userId");
        }

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);

        // Initialize Firestore and the main RecyclerView
        initFireStore();
        initRecyclerView();
//        readFromDatabase();
    }

    /**
     * Initialize Firebase
     */
    private void initFireStore() {
        mFirestore = FirebaseFirestore.getInstance();

        // Get all recommendations
        mQuery = mFirestore.collection("design")
                .whereArrayContains("user", userId);
    }

    /**
     * Initialize main RecyclerView
     */
    private void initRecyclerView() {
        mAdapter = new DesignAdapter(mQuery, this) {};
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Start listening for Firestore updates
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    @Override
    public void OnDesignSelectedListener(DocumentSnapshot design) {

        // Go to the details page for the selected recommendation
        Intent intent = new Intent(MyDesignActivity.this, DesignDetail.class);
        intent.putExtra(DesignDetail.KEY_DESIGN_ID, design.getId());

        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backArrow) {
            Intent intent = new Intent(MyDesignActivity.this, MainActivity.class);
            intent.putExtra("Profile",true);
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            startActivity(intent);
        }
    }
}
