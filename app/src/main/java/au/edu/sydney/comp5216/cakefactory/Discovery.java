package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import android.widget.AdapterView;
import com.google.firebase.firestore.Query;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import custom_font.ExpandableHeightListView;

public class Discovery extends Fragment {

    private ExpandableHeightListView listview;
    private ArrayList<Article> Bean = new ArrayList<>();
    private ListBaseAdapter baseAdapter;
    private FirebaseFirestore mFirestore;
    private Query mQuery;
    DatabaseReference ref;
    private static final String TAG =Discovery.class.getSimpleName();

    private int[] IMAGE1 = {R.drawable.newsname1, R.drawable.newsname1, R.drawable.newsname1};
    private int[] IMAGE2 = {R.drawable.img1, R.drawable.img1, R.drawable.img1};
    private String[] NEWSNAME = {"Fox News .", "Fox News .", "Fox News ."};
    private String[] TITLE = {"1 day ago", "1 day ago", "1 day ago"};
    private String[] NEWS = {"Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous. Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous",
            "Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous. Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous",
            "Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous. Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous"};
    private String[] NEWSSUB = {"Why even a President Trump couldn’t make Apple manufacture iPhone in the state.","Why even a President Trump couldn’t make Apple manufacture iPhone in the state.",
            "Why even a President Trump couldn’t make Apple manufacture iPhone in the state."};

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    private void initFirestore() {

        mFirestore = FirebaseFirestore.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mQuery = mFirestore.collection("article").limit(5);
        ref = database.getReference("/cake-factory-59fdc.firebaseio.com/");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.discovery, container, false);
    }

    private void readFromDatabase(){
        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Article post = dataSnapshot.getValue(Article.class);
                System.out.println(post);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FirebaseFirestore.setLoggingEnabled(true);
        initFirestore();
        readFromDatabase();

        listview = getActivity().findViewById(R.id.listview);

        for (int i= 0; i< IMAGE1.length; i++){
            Article bean = new Article(IMAGE1[i], IMAGE2[i],NEWSNAME[i], TITLE[i], NEWS[i], NEWSSUB[i]);
            Bean.add(bean);
        }
        baseAdapter = new ListBaseAdapter(getContext(), Bean);
        listview.setAdapter(baseAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Get the photo path from system
                // String photoPath = imageAdapter.getItem(position);
                startActivity(new Intent(getActivity(), ViewArticleAcitivity.class));

            }
        });
    }
}
