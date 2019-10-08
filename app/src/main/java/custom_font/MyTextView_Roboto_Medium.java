package custom_font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
//import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;


public class MyTextView_Roboto_Medium extends AppCompatTextView {

    public MyTextView_Roboto_Medium(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyTextView_Roboto_Medium(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTextView_Roboto_Medium(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
            setTypeface(tf);
        }
    }

}