package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.DesignModel;
import model.User;

/**
 * Design Detail Activity
 * Show design'detail information
 * Show selected shape, flavour, type and decoration design
 */
public class DesignDetail extends AppCompatActivity implements
        View.OnClickListener,
        EventListener<DocumentSnapshot> {

    public static final String KEY_DESIGN_ID = "key_design_id";
    private String designId;

    private DesignModel currentDesign;
    private User currentUser;
    TextView flavour;
    TextView shape;
    TextView type;
    TextView date;
    private Drawable strawberry;
    private Drawable cookie;
    private Drawable marshmallow;
    private Drawable beans;
    private Drawable candle;
    private Drawable sprinkling;
    private ViewGroup cakeBase;

    private FirebaseFirestore db;
    private DocumentReference mDesignRef;
    private ListenerRegistration mDesignReg;
    private CollectionReference design;

    private Boolean isProfile = false;
    private String datetime;
    private static final String TAG = DesignDetail.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.design_detail);

        //get drawable initilize with drawable
        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Design summary");

        Button save = findViewById(R.id.save);
        ImageView submit = findViewById(R.id.submit);

        //get drawable initialize with drawable
        strawberry = getResources().getDrawable(R.drawable.deco_strawberry);
        cookie = getResources().getDrawable(R.drawable.deco_cookie);
        marshmallow = getResources().getDrawable(R.drawable.deco_marshmallows);
        beans = getResources().getDrawable(R.drawable.deco_beans);
        candle = getResources().getDrawable(R.drawable.deco_candle);
        sprinkling = getResources().getDrawable(R.drawable.deco_sprinkles);
        cakeBase = findViewById(R.id.base);
        cakeBase = findViewById(R.id.base);

        //Initlize the textview
        flavour = findViewById(R.id.flavour_text);
        type = findViewById(R.id.type_text);
        shape = findViewById(R.id.shape_text);
        date = findViewById(R.id.date_text);
        flavour = findViewById(R.id.flavour_text);
        type = findViewById(R.id.type_text);
        shape = findViewById(R.id.shape_text);
        date = findViewById(R.id.date_text);

        initFirestore();

        findViewById(R.id.backArrow).setOnClickListener(this);

        LocalDate date2 = LocalDate.now(ZoneId.of("Australia/Sydney"));
        ZonedDateTime time = Instant.now().atZone(ZoneId.of("Australia/Sydney"));
        datetime = date2.toString() + " " + time.toString().substring(11, 19);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(KEY_DESIGN_ID)) {
            isProfile = true;
            save.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
            designId = extras.getString(KEY_DESIGN_ID);
            mDesignRef = db.collection("design").document(designId);
            db.collection("design").document(designId).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                currentDesign = document.toObject(DesignModel.class);
                                shape.setText(currentDesign.getShape());
                                flavour.setText(currentDesign.getFlavour());
                                type.setText(currentDesign.getType());
                                date.setText(currentDesign.getDatetime());
                                drawDecoration();
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
        } else {
            currentDesign = (DesignModel) getIntent().getSerializableExtra("design");
            fillInInformation();
            drawDecoration();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mDesignReg != null) {
            mDesignReg = mDesignRef.addSnapshotListener(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void initFirestore() {
        db = FirebaseFirestore.getInstance();
        design = db.collection("design");
    }

    /*
     * Fill in TextView based on Model information
     */
    private void fillInInformation() {
        //Set all the design information
        shape.setText(currentDesign.getShape());
        flavour.setText(currentDesign.getFlavour());
        type.setText(currentDesign.getType());
        date.setText(datetime);

    }

    /*
     * Draw the cake decoration image based on topping name and coordinates
     */
    private void drawDecoration() {
        ArrayList<String> decorations = currentDesign.getDecorations();
        ArrayList<Integer> X = currentDesign.getX();
        ArrayList<Integer> Y = currentDesign.getY();
        //Get the width and height of decoration, each is 50dp*50dp
        int w = (int) (50 * DesignDetail.this.getResources().getDisplayMetrics().density);
        int h = (int) (50 * DesignDetail.this.getResources().getDisplayMetrics().density);
        //Since the coordinate index starts from the second element
        //Get each coordinate from index+1
        for (int i = 0; i < decorations.size() - 1; i++) {
            ImageView temp = new ImageView(DesignDetail.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, h);
            temp.setLayoutParams(layoutParams);
            //Set decoration image based on decoration name
            if (decorations.get(i).equals("strawberry")) {
                temp.setImageDrawable(strawberry);
            } else if (decorations.get(i).equals("sprinkling")) {
                temp.setImageDrawable(sprinkling);
            } else if (decorations.get(i).equals("candle")) {
                temp.setImageDrawable(candle);
            } else if (decorations.get(i).equals("bean")) {
                temp.setImageDrawable(beans);
            } else if (decorations.get(i).equals("cookie")) {
                temp.setImageDrawable(cookie);
            } else {
                temp.setImageDrawable(marshmallow);
            }
            temp.setX(X.get(i + 1));
            temp.setY(Y.get(i + 1));
            cakeBase.addView(temp);

        }
    }


    /**
     * The Listener for submit button
     * Start activity to check out page
     */
    public void submitDesign(View view) {
        Intent it = new Intent(DesignDetail.this, CheckOutActivity.class);
        double price = calculatePrice(currentDesign.getDecorations());
        // it.putExtra()
        it.putExtra("price", price);
        it.putExtra("design", mDesignRef.getId());
        finish();
        startActivity(it);
    }

    /*
     * Save design to database
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveDesign(View view) {
        //get the designer's userID
        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        final String userId = preferences.getString("user_id", "0");

        //Input all design information into HashMap
        Map<String, Object> data = new HashMap<>();
        data.put("flavour", currentDesign.getFlavour());
        data.put("shape", currentDesign.getShape());
        data.put("type", currentDesign.getType());
        data.put("X", currentDesign.getX());
        data.put("Y", currentDesign.getY());
        data.put("decorations", currentDesign.getDecorations());
        data.put("user", userId);
        data.put("datetime", datetime);

        db.collection("users").document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            currentUser = document.toObject(User.class);
                            int preNum = currentUser.getDesigns();
                            db.collection("users")
                                    .document(userId)
                                    .update("designs", preNum + 1);
                        }
                    }
        });

        design.document()
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(DesignDetail.this, "Your design has been saved!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(DesignDetail.this, MyDesignActivity.class);
                        intent.putExtra("Profile",true);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        currentDesign = snapshot.toObject(DesignModel.class);

        // Set all the design information
        shape.setText(currentDesign.getShape());
        flavour.setText(currentDesign.getFlavour());
        type.setText(currentDesign.getType());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backArrow) {
            Intent intent = new Intent(DesignDetail.this, MainActivity.class);
            if (isProfile) {
                intent.putExtra("Profile", true);
            } else {
                intent.putExtra("Design", true);
            }
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            startActivity(intent);
        }
    }

    /**
     * Calculate the final price based on toppings
     */
    private double calculatePrice(ArrayList<String> decorations) {
        double price = 88d;
        for (int i = 0; i < decorations.size() - 1; i++) {

            if (decorations.get(i).equals("strawberry")) {
                price += 5;
            } else if (decorations.get(i).equals("sprinkling")) {
                price += 5;
            } else if (decorations.get(i).equals("candle")) {
                price += 5;
            } else if (decorations.get(i).equals("bean")) {
                price += 5;
            } else if (decorations.get(i).equals("cookie")) {
                price += 5;
            } else {
                price += 5;
            }

        }
        return price;
    }
}

