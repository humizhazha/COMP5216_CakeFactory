package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Step3Activity extends AppCompatActivity {
    ImageView chocolate;
    ImageView cheese;
    ImageView matcha;
    ImageView strawberry;
    ImageView vanilla;
    String selected;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step3);
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        drawBorder();

    }
    public void goNext(View view) {
        Intent intent = new Intent(Step3Activity.this, DecorationActivity.class);
        startActivity(intent);

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
