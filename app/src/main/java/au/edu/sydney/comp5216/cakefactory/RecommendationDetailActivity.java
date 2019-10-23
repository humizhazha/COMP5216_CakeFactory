package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import model.Recommendation;

/**
 * The detailed page for selected recommendation
 */
public class RecommendationDetailActivity extends AppCompatActivity implements
        View.OnClickListener,
        EventListener<DocumentSnapshot> {

    public static final String KEY_RECOMMENDATION_ID = "key_recommendation_id";

    //Firebase
    private FirebaseFirestore mFirestore;
    private DocumentReference mRecommendationRef;
    private ListenerRegistration mRestaurantRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_detail);

        findViewById(R.id.backArrow).setOnClickListener(this);

        // Get recommendation ID from extras
        String recommendationId = getIntent().getExtras().getString(KEY_RECOMMENDATION_ID);
        if (recommendationId == null) {
            throw new IllegalArgumentException("Must pass extra " + KEY_RECOMMENDATION_ID);
        }

        // Initialize Firestore
        mFirestore = FirebaseFirestore.getInstance();

        // Get reference to the recommendation
        mRecommendationRef = mFirestore.collection("recommendations")
                .document(recommendationId);
    }

    @Override
    public void onStart() {
        super.onStart();
        mRestaurantRegistration = mRecommendationRef.addSnapshotListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() ==  R.id.backArrow)  {
            startActivity(new Intent(RecommendationDetailActivity.this,
                    RecommendationActivity.class));
        }
    }

    /**
     * Listener for the Restaurant document ({@link #mRecommendationRef}).
     */
    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        onRecommendationLoaded(snapshot.toObject(Recommendation.class));
    }

    private void onRecommendationLoaded(Recommendation recommendation) {
        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText(recommendation.getTopic());
    }
}
