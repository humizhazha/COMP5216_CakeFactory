package au.edu.sydney.comp5216.cakefactory;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity  extends AppCompatActivity {
    TextView  signUp;
    ImageView signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        signUp = (TextView)findViewById(R.id.signUp);
        signIn = (ImageView)findViewById(R.id.signIn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(it);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SignInActivity.this,MainActivity.class);
                startActivity(it);
            }
        });

    }
}
