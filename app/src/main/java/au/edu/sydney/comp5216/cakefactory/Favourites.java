package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import adapter.ListBaseAdapter;
import custom_font.ExpandableHeightListView;
import model.Article;
import model.User;

/**
 * Collected article page
 * Show articles collected by user from Cloud Firestore
 * User can search article name
 */
public class Favourites extends AppCompatActivity implements EventListener<DocumentSnapshot> {

    private ListBaseAdapter baseAdapter;
    private ExpandableHeightListView listview;
    private ArrayList<Article> Bean = new ArrayList<>();
    private ArrayList<String> userArticles = new ArrayList<>();

    private FirebaseFirestore db;
    private CollectionReference articles;
    private DocumentReference mUserRef;
    private ListenerRegistration mUserRegistration;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private int[] IMAGE1 = {R.drawable.newsname1, R.drawable.newsname1, R.drawable.newsname1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Favourites");

        ImageView goBack = findViewById(R.id.backArrow);
        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Favourites.this, MainActivity.class);
                intent.putExtra("Profile",true);
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });

        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        String userId = preferences.getString("user_id", "0");
        userId = "US8ef5moenRIrBHB5P7aySB8ssx2";
        if (userId == null) {
            throw new IllegalArgumentException("Must pass extra userId");
        }

        // Initial database
        db = FirebaseFirestore.getInstance();
        articles = db.collection("article");
        mUserRef = db.collection("users").document(userId);

        readFromDatabase();

        listview = findViewById(R.id.listview);
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

    /**
     * Listener for the User document ({@link #mUserRef}).
     */
    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        userArticles = snapshot.toObject(User.class).getArticles();
    }

    /**
     * Read article from Firestore
     */
    private void readFromDatabase() {
        // Attach a listener to read the data at our posts reference
        db.collection("article")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            // check whether article is collected by user
                            if (userArticles.contains(document.getId())) {
                                String author = document.get("author").toString();
                                String title = document.get("title").toString();
                                String content = document.get("content").toString().replaceAll("\\n", "\n");
                                String sub = document.get("newssub").toString();
                                String image = document.get("image").toString();
                                int like = Integer.parseInt(document.get("like").toString());
                                Date date = document.getDate("date");
                                String strDate = dateFormat.format(date);
                                addToList(author, title, content, strDate, sub, image,like, document.getId());
                            }
                        }

                        addbaseAdapter();
                    }
                });
    }

    private void addToList(String author, String title, String content, String date, String sub, String image, int like,String article_id) {
        Article article = new Article(IMAGE1[0], image, author, date, title, sub, content, like);
        article.setArticle_id(article_id);
        Bean.add(article);

    }

    private void addbaseAdapter() {
        baseAdapter = new ListBaseAdapter(Favourites.this, Bean);
        listview.setAdapter(baseAdapter);
        baseAdapter.notifyDataSetChanged();
    }
}