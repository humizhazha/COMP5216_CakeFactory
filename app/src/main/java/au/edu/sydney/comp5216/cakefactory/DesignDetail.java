package au.edu.sydney.comp5216.cakefactory;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import model.DesignModel;

/**
 * Design Detail Activity
 * Show design'detail information
 * Show selected shape, flavour, type and decoration design
 */
public class DesignDetail extends AppCompatActivity implements
        EventListener<DocumentSnapshot> {

    public static final String KEY_DESIGN_ID = "key_design_id";
    private String designId;

    private DesignModel currentDesign;
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
    private static final String TAG = DesignDetail.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.design_detail);

        //get drawable initilize with drawable
        strawberry = getResources().getDrawable(R.drawable.deco_strawberry);
        cookie = getResources().getDrawable(R.drawable.deco_cookie);
        marshmallow = getResources().getDrawable(R.drawable.deco_marshmallows);
        beans = getResources().getDrawable(R.drawable.deco_beans);
        candle = getResources().getDrawable(R.drawable.deco_candle);
        sprinkling = getResources().getDrawable(R.drawable.deco_sprinkles);
        cakeBase = (ViewGroup) findViewById(R.id.base);

        //Initlize the textview
        flavour = (TextView)findViewById(R.id.flavour_text);
        type = (TextView) findViewById(R.id.type_text);
        shape = (TextView) findViewById(R.id.shape_text);
        date = (TextView) findViewById(R.id.date_text);

        initFirestore();

        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.containsKey(KEY_DESIGN_ID)) {
            designId = extras.getString(KEY_DESIGN_ID);
            mDesignRef = db.collection("design").document(designId);
        } else {
            currentDesign = (DesignModel)getIntent().getSerializableExtra("design");
            fillInInformation();
            drawDecoration();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mDesignReg != null){
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

    private void fillInInformation(){
        //Set all the design information
        shape.setText(currentDesign.getShape());
        flavour.setText(currentDesign.getFlavour());
        type.setText(currentDesign.getType());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date now = new Date();
        date.setText(formatter.format(now));

    }
    private void drawDecoration(){
        ArrayList<String> decorations = new ArrayList<>();
        ArrayList<Integer> X = new ArrayList<>();
        ArrayList<Integer> Y = new ArrayList<>();
        decorations = currentDesign.getDecorations();
        X = currentDesign.getX();
        Y = currentDesign.getY();
        int w = (int) (50 * DesignDetail.this.getResources().getDisplayMetrics().density);
        int h = (int) (50 * DesignDetail.this.getResources().getDisplayMetrics().density);
        for(int i=0;i<decorations.size()-1;i++){

            ImageView temp = new ImageView(DesignDetail.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, h);
            temp.setLayoutParams(layoutParams);
            if(decorations.get(i).equals("strawberry")){
                temp.setImageDrawable(strawberry);
            }else if(decorations.get(i).equals("sprinkling")){
                temp.setImageDrawable(sprinkling);
            }else if(decorations.get(i).equals("candle")){
                temp.setImageDrawable(candle);
            }else if(decorations.get(i).equals("bean")){
                temp.setImageDrawable(beans);
            }else if(decorations.get(i).equals("cookie")){
                temp.setImageDrawable(cookie);
            }else{
                temp.setImageDrawable(marshmallow);
            }
            temp.setX(X.get(i+1));
            temp.setY(Y.get(i+1));
            cakeBase.addView(temp);

        }

    }
    public void submitDesign(View view){
        Map<String, Object> data = new HashMap<>();
        data.put("flavour", currentDesign.getFlavour());
        data.put("shape", currentDesign.getShape());
        data.put("type", currentDesign.getType());
        data.put("X", currentDesign.getX());
        data.put("Y", currentDesign.getY());
        data.put("decorations", currentDesign.getDecorations());
        design.document()
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(DesignDetail.this, "Your design has been saved!", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        Intent intent = new Intent(DesignDetail.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        currentDesign = snapshot.toObject(DesignModel.class);

        // Set all the design information
        shape.setText(currentDesign.getShape());
        flavour.setText(currentDesign.getFlavour());
        type.setText(currentDesign.getType());
    }

}
