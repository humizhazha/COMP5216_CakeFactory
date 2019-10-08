package au.edu.sydney.comp5216.cakefactory;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity  extends AppCompatActivity {
    TextView  signUp;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        signUp = (TextView)findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(it);
            }
        });

    }
}
