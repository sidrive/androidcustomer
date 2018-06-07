package lawyerku.android_customer.widget.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewRegular extends AppCompatTextView{
    public TextViewRegular(Context context) {
        super(context);
        setTransformationMethod(null);
        setFont(context);
    }

    public TextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTransformationMethod(null);
        setFont(context);
    }

    private void setFont(Context context) {
        Typeface type = Typeface.createFromAsset(context.getAssets(), "font/Ubuntu-Regular.ttf");
        setTypeface(type);
    }
}
