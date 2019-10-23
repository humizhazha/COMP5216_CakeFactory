package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import model.DesignModel;

public class Step2Activity extends AppCompatActivity {
    private ImageView wedding;
    private ImageView birthday;
    private ImageView cupcake;
    private ImageView pie;
    private ImageView roll;
    private ImageView slice;
    private String selected;
    private DesignModel currentDesign;
    Drawable highlight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step2);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Design");
        currentDesign = (DesignModel) getIntent().getSerializableExtra("design");
        highlight = getResources().getDrawable(R.drawable.ic_border);

        ImageView goBack = findViewById(R.id.backArrow);
        goBack.setVisibility(View.INVISIBLE);

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        wedding = (ImageView)findViewById(R.id.wedding);
        birthday = (ImageView)findViewById(R.id.birthday);
        cupcake = (ImageView)findViewById(R.id.cupcake);
        pie = (ImageView)findViewById(R.id.pie);
        roll = (ImageView)findViewById(R.id.roll);
        slice = (ImageView)findViewById(R.id.slice);
        iniBorder();
        drawBorder();

    }

    private void iniBorder(){
        if(currentDesign.getType()!=null){
            String previousSelection = currentDesign.getType();
            if(previousSelection.equals("slice")){
                slice.setBackground(highlight);
            }
            else if(previousSelection.equals("roll")){
                roll.setBackground(highlight);
            }else if(previousSelection.equals("pie")){
                pie.setBackground(highlight);
            }else if(previousSelection.equals("wedding")){
                wedding.setBackground(highlight);
            }else if(previousSelection.equals("cupcake")){
                cupcake.setBackground(highlight);
            }else{
                birthday.setBackground(highlight);
            }
        }

    }
    public void goNext(View view) {
        Intent intent = new Intent(Step2Activity.this, Step3Activity.class);
        if(currentDesign.getType()==null){
            Toast.makeText(Step2Activity.this, "Please select your cake type!", Toast.LENGTH_SHORT).show();
        }else{
            currentDesign.setType(selected);
            if (intent != null) {
                intent.putExtra("design", currentDesign);
                startActivity(intent);
            }
        }


    }
    public void goBack(View view) {
        Intent intent = new Intent(Step2Activity.this, Step1Activity.class);
        if (intent != null) {
            intent.putExtra("design", currentDesign);
            startActivity(intent);
        }
    }

    public void drawBorder(){
        wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wedding.setBackground(highlight);
                birthday.setBackground(null);
                cupcake.setBackground(null);
                pie.setBackground(null);
                roll.setBackground(null);
                slice.setBackground(null);
                selected = "wedding";
                currentDesign.setType(selected);

            }
        });

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wedding.setBackground(null);
                birthday.setBackground(highlight);
                cupcake.setBackground(null);
                pie.setBackground(null);
                roll.setBackground(null);
                slice.setBackground(null);
                selected = "birthday";
                currentDesign.setType(selected);
            }
        });

        cupcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wedding.setBackground(null);
                birthday.setBackground(null);
                cupcake.setBackground(highlight);
                pie.setBackground(null);
                roll.setBackground(null);
                slice.setBackground(null);
                selected = "cupcake";
                currentDesign.setType(selected);
            }
        });


        pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wedding.setBackground(null);
                birthday.setBackground(null);
                cupcake.setBackground(null);
                pie.setBackground(highlight);
                roll.setBackground(null);
                slice.setBackground(null);
                selected = "pie";
                currentDesign.setType(selected);
            }
        });

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wedding.setBackground(null);
                birthday.setBackground(null);
                cupcake.setBackground(null);
                pie.setBackground(null);
                roll.setBackground(highlight);
                slice.setBackground(null);
                selected = "roll";
                currentDesign.setType(selected);
            }
        });

        slice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wedding.setBackground(null);
                birthday.setBackground(null);
                cupcake.setBackground(null);
                pie.setBackground(null);
                roll.setBackground(null);
                slice.setBackground(highlight);
                selected = "slice";
                currentDesign.setType(selected);
            }
        });

    }
}
