package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

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
    TextView news_content;
    ImageView newsimage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Article");
        article = (Article) getIntent().getSerializableExtra("article");
        author = (TextView) findViewById(R.id.author);
        title = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.date);
        news_content = (TextView) findViewById(R.id.content);
        newsimage = (ImageView)findViewById(R.id.newsimage);

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
    }
    private void fillInArticle(){
        author.setText(article.getNewsname());
        title.setText(article.getNews());
        date.setText(article.getTime());
        news_content.setText(article.getContent().replace("\\n", "\n\n"));
        Glide.with(newsimage.getContext())
                .load(article.getNewsimage2())
                .into(newsimage);
    }
}
