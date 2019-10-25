package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import model.Article;

/**
 * View Article Activity
 * User can view the content of the article
 * User can give upvote and add article to My favorites
 */
public class ViewArticleAcitivity extends AppCompatActivity {
    Article article;
    TextView author;
    TextView title;
    TextView date;
    TextView like_number;
    TextView news_content;
    ImageView newsimage;
    ImageView unlike;
    ImageView like;
    ImageView unfavorite;
    ImageView favorite;
    private FirebaseFirestore db;
    SharedPreferences preferences;
    String userId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Article");
        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        userId = preferences.getString("user_id", "0");
        article = (Article) getIntent().getSerializableExtra("article");
        author = (TextView) findViewById(R.id.author);
        title = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.date);
        like_number = (TextView) findViewById(R.id.number);
        news_content = (TextView) findViewById(R.id.content);
        newsimage = (ImageView) findViewById(R.id.newsimage);
        unlike = (ImageView) findViewById(R.id.upvote);
        like = (ImageView) findViewById(R.id.upvote_click);
        unfavorite = (ImageView) findViewById(R.id.favorite);
        favorite = (ImageView) findViewById(R.id.favorite_click);
        like.setVisibility(View.INVISIBLE);
        db = FirebaseFirestore.getInstance();
        showIfisMyFavorite();

        ImageView goBack = findViewById(R.id.backArrow);
        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ViewArticleAcitivity.this, MainActivity.class);
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });
        fillInArticle();
        addLikeListener();
        addFavoriteListener();
    }
    /**
     * If the user has already favorite this article
     * Show the favorite icon
     */
    private void showIfisMyFavorite(){
        db.collection("favorite")
                .whereEqualTo("article_id", article.getArticle_id())
                .whereEqualTo("user_id", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().isEmpty()){
                        favorite.setVisibility(View.INVISIBLE);
                    }

                } else {
                    Log.d("View Article", "Error getting documents: ", task.getException());
                }
            }
        });

    }

    private void fillInArticle() {
        author.setText(article.getNewsname());
        title.setText(article.getNews());
        date.setText(article.getTime());
        news_content.setText(article.getContent().replace("\\n", "\n\n"));
        Glide.with(newsimage.getContext())
                .load(article.getNewsimage2())
                .into(newsimage);
        like_number.setText(String.valueOf(article.getLike()));
    }

    private void addLikeListener() {

        unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentLike = Integer.parseInt(like_number.getText().toString());
                unlike.setVisibility(View.INVISIBLE);
                like.setVisibility(View.VISIBLE);
                currentLike++;
                updateLikeInFireBase(currentLike);
                article.setLike(currentLike);
                like_number.setText(String.valueOf(currentLike));
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentLike = Integer.parseInt(like_number.getText().toString());
                unlike.setVisibility(View.VISIBLE);
                like.setVisibility(View.INVISIBLE);
                currentLike--;
                updateLikeInFireBase(currentLike);
                article.setLike(currentLike);
                like_number.setText(String.valueOf(currentLike));

            }
        });
    }
    /**
     * Update the number of likes in database
     */
    private void updateLikeInFireBase(int currentlike) {

        db.collection("article")
                .document(article.getArticle_id())
                .update("like", currentlike);

    }

    /**
     * Add article to favorites collection in Firestore
     */
    private void addNewFavorite(){
        Map<String, Object> favorite = new HashMap<>();
        favorite.put("article_id", article.getArticle_id());

        favorite.put("user_id", userId);
        db.collection("favorite").document()
                .set(favorite)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Design Detail", "DocumentSnapshot successfully written!");
                        Toast.makeText(ViewArticleAcitivity.this, "Successfully added this article to My favorites",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addFavoriteListener() {

        unfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unfavorite.setVisibility(View.INVISIBLE);
                favorite.setVisibility(View.VISIBLE);
                addNewFavorite();

            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unfavorite.setVisibility(View.VISIBLE);
                favorite.setVisibility(View.INVISIBLE);
            }
        });
    }
}
