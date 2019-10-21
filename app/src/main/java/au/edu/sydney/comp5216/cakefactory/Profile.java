package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import adapter.ProfileAdapter;
import model.ProfileModel;

public class Profile extends AppCompatActivity {

    private ProfileAdapter profileAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ProfileModel> profileModelArrayList = new ArrayList<>();

    private Integer[] icons = {
            R.drawable.ic_inbox,
            R.drawable.ic_profile,
            R.drawable.ic_settings};
    private Integer arrow = R.drawable.ic_chevron_right;
    private String[] titles = {
            "Recommendations",
            "Profile",
            "Settings"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        recyclerView = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Profile.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < 3; i++) {
            ProfileModel view = new ProfileModel(icons[i], arrow, titles[i]);
            profileModelArrayList.add(view);
        }
        profileAdapter = new ProfileAdapter(Profile.this, profileModelArrayList);
        recyclerView.setAdapter(profileAdapter);
    }

    public void goDesign(View view) {
        Intent i = new Intent(Profile.this, EditProfile.class);
        startActivity(i);
    }

    public void goOrder(View view) {
        Intent i = new Intent(Profile.this, Orders.class);
        startActivity(i);
    }

    public void goFavourite(View view) {
        Intent i = new Intent(Profile.this, Favourites.class);
        startActivity(i);
    }
}
