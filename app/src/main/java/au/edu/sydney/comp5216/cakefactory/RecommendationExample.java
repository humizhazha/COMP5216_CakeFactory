package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecommendationExample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommendation_example);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("No.154 Popular Cake in August");

        ImageView goBack = findViewById(R.id.backArrow);
        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(RecommendationExample.this, Recommendations.class));
            }
        });
    }

}
