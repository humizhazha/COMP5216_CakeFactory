package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import adapter.RecommendationAdapter;
import model.RecommendationModel;

public class Recommendations extends AppCompatActivity {

    private RecommendationAdapter recommendationAdapter;
    private RecyclerView recyclerView;
    private ArrayList<RecommendationModel> recommendationModelArrayList = new ArrayList<>();

    private int[] imgs = {
            R.drawable.recommendation1,
            R.drawable.recommendation2,
            R.drawable.recommendation3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommendations);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Recommendations");

        recyclerView = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(Recommendations.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < 3; i++) {
            recommendationModelArrayList.add(new RecommendationModel(imgs[i]));
        }

        recommendationAdapter =
                new RecommendationAdapter(Recommendations.this, recommendationModelArrayList);
        recyclerView.setAdapter(recommendationAdapter);

        ImageView goBack = findViewById(R.id.backArrow);
        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Recommendations.this, Profile.class);
                startActivity(i);
            }
        });
    }
}
