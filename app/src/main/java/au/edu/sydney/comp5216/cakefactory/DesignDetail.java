package au.edu.sydney.comp5216.cakefactory;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import model.DesignModel;

public class DesignDetail extends AppCompatActivity {

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

    Button goCheckout;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.design_detail);
        currentDesign = (DesignModel)getIntent().getSerializableExtra("design");

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


        Button goCheckout = findViewById(R.id.goCheckOut);
        goCheckout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(DesignDetail.this, CheckOutActivity.class);
                double price = calculatePrice(currentDesign.getDecorations());
                // it.putExtra()
                it.putExtra("price", price);
                finish();
                startActivity(it);
            }
        });

        fillInInformation();
        drawDecoration();

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


    private double calculatePrice(ArrayList<String> decorations){
        double price = 88d;
        for(int i=0;i<decorations.size()-1;i++){

            if(decorations.get(i).equals("strawberry")){
                price+=5;
            }else if(decorations.get(i).equals("sprinkling")){
                price+=5;
            }else if(decorations.get(i).equals("candle")){
                price+=5;
            }else if(decorations.get(i).equals("bean")){
                price+=5;
            }else if(decorations.get(i).equals("cookie")){
                price+=5;
            }else{
                price+=0;
            }

        }
        return price;
    }



}
