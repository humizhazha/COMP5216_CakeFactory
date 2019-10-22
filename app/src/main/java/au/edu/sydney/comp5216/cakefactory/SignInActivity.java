package au.edu.sydney.comp5216.cakefactory;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;

public class SignInActivity extends AppCompatActivity {
    TextView signUp;
    ImageView signIn;
    private FirebaseFirestore mFirestore;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        signUp = (TextView) findViewById(R.id.signUp);
        signIn = (ImageView) findViewById(R.id.signIn);
        FirebaseFirestore.setLoggingEnabled(true);
        // Initialize Firestore and the main RecyclerView
        initFirestore();
        startSignIn();


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(it);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

    }

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();
    }

//    private boolean shouldStartSignIn() {
//        return (!mViewModel.getIsSigningIn() && FirebaseAuth.getInstance().getCurrentUser() == null);
//    }

    private void startSignIn() {
        // Sign in with FirebaseUI
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(Collections.singletonList(
                        new AuthUI.IdpConfig.EmailBuilder().build()))
                .setIsSmartLockEnabled(false)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // Start sign in if necessary
//        if (shouldStartSignIn()) {
//            startSignIn();
//            return;
//        }
//    }

}
