package au.edu.sydney.comp5216.cakefactory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;

public class SignInActivity extends AppCompatActivity {
    TextView signUp;
    EditText email_edit;
    EditText password_edit;
    String email;
    String password;
    ImageView signIn;
    private FirebaseAuth mAuth;
    private static final String TAG = SignInActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        signUp = (TextView) findViewById(R.id.signUp);

        signIn = (ImageView) findViewById(R.id.signIn);
        FirebaseFirestore.setLoggingEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        setUpListener();

    }

    /**
     * Check if any edittext field is empty
     * @return if edittext is empty, return true.
     */
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    private void setUpListener(){
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
                email_edit = (EditText)findViewById(R.id.email);
                password_edit = (EditText)findViewById(R.id.password);
                if(isEmpty(email_edit)||isEmpty(password_edit)){
                    Toast.makeText(SignInActivity.this, "Email or password can't be blank!", Toast.LENGTH_SHORT).show();
                }else{
                    email = email_edit.getText().toString();
                    password = password_edit.getText().toString();
                    startSignIn(email,password);
                }

            }
        });

    }



    private void startSignIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("user_id", user.getUid());
                            editor.commit();

                            Intent it = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(it);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}
