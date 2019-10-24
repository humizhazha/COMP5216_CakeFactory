package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Sign up Activity
 * User register by input email, password and mobile phone
 * Click Sign in to enter our application
 */
public class SignUpActivity extends AppCompatActivity {
    EditText email_edit;
    EditText password_edit;
    EditText mobile_edit;
    EditText repassword_edit;
    String email;
    String password;
    String mobile;
    String repassword;
    ImageView signIn;
    private FirebaseAuth mAuth;
    private static final String TAG = SignUpActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        signIn = (ImageView) findViewById(R.id.signIn);
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);
        setUpListener();
    }

    /**
     * Check if any edittext field is empty
     *
     * @return if edittext is empty, return true.
     */
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    /**
     * Set up SignIn button Listener
     */
    private void setUpListener() {

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_edit = (EditText) findViewById(R.id.email);
                password_edit = (EditText) findViewById(R.id.password);
                mobile_edit = (EditText) findViewById(R.id.mobile);
                repassword_edit = (EditText) findViewById(R.id.repassword);

                //If EditText is empty, warn the user
                if (isEmpty(email_edit) || isEmpty(password_edit) || isEmpty(mobile_edit) || isEmpty(repassword_edit)) {
                    Toast.makeText(SignUpActivity.this, "Email or password can't be blank!", Toast.LENGTH_SHORT).show();
                } else {
                    email = email_edit.getText().toString();
                    password = password_edit.getText().toString();
                    mobile = mobile_edit.getText().toString();
                    repassword = repassword_edit.getText().toString();

                    //If password and repeated password is same, warn the user
                    //Else create new account in Firebase
                    if(password.equals(repassword)){
                        startSignIn(email, password);
                    }else{
                        Toast.makeText(SignUpActivity.this, "Password is not same! ", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    /**
     * Create a new account in Firebase
     * @value email: user's email
     * @value password: user's password
     */
    private void startSignIn(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent it = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(it);
                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    /**
     * Set up Go back button Listener, go back to SignIn page
     */
    private void goToSignIn(View view){
        Intent it = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(it);
    }
}
