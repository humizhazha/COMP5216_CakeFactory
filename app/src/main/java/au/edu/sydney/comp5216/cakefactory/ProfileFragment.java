package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Nullable;

import adapter.ProfileAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import model.Profile;
import model.Recommendation;
import model.User;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment implements View.OnClickListener,
        EventListener<DocumentSnapshot> {

    private ProfileAdapter mAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Profile> profileModelArrayList = new ArrayList<>();

    private CircleImageView profile_image;
    private TextView name;
    //        TextView Bio;
    private TextView favouriteNum;
    private TextView designNum;
    private TextView orderNum;

    // Firebase
    private FirebaseFirestore mFirestore;
    private DocumentReference mUserRef;
    private ListenerRegistration mUserRegistration;

    private Integer[] icons = {
            R.drawable.ic_inbox,
            R.drawable.ic_profile};
    private Integer arrow = R.drawable.ic_chevron_right;
    private String[] titles = {
            "Recommendations",
            "Profile"};

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().findViewById(R.id.favourite).setOnClickListener(this);
        getActivity().findViewById(R.id.design).setOnClickListener(this);
        getActivity().findViewById(R.id.order).setOnClickListener(this);
        getActivity().findViewById(R.id.setting).setOnClickListener(this);

        recyclerView = getView().findViewById(R.id.recycler);

        profile_image  = getView().findViewById(R.id.profile_image);
        name = getView().findViewById(R.id.name);
        favouriteNum = getView().findViewById(R.id.favouriteNum);
        designNum = getView().findViewById(R.id.designNum);
        orderNum = getView().findViewById(R.id.orderNum);

        // Get restaurant ID from extras
        SharedPreferences preferences = getActivity().getSharedPreferences("preferences", MODE_PRIVATE);
        String userId = preferences.getString("user_id", "0");
        userId = "US8ef5moenRIrBHB5P7aySB8ssx2";
        if (userId == null) {
            throw new IllegalArgumentException("Must pass extra userId");
        }
        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);

        // Initialize Firestore and the main RecyclerView
        mFirestore = FirebaseFirestore.getInstance();

        // Get reference to the logined user
        mUserRef = mFirestore.collection("users")
                .document(userId);

        initRecyclerView();
    }

    /**
     * Initialize main RecyclerView
     */
    private void initRecyclerView() {
        recyclerView.setId(R.id.container);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < 2; i++) {
            Profile view = new Profile(icons[i], arrow, titles[i]);
            profileModelArrayList.add(view);
        }

        mAdapter = new ProfileAdapter(getContext(), profileModelArrayList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mUserRegistration = mUserRef.addSnapshotListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favourite:
                startActivity(new Intent(getActivity(), Favourites.class));
                break;
            case R.id.order:
                startActivity(new Intent(getActivity(), Orders.class));
                break;
            case R.id.design:
                startActivity(new Intent(getActivity(), RecommendationActivity.class));
                break;
            case R.id.setting:
                startActivity(new Intent(getActivity(), Setting.class));
                break;

        }
    }

    /**
    * Listener for the User document ({@link #mUserRef}).
    */
    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        User user = snapshot.toObject(User.class);
        Glide.with(profile_image.getContext())
                .load(user.getAvatar())
                .into(profile_image);
        name.setText(user.getUsername());
        favouriteNum.setText(String.valueOf(user.getArticles().size()));
        designNum.setText(String.valueOf(user.getDesigns().size()));
        orderNum.setText(String.valueOf(user.getOrders().size()));
    }
}
