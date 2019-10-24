package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Edit user profile page
 * Edit a single field of user profile and save to Google Cloud Firestore
 */
public class EditProfileDetailActivity extends AppCompatActivity implements
        View.OnClickListener {

    private FirebaseFirestore mFirestore;
    private FirebaseUser user;
    private AuthCredential credential;

    private TextView done;
    private EditText input;
    private EditText input1;
    private EditText input2;
    private LinearLayout buttons;
    private Button save;
    private Button notSave;
    private Button cancel;

    private String toobarText;
    private String field;
    private String value;
    private String userId;
    private String oldPass;
    private String newPass;
    private Boolean isChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_detail);

        TextView toolbar = findViewById(R.id.toolbar);

        // Get extra text
        Bundle extras = getIntent().getExtras();

        if(extras != null && extras.containsKey("Username")) {
            toobarText = "Username";
            field = "username";
            value = extras.getString("Username");
        } else if (extras != null && extras.containsKey("Email")){
            toobarText = "Email";
            field = "email";
            value = extras.getString("Email");
        } else if (extras != null && extras.containsKey("Mobile")){
            toobarText = "Mobile";
            field = "mobile";
            value = extras.getString("Mobile");
        } else {
            toobarText = "Password";
            field = "password";
            value = extras.getString("Password");
        }
        toolbar.setText("Edit " + toobarText);

        done = findViewById(R.id.done);
        done.setVisibility(View.VISIBLE);
        input = findViewById(R.id.input);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        buttons = findViewById(R.id.buttons);
        buttons.setVisibility(View.INVISIBLE);
        save = findViewById(R.id.save);
        notSave = findViewById(R.id.notSave);
        cancel = findViewById(R.id.cancel);

        if(!field.equals("password")){
            input.setText(value);
            input.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    isChanged = true;
                    done.setTextColor(Color.WHITE);
                    done.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            updateDatabase(input.getText().toString());
                            startActivity(new Intent(EditProfileDetailActivity.this,
                                    EditProfileActivity.class));
                        }
                    });
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });
        } else {
            input.setVisibility(View.GONE);
            input1.setVisibility(View.VISIBLE);
            input2.setVisibility(View.VISIBLE);

            input2.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    oldPass =  input1.getText().toString();
                    newPass =  input2.getText().toString();

                    isChanged = true;
                    done.setTextColor(Color.WHITE);
                    done.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if(oldPass.equals(newPass)){
                                Toast.makeText(EditProfileDetailActivity.this, "New password must be different from old password",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }

                            validatePassword(oldPass, newPass);
                        }
                    });
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });
        }

        findViewById(R.id.backArrow).setOnClickListener(this);
        save.setOnClickListener(this);
        notSave.setOnClickListener(this);
        cancel.setOnClickListener(this);

        // Get user ID from extras
        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        userId = preferences.getString("user_id", "0");

        if (userId == null) {
            throw new IllegalArgumentException("Must pass extra userId");
        }

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);

        // Initialize Firestore
        mFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.backArrow:
                // Confirm popup to cancel editing
                if (isChanged) {
                    buttons.setVisibility(View.VISIBLE);
                } else {
                    startActivity(new Intent(EditProfileDetailActivity.this,
                            EditProfileActivity.class));
                }
                break;
            case R.id.save:
                if(!field.equals("password")) {
                    updateDatabase(input.getText().toString());
                    startActivity(new Intent(EditProfileDetailActivity.this,
                            EditProfileActivity.class));
                } else {
                    if(oldPass.equals(newPass)){
                        Toast.makeText(EditProfileDetailActivity.this, "New password must be different from old password",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    validatePassword(oldPass, newPass);
                }
                break;
            case R.id.notSave:
                startActivity(new Intent(EditProfileDetailActivity.this,
                        EditProfileActivity.class));
                break;
            default:
                buttons.setVisibility(View.INVISIBLE);
                break;
        }
    }

    /**
     * Update field of 'users' collection
     */
    private void updateDatabase(String newValue) {
        FirebaseFirestore.setLoggingEnabled(true);
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mFirestore
                .collection("users")
                .document(userId)
                .update(field, newValue);
    }

    private void validatePassword(String prePass, final String newPass) {
                    user = FirebaseAuth.getInstance().getCurrentUser();
            credential = EmailAuthProvider
                    .getCredential(value, prePass);

            // Prompt the user to re-provide their sign-in credentials
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(EditProfileDetailActivity.this, "Password has be updated, Please re-login",
                                                    Toast.LENGTH_SHORT).show();

                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                public void run() {
                                                    //Sign-out
                                                    FirebaseAuth.getInstance().signOut();
                                                    Intent it = new Intent(EditProfileDetailActivity.this, SignInActivity.class);
                                                    startActivity(it);
                                                }
                                            }, 1000);
                                        } else {
                                            Toast.makeText(EditProfileDetailActivity.this, "Error password not updated",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(EditProfileDetailActivity.this, "Old Password wrong",
                                        Toast.LENGTH_SHORT).show();
                                input1.setHintTextColor(Color.RED);
                            }
                        }
                    });
    }
}
