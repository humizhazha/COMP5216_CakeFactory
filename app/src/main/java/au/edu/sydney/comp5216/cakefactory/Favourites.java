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

import adapter.FavouriteAdapter;
import model.FavouriteModel;

public class Favourites extends AppCompatActivity {

    private FavouriteAdapter favouriteAdapter;
    private RecyclerView recyclerView;
    private ArrayList<FavouriteModel> favouriteModelArrayList = new ArrayList<>();

    private Integer[] imgs = {
            R.drawable.img1,
            R.drawable.img1,
            R.drawable.img1};
    private String[] titles = {
            "Wild Honey at Scotts Square",
            "Wild Honey at Scotts Square",
            "Wild Honey at Scotts Square"};
    private String[] subtittles = {
            "473 Keeling Station",
            "473 Keeling Station",
            "473 Keeling Station"};
    private int[] ratings = {4, 4, 4};
    private String[] reviews = {
            "238 reviews",
            "238 reviews",
            "238 reviews"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Favourites");

        recyclerView = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Favourites.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < 3; i++) {
            favouriteModelArrayList.add(
                    new FavouriteModel(imgs[i], titles[i], subtittles[i], ratings[i], reviews[i]));
        }

        favouriteAdapter = new FavouriteAdapter(Favourites.this, favouriteModelArrayList);
        recyclerView.setAdapter(favouriteAdapter);

        ImageView goBack = findViewById(R.id.backArrow);
        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Favourites.this, Profile.class);
                startActivity(i);
            }
        });
    }
}