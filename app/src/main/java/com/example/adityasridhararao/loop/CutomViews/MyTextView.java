package com.example.adityasridhararao.loop.CutomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by aditya.sridhara.rao on 6/21/2016.
 */
public class MyTextView extends TextView {
    public MyTextView(Context context, AttributeSet attributeSet) {

        super(context,attributeSet);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Helvetica.ttf"));
    }
}
