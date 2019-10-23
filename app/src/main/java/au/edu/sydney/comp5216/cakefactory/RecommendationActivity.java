package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
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

import adapter.RecommendationAdapter;

public class RecommendationActivity extends AppCompatActivity implements
        View.OnClickListener,
        RecommendationAdapter.OnRecommendationSelectedListener {

    // Layout
    private RecommendationAdapter mAdapter;
    private RecyclerView recyclerView;

    // Firebase
    private FirebaseFirestore mFirestore;
    private Query mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Recommendations");

        findViewById(R.id.backArrow).setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler);

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);

        // Initialize Firestore and the main RecyclerView
        initFireStore();
        initRecyclerView();
    }

    /**
     * Initialize Firebase
     */
    private void initFireStore() {
        mFirestore = FirebaseFirestore.getInstance();

        // Get all recommendations
        mQuery = mFirestore.collection("recommendations");
    }

    /**
     * Initialize main RecyclerView
     */
    private void initRecyclerView() {
        mAdapter = new RecommendationAdapter(mQuery, this) {};
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
    public void onRecommendationSelected(DocumentSnapshot recommendation) {
        // Go to the details page for the selected recommendation
        Intent intent = new Intent(RecommendationActivity.this, RecommendationDetailActivity.class);
        intent.putExtra(RecommendationDetailActivity.KEY_RECOMMENDATION_ID, recommendation.getId());

        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backArrow) {
                Intent intent = new Intent(RecommendationActivity.this, MainActivity.class);
                intent.putExtra("Profile",true);
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
        }
    }
}
