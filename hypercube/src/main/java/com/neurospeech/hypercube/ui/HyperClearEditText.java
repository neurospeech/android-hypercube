package com.neurospeech.hypercube.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import com.neurospeech.hypercube.R;

/**
 * Created by akash.kava on 09-09-2016.
 */
public class HyperClearEditText extends HyperEditText {
    public HyperClearEditText(Context context) {
        super(context);
        init();
    }

    public HyperClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HyperClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public HyperClearEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {



        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                }else{
                    setCompoundDrawablesWithIntrinsicBounds(0,0, android.R.drawable.ic_delete ,0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setPadding(getPaddingLeft(),getPaddingTop(),getPaddingRight()*5,getPaddingBottom());

    }

    @Override
    protected void onDrawableClick(int i, Drawable cd, MotionEvent event) {
        super.onDrawableClick(i, cd, event);
        if(i==2){
            // clear text...

            setText("");
        }
    }
}
