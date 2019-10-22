package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Step1Activity extends AppCompatActivity {

    private ImageView square;
    private ImageView circle;
    private ImageView heart;
    private String selected;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step1);

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

        square = (ImageView)findViewById(R.id.square);
        heart = (ImageView)findViewById(R.id.heart);
        circle = (ImageView)findViewById(R.id.circle);

        drawBorder();
    }

    public void goNext(View view) {
        startActivity(new Intent(Step1Activity.this, Step2Activity.class));
    }

    public void drawBorder(){
        square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable(R.drawable.ic_border);
                square.setBackground(highlight);
                circle.setBackground(null);
                heart.setBackground(null);
                selected = "square";

            }
        });

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.ic_border);
                circle.setBackground(highlight);
                heart.setBackground(null);
                square.setBackground(null);
                selected = "circle";

            }
        });

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.ic_border);
                heart.setBackground(highlight);
                square.setBackground(null);
                circle.setBackground(null);
                selected = "heart";

            }
        });

    }


}
