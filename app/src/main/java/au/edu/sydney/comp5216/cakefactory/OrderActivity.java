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
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import adapter.OrderAdapter;

/**
 * Order page
 */
public class OrderActivity extends AppCompatActivity implements
        View.OnClickListener,
        OrderAdapter.OnOrderSelectedListener {

    private OrderAdapter mAdapter;
    private RecyclerView recyclerView;

    // Firebase
    private FirebaseFirestore mFirestore;
    private ListenerRegistration mOrderRef;
    private ListenerRegistration mDesignRef;
    private Query mQuery;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("My Orders");

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
    }

    /**
     * Initialize Firebase
     */
    private void initFireStore() {
        mFirestore = FirebaseFirestore.getInstance();

        // Get all designs
        mQuery = mFirestore.collection("orders")
                .whereEqualTo("user", userId)
                .orderBy("datetime", Query.Direction.DESCENDING);
    }

    /**
     * Initialize main RecyclerView
     */
    private void initRecyclerView() {
        mAdapter = new OrderAdapter(mQuery, this) {};
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
    public void OnOrderSelectedListener(DocumentSnapshot order) {

        // Go to the details page for the selected recommendation
//        Intent intent = new Intent(OrderActivity.this, RecommendationActivity.class);
//        intent.putExtra(OrderDetailActivity.KEY_ORDER_ID, order.getId());

//        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backArrow) {
            Intent intent = new Intent(OrderActivity.this, MainActivity.class);
            intent.putExtra("Profile",true);
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            startActivity(intent);
        }
    }
}
