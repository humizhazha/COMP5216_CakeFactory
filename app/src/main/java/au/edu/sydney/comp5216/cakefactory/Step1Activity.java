package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import model.DesignModel;

/**
 * Step 1 Activity
 * Ask user to select cake shape
 * User click next button to step 2
 */
public class Step1Activity extends AppCompatActivity {

    private ImageView square;
    private ImageView circle;
    private ImageView heart;
    private String selected;
    private DesignModel currentDesign;
    Drawable highlight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step1);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Design");
        highlight = getResources().getDrawable(R.drawable.ic_border);

        ImageView goBack = findViewById(R.id.backArrow);
        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Step1Activity.this, MainActivity.class);
                intent.putExtra("Design", true);
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });
        square = (ImageView) findViewById(R.id.square);
        heart = (ImageView) findViewById(R.id.heart);
        circle = (ImageView) findViewById(R.id.circle);
        if(getIntent().getSerializableExtra("design")!=null){
            currentDesign = (DesignModel) getIntent().getSerializableExtra("design");
            iniBorder();
        }else{
            currentDesign = new DesignModel();
        }

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);



        drawBorder();
    }
    private void iniBorder(){
        String previousSelection = currentDesign.getShape();
        if(previousSelection.equals("square")){
            square.setBackground(highlight);
        }
        else if(previousSelection.equals("heart")){
            heart.setBackground(highlight);
        }else{
            circle.setBackground(highlight);
        }
    }

    public void goNext(View view) {
        if (currentDesign.getShape()==null) {
            Toast.makeText(Step1Activity.this, "Please select your cake shape!", Toast.LENGTH_SHORT).show();
        } else {
            currentDesign.setShape(selected);
            Intent intent = new Intent(Step1Activity.this, Step2Activity.class);
            if (intent != null) {
                intent.putExtra("design", currentDesign);
                startActivity(intent);
            }

        }
    }

    public void drawBorder() {
        square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                square.setBackground(highlight);
                circle.setBackground(null);
                heart.setBackground(null);
                selected = "square";
                currentDesign.setShape(selected);

            }
        });

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circle.setBackground(highlight);
                heart.setBackground(null);
                square.setBackground(null);
                selected = "circle";
                currentDesign.setShape(selected);

            }
        });

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heart.setBackground(highlight);
                square.setBackground(null);
                circle.setBackground(null);
                selected = "heart";
                currentDesign.setShape(selected);

            }
        });

    }


}
