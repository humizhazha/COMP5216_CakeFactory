package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Step2Activity extends AppCompatActivity {
    private ImageView wedding;
    private ImageView birthday;
    private ImageView cupcake;
    private ImageView pie;
    private ImageView roll;
    private ImageView slice;
    private String selected;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step2);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Design");

        ImageView goBack = findViewById(R.id.backArrow);
        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Step2Activity.this, MainActivity.class);
                intent.putExtra("Design",true);
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        wedding = (ImageView)findViewById(R.id.wedding);
        birthday = (ImageView)findViewById(R.id.birthday);
        cupcake = (ImageView)findViewById(R.id.cupcake);
        pie = (ImageView)findViewById(R.id.pie);
        roll = (ImageView)findViewById(R.id.roll);
        slice = (ImageView)findViewById(R.id.slice);

        drawBorder();

    }
    public void goNext(View view) {
        Intent intent = new Intent(Step2Activity.this, Step3Activity.class);
        startActivity(intent);

    }
    public void goBack(View view) {
        Intent intent = new Intent(Step2Activity.this, Step1Activity.class);
        startActivity(intent);
    }

    public void drawBorder(){
        wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable(R.drawable.ic_border);
                wedding.setBackground(highlight);
                birthday.setBackground(null);
                cupcake.setBackground(null);
                pie.setBackground(null);
                roll.setBackground(null);
                slice.setBackground(null);
                selected = "wedding";

            }
        });

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.ic_border);
                wedding.setBackground(null);
                birthday.setBackground(highlight);
                cupcake.setBackground(null);
                pie.setBackground(null);
                roll.setBackground(null);
                slice.setBackground(null);
                selected = "birthday";
            }
        });

        cupcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.ic_border);
                wedding.setBackground(null);
                birthday.setBackground(null);
                cupcake.setBackground(highlight);
                pie.setBackground(null);
                roll.setBackground(null);
                slice.setBackground(null);
                selected = "cupcake";
            }
        });


        pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.ic_border);
                wedding.setBackground(null);
                birthday.setBackground(null);
                cupcake.setBackground(null);
                pie.setBackground(highlight);
                roll.setBackground(null);
                slice.setBackground(null);
                selected = "pie";
            }
        });

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.ic_border);
                wedding.setBackground(null);
                birthday.setBackground(null);
                cupcake.setBackground(null);
                pie.setBackground(null);
                roll.setBackground(highlight);
                slice.setBackground(null);
                selected = "roll";
            }
        });

        slice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.ic_border);
                wedding.setBackground(null);
                birthday.setBackground(null);
                cupcake.setBackground(null);
                pie.setBackground(null);
                roll.setBackground(null);
                slice.setBackground(highlight);
                selected = "slice";
            }
        });

    }
}
