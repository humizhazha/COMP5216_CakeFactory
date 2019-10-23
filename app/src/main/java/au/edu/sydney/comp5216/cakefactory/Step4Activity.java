package au.edu.sydney.comp5216.cakefactory;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.ViewGroup;
import android.view.MotionEvent;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import model.DesignModel;
import android.util.Log;

public class Step4Activity extends AppCompatActivity implements View.OnTouchListener {
    int windowwidth; // Actually the width of the RelativeLayout.
    int windowheight; // Actually the height of the RelativeLayout.
    private ImageView mImageView;
    private ViewGroup mRrootLayout;
    private int _xDelta;
    private int _yDelta;
    private ImageView marshmallows;
    private ImageView strawberry;
    private ImageView cookie;
    private ImageView Sprinkling;
    private ImageView candle;
    private ImageView bean;
    private Drawable currentDeco;
    private int position_x;
    private int position_y;
    private int relative_position_x;
    private int relative_position_y;
    private DesignModel currentDesign;
    ArrayList<String> decorations = new ArrayList<>();
    ArrayList<Integer> X = new ArrayList<>();
    ArrayList<Integer> Y = new ArrayList<>();
    private FirebaseFirestore db;
    CollectionReference design;
    String selected;
    private static final String TAG = Step4Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step4);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Design");

        ImageView goBack = findViewById(R.id.backArrow);
        goBack.setVisibility(View.INVISIBLE);
        currentDesign = (DesignModel) getIntent().getSerializableExtra("design");

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        // We are interested when the image view leaves its parent RelativeLayout
        // container and not the screen, so the following code is not needed.

        mRrootLayout = (ViewGroup) findViewById(R.id.root);
        mImageView = (ImageView) mRrootLayout.findViewById(R.id.im_move_zoom_rotate);

        marshmallows = (ImageView)findViewById(R.id.marshmallows);
        cookie = (ImageView)findViewById(R.id.cookies);
        Sprinkling = (ImageView)findViewById(R.id.sprinkling);
        strawberry = (ImageView)findViewById(R.id.strawberry);
        bean = (ImageView)findViewById(R.id.beans);
        candle = (ImageView)findViewById(R.id.candle);
        startDecorationListener();
        initFirestore();
        mImageView.setImageDrawable(null);

        // These these following 2 lines that address layoutparams set the width
        // and height of the ImageView to 150 pixels and, as a side effect, clear any
        // params that will interfere with movement of the ImageView.
        // We will rely on the XML to define the size and avoid anything that will
        // interfere, so we will comment these lines out. (You can test out how a layout parameter
        // can interfere by setting android:layout_centerInParent="true" in the ImageView.
        mImageView.setOnTouchListener(this);

        // Capture the width of the RelativeLayout once it is laid out.
        mRrootLayout.post(new Runnable() {
            @Override
            public void run() {
                windowwidth = mRrootLayout.getWidth();
                windowheight = mRrootLayout.getHeight();
            }
        });
    }
    // Tracks when we have reported that the image view is out of bounds so we
    // don't over report.
    private boolean isOutReported = false;

    private void initFirestore() {

        db = FirebaseFirestore.getInstance();
        design = db.collection("design");

    }

    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        position_x = X;
        position_y = Y;
        relative_position_x = (int) event.getX();
        relative_position_y = (int) event.getY();



        // Check if the image view is out of the parent view and report it if it is.
        // Only report once the image goes out and don't stack toasts.
        if (isOut(view)) {
            if (!isOutReported) {
                isOutReported = true;
                Toast.makeText(this, "OUT", Toast.LENGTH_SHORT).show();
            }
        } else {
            isOutReported = false;
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // _xDelta and _yDelta record how far inside the view we have touched. These
                // values are used to compute new margins when the view is moved.
                _xDelta = X - view.getLeft();
                _yDelta = Y - view.getTop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                // Do nothing
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                // Image is centered to start, but we need to unhitch it to move it around.

                    lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
                    lp.addRule(RelativeLayout.CENTER_VERTICAL, 0);

                lp.leftMargin = X - _xDelta;
                lp.topMargin = Y - _yDelta;
                // Negative margins here ensure that we can move off the screen to the right
                // and on the bottom. Comment these lines out and you will see that
                // the image will be hemmed in on the right and bottom and will actually shrink.
                lp.rightMargin = view.getWidth() - lp.leftMargin - windowwidth;
                lp.bottomMargin = view.getHeight() - lp.topMargin - windowheight;
                view.setLayoutParams(lp);
                break;
        }
        // invalidate is redundant if layout params are set or not needed if they are not set.
//        mRrootLayout.invalidate();
        return true;
    }

    private boolean isOut(View view) {
        // Check to see if the view is out of bounds by calculating how many pixels
        // of the view must be out of bounds to and checking that at least that many
        // pixels are out.
        float percentageOut = 0.50f;
        int viewPctWidth = (int) (view.getWidth() * percentageOut);
        int viewPctHeight = (int) (view.getHeight() * percentageOut);

        return ((-view.getLeft() >= viewPctWidth) ||
                (view.getRight() - windowwidth) > viewPctWidth ||
                (-view.getTop() >= viewPctHeight) ||
                (view.getBottom() - windowheight) > viewPctHeight);
    }

    public void goNext(View view) {
        if(selected!=null){
            decorations.add(selected);
            X.add(position_x);
            Y.add(position_y);
        }

        currentDesign.setDecorations(decorations);
        currentDesign.setX(X);
        currentDesign.setY(Y);
        Map<String, Object> data = new HashMap<>();
        data.put("flavour", currentDesign.getFlavour());
        data.put("shape", currentDesign.getShape());
        data.put("type", currentDesign.getType());
        data.put("X", X);
        data.put("Y", Y);
        data.put("decorations", decorations);
        design.document()
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        Toast.makeText(Step4Activity.this, "Your design has been saved!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Step4Activity.this, DesignDetail.class);
        if (intent != null) {
            intent.putExtra("design", currentDesign);
            startActivity(intent);
        }



    }
    public void goBack(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Step4Activity.this);
        builder.setTitle(R.string.dialog_cancel_title)
                .setMessage(R.string.dialog_cancel_msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Step4Activity.this, Step3Activity.class);
                        if (intent != null) {
                            intent.putExtra("design", currentDesign);
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User cancelled the dialog
                        // Nothing happens
                    }
                });
        builder.create().show();



    }
    public void emptyBorder(){
        marshmallows.setBackground(null);
        cookie.setBackground(null);
        Sprinkling.setBackground(null);
        strawberry.setBackground(null);
        bean.setBackground(null);
        candle.setBackground(null);
    }

    public void startDecorationListener(){
        marshmallows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable(R.drawable.ic_border);
                currentDeco = getResources().getDrawable(R.drawable.deco_marshmallows);
                ImageView temp = addDecoration(mImageView,v,"marshmallows");
                mRrootLayout.addView(temp);
                mImageView.setImageDrawable(currentDeco);
                emptyBorder();
                marshmallows.setBackground(highlight);
            }
        });

        cookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable(R.drawable.ic_border);
                currentDeco = getResources().getDrawable(R.drawable.deco_cookie);
                ImageView temp = addDecoration(mImageView,v,"cookie");
                mRrootLayout.addView(temp);
                mImageView.setImageDrawable(currentDeco);
                emptyBorder();
                cookie.setBackground(highlight);
            }
        });

        Sprinkling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable(R.drawable.ic_border);
                currentDeco = getResources().getDrawable(R.drawable.deco_sprinkles);
                ImageView temp = addDecoration(mImageView,v,"sprinkling");
                mRrootLayout.addView(temp);
                mImageView.setImageDrawable(currentDeco);
                emptyBorder();
                Sprinkling.setBackground(highlight);
            }
        });

        strawberry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.ic_border);
                currentDeco = getResources().getDrawable(R.drawable.deco_strawberry);
                ImageView temp = addDecoration(mImageView,v,"strawberry");
                mRrootLayout.addView(temp);
                mImageView.setImageDrawable(currentDeco);
                emptyBorder();
                strawberry.setBackground(highlight);
            }
        });

        candle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.ic_border);
                currentDeco = getResources().getDrawable(R.drawable.deco_candle);
                ImageView temp = addDecoration(mImageView,v,"candle");
                mRrootLayout.addView(temp);
                mImageView.setImageDrawable(currentDeco);
                emptyBorder();
                candle.setBackground(highlight);
            }
        });

        bean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.ic_border);
                currentDeco = getResources().getDrawable(R.drawable.deco_beans);
                ImageView temp = addDecoration(mImageView,v,"bean");
                //temp.setPadding(mImageView.,mImageView.getTop(),mImageView.getRight(),mImageView.getBottom());
                mRrootLayout.addView(temp);
                mImageView.setImageDrawable(currentDeco);
                emptyBorder();
                bean.setBackground(highlight);
            }
        });

    }

    private ImageView addDecoration( ImageView mImageView, View view, String type){


        ImageView temp = new ImageView(Step4Activity.this);
        int w = (int) (50 * Step4Activity.this.getResources().getDisplayMetrics().density);
        int h = (int) (50 * Step4Activity.this.getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, h);
        temp.setLayoutParams(layoutParams);
        temp.setImageDrawable(mImageView.getDrawable());
        temp.setX(mImageView.getLeft());
        temp.setY(mImageView.getTop()+200);
        decorations.add(type);
        X.add(mImageView.getLeft()-35);
        Y.add(mImageView.getTop());
        selected = type;

        return temp;

    }

}
