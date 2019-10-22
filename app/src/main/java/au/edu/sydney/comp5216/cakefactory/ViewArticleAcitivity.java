package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewArticleAcitivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        TextView toolbar = findViewById(R.id.toolbar);
        toolbar.setText("Article");

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
    }
}
