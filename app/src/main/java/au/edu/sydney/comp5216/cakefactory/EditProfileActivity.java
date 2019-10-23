package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import model.User;

/**
 * User profile page
 * Show the profile of logged user from Cloud Firestore
 */
public class EditProfileActivity extends AppCompatActivity implements
        View.OnClickListener,
        EventListener<DocumentSnapshot> {

    private FirebaseFirestore mFirestore;
    private DocumentReference mUserRef;
    private ListenerRegistration mUserRegistration;

    String userId;

    private ImageView setting;
    private ImageView avatar;
    private TextView username;
    private TextView email;
    private TextView mobile;
    private LinearLayout usernameView;
    private LinearLayout emailView;;
    private LinearLayout mobileView;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Edit Profile");

        setting = findViewById(R.id.setting);
        setting.setVisibility(View.VISIBLE);

        avatar = findViewById(R.id.avatar);

        username = findViewById(R.id.usernameInput);
        email = findViewById(R.id.emailInput);
        mobile = findViewById(R.id.mobileInput);

        usernameView = findViewById(R.id.username);
        emailView = findViewById(R.id.email);
        mobileView = findViewById(R.id.mobile);

        findViewById(R.id.backArrow).setOnClickListener(this);
        findViewById(R.id.setting).setOnClickListener(this);
        findViewById(R.id.username).setOnClickListener(this);
        findViewById(R.id.email).setOnClickListener(this);
        findViewById(R.id.mobile).setOnClickListener(this);

        // Get user ID from extras
        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        userId = preferences.getString("user_id", "0");
        userId = "US8ef5moenRIrBHB5P7aySB8ssx2";
        if (userId == null) {
            throw new IllegalArgumentException("Must pass extra userId");
        }
        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);

        // Initialize Firestore
        mFirestore = FirebaseFirestore.getInstance();

        // Get reference to the recommendation
        mUserRef = mFirestore.collection("users").document(userId);
    }

    @Override
    public void onStart() {
        super.onStart();
        mUserRegistration = mUserRef.addSnapshotListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.backArrow:
                Intent intent = new Intent(EditProfileActivity.this,
                        MainActivity.class);
                intent.putExtra("Profile",true);
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
                break;
            case R.id.setting:
            case R.id.username:
                intent = new Intent(EditProfileActivity.this,
                        EditProfileDetailActivity.class);
                intent.putExtra("Username", username.getText());
                startActivity(intent);
                break;
            case R.id.email:
                intent = new Intent(EditProfileActivity.this,
                        EditProfileDetailActivity.class);
                intent.putExtra("Email", email.getText());
                startActivity(intent);
                break;
            case R.id.mobile:
                intent = new Intent(EditProfileActivity.this,
                        EditProfileDetailActivity.class);
                intent.putExtra("Mobile", mobile.getText());
                startActivity(intent);
                break;
        }
    }

    /**
     * Listener for the Restaurant document ({@link #mUserRef}).
     */
    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        User user = snapshot.toObject(User.class);
        Glide.with(avatar.getContext())
                .load(user.getAvatar())
                .into(avatar);
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        mobile.setText(user.getMobile());
    }
}
