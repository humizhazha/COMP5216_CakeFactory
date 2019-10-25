package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import model.DesignModel;

/**
 * Step 3 Activity
 * Ask user to select cake flavour
 * User click next button to step 4
 */
public class Step3Activity extends AppCompatActivity {
    private ImageView chocolate;
    private ImageView cheese;
    private ImageView matcha;
    private ImageView strawberry;
    private ImageView vanilla;
    private String selected;
    private DesignModel currentDesign;
    Drawable highlight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step3);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Design");
        highlight = getResources().getDrawable(R.drawable.ic_border);
        currentDesign = (DesignModel) getIntent().getSerializableExtra("design");


        ImageView goBack = findViewById(R.id.backArrow);
        goBack.setVisibility(View.INVISIBLE);

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

        chocolate = (ImageView)findViewById(R.id.chocolate);
        cheese = (ImageView)findViewById(R.id.cheese);
        matcha = (ImageView)findViewById(R.id.matcha);
        strawberry = (ImageView)findViewById(R.id.strawberry);
        vanilla = (ImageView)findViewById(R.id.vanilla);

        drawBorder();
        iniBorder();

    }

    private void iniBorder(){
        if(currentDesign.getFlavour()!=null){
            String previousSelection = currentDesign.getFlavour();
            if(previousSelection.equals("chocolate")){
                chocolate.setBackground(highlight);
            }
            else if(previousSelection.equals("cheese")){
                cheese.setBackground(highlight);
            }else if(previousSelection.equals("matcha")){
                matcha.setBackground(highlight);
            }else if(previousSelection.equals("strawberry")){
                strawberry.setBackground(highlight);
            }else{
                vanilla.setBackground(highlight);
            }
        }

    }
    /*
     * Store current selection and get to next step
     * If use does not make any selection, send error message
     */
    public void goNext(View view) {
        Intent intent = new Intent(Step3Activity.this, Step4Activity.class);
        if(currentDesign.getFlavour()==null){
            Toast.makeText(Step3Activity.this, "Please select your cake flavour!", Toast.LENGTH_SHORT).show();
        }else{
            currentDesign.setFlavour(selected);
            if (intent != null) {
                intent.putExtra("design", currentDesign);
                startActivity(intent);
            }
        }

    }
    /*
     * Go back to previous step
     */
    public void goBack(View view) {
        Intent intent = new Intent(Step3Activity.this, Step2Activity.class);
        if (intent != null) {
            intent.putExtra("design", currentDesign);
            startActivity(intent);
        }

    }

    /*
     * Add Listener to all selection
     * If selected, clear the border of previous selection
     * Draw border for the selected one
     */
    public void drawBorder(){
        chocolate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chocolate.setBackground(highlight);
                cheese.setBackground(null);
                matcha.setBackground(null);
                strawberry.setBackground(null);
                vanilla.setBackground(null);
                selected = "chocolate";
                currentDesign.setFlavour(selected);

            }
        });

        cheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chocolate.setBackground(null);
                cheese.setBackground(highlight);
                matcha.setBackground(null);
                strawberry.setBackground(null);
                vanilla.setBackground(null);
                selected = "cheese";
                currentDesign.setFlavour(selected);

            }
        });

        matcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chocolate.setBackground(null);
                cheese.setBackground(null);
                matcha.setBackground(highlight);
                strawberry.setBackground(null);
                vanilla.setBackground(null);
                selected = "matcha";
                currentDesign.setFlavour(selected);
            }
        });

        strawberry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chocolate.setBackground(null);
                cheese.setBackground(null);
                matcha.setBackground(null);
                strawberry.setBackground(highlight);
                vanilla.setBackground(null);
                selected = "strawberry";
                currentDesign.setFlavour(selected);
            }
        });

        vanilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chocolate.setBackground(null);
                cheese.setBackground(null);
                matcha.setBackground(null);
                strawberry.setBackground(null);
                vanilla.setBackground(highlight);
                selected = "vanilla";
                currentDesign.setFlavour(selected);
            }
        });

    }
}
