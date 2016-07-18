package com.example.adityasridhararao.loop.utilsJava;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by aditya.sridhara.rao on 6/18/2016.
 */
public class ListnerOnTextChange implements View.OnFocusChangeListener{

    private Context mContext;
    private EditText mEdittextview;
    private TextView mTextView;

    //constructor
    // will take the edit text view  and
    public ListnerOnTextChange(Context context, EditText edittextview, TextView textView){
        super();
        this.mContext = context;
        this.mEdittextview= edittextview;
        this.mTextView = textView;

    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            mTextView.setVisibility(View.VISIBLE);
        }
    }
}
