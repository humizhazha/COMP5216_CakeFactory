package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Step3Activity extends AppCompatActivity {
    private ImageView chocolate;
    private ImageView cheese;
    private ImageView matcha;
    private ImageView strawberry;
    private ImageView vanilla;
    private String selected;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step3);
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

        chocolate = (ImageView)findViewById(R.id.chocolate);
        cheese = (ImageView)findViewById(R.id.cheese);
        matcha = (ImageView)findViewById(R.id.matcha);
        strawberry = (ImageView)findViewById(R.id.strawberry);
        vanilla = (ImageView)findViewById(R.id.vanilla);

        drawBorder();

    }
    public void goNext(View view) {
        Intent intent = new Intent(Step3Activity.this, Step4Activity.class);
        if(selected==null){
            Toast.makeText(Step3Activity.this, "Please select your cake flavour!", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(intent);
        }

    }
    public void goBack(View view) {
        Intent intent = new Intent(Step3Activity.this, Step2Activity.class);
        startActivity(intent);

    }

    public void drawBorder(){
        chocolate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable(R.drawable.ic_border);
                chocolate.setBackground(highlight);
                cheese.setBackground(null);
                matcha.setBackground(null);
                strawberry.setBackground(null);
                vanilla.setBackground(null);
                selected = "chocolate";

            }
        });

        cheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.ic_border);
                chocolate.setBackground(null);
                cheese.setBackground(highlight);
                matcha.setBackground(null);
                strawberry.setBackground(null);
                vanilla.setBackground(null);
                selected = "cheese";

            }
        });

        matcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.ic_border);
                chocolate.setBackground(null);
                cheese.setBackground(null);
                matcha.setBackground(highlight);
                strawberry.setBackground(null);
                vanilla.setBackground(null);
                selected = "matcha";
            }
        });

        strawberry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.ic_border);
                chocolate.setBackground(null);
                cheese.setBackground(null);
                matcha.setBackground(null);
                strawberry.setBackground(highlight);
                vanilla.setBackground(null);
                selected = "strawberry";
            }
        });

        vanilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.ic_border);
                chocolate.setBackground(null);
                cheese.setBackground(null);
                matcha.setBackground(null);
                strawberry.setBackground(null);
                vanilla.setBackground(highlight);
                selected = "vanilla";
            }
        });

    }
}
