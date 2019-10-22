package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import adapter.ProfileAdapter;
import model.ProfileModel;

public class Profile extends Fragment {

    private ProfileAdapter profileAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ProfileModel> profileModelArrayList = new ArrayList<>();

    private Integer[] icons = {
            R.drawable.ic_inbox,
            R.drawable.ic_profile};
    private Integer arrow = R.drawable.ic_chevron_right;
    private String[] titles = {
            "Recommendations",
            "Profile"};

    public Profile() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = getView().findViewById(R.id.recycler);
        recyclerView.setId(R.id.container);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < 2; i++) {
            ProfileModel view = new ProfileModel(icons[i], arrow, titles[i]);
            profileModelArrayList.add(view);
        }
        profileAdapter = new ProfileAdapter(getContext(), profileModelArrayList);
        recyclerView.setAdapter(profileAdapter);

        // Jump to My Favourites page
        LinearLayout goFavourite = getView().findViewById(R.id.favourite);
        goFavourite.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getActivity(), Favourites.class));
            }
        });

        // Jump to My Orders page
        LinearLayout goOrder = getView().findViewById(R.id.order);
        goOrder.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getActivity(), Orders.class));
            }
        });

        // Jump to My Designs page
        LinearLayout goDesign = getView().findViewById(R.id.design);
        goDesign.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getContext(), Orders.class));
            }
        });

        // Jump to My Setting page
        ImageView goSetting = getView().findViewById(R.id.setting);
        goSetting.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getContext(), Setting.class));
            }
        });
    }
}
