package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Article");
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
        favorite.setVisibility(View.INVISIBLE);
        db = FirebaseFirestore.getInstance();

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

    private void updateLikeInFireBase(int currentlike) {

        db.collection("article")
                .document(article.getArticle_id())
                .update("like", currentlike);

    }

    private void addFavoriteListener() {

        unfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unfavorite.setVisibility(View.INVISIBLE);
                favorite.setVisibility(View.VISIBLE);
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
