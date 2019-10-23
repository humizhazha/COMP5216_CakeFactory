package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Edit user profile page
 * Edit a single field of user profile and save to Google Cloud Firestore
 */
public class EditProfileDetailActivity extends AppCompatActivity implements
        View.OnClickListener {

    private FirebaseFirestore mFirestore;

    private TextView done;
    private EditText input;
    private LinearLayout buttons;
    private Button save;
    private Button notSave;
    private Button cancel;

    private String toobarText;
    private String field;
    private String value;
    private String userId;
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
        } else {
            toobarText = "Mobile";
            field = "mobile";
            value = extras.getString("Mobile");
        }
        toolbar.setText("Edit " + toobarText);

        done = findViewById(R.id.done);
        done.setVisibility(View.VISIBLE);
        input = findViewById(R.id.input);
        input.setText(value);
        buttons = findViewById(R.id.buttons);
        buttons.setVisibility(View.INVISIBLE);
        save = findViewById(R.id.save);
        notSave = findViewById(R.id.notSave);
        cancel = findViewById(R.id.cancel);


        findViewById(R.id.backArrow).setOnClickListener(this);
        save.setOnClickListener(this);
        notSave.setOnClickListener(this);
        cancel.setOnClickListener(this);

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

        input.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                isChanged = true;
                done.setTextColor(Color.WHITE);
                done.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        save = findViewById(R.id.save);
                        notSave = findViewById(R.id.notSave);
                        cancel = findViewById(R.id.cancel);
                        updateDatabase(input.getText().toString());
                        startActivity(new Intent(EditProfileDetailActivity.this,
                                EditProfileActivity.class));
                    }
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
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
                updateDatabase(input.getText().toString());
                startActivity(new Intent(EditProfileDetailActivity.this,
                        EditProfileActivity.class));
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
}
