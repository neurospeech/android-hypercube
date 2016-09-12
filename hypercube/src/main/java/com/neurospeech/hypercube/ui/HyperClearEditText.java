package com.neurospeech.hypercube.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
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

    public int getClearDrawable() {
        return clearDrawable;
    }

    public void setClearDrawable(int clearDrawable) {
        this.clearDrawable = clearDrawable;
    }

    private int clearDrawable;

    private void init() {
        setPadding(getPaddingLeft(),getPaddingTop(),getPaddingRight()*5,getPaddingBottom());

        setClearDrawable(android.R.drawable.ic_delete);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                refreshUI();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        if(focused){
            refreshUI();
        }else{
            setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }

    }

    private void refreshUI() {
        if(getText().length()>0){
            setCompoundDrawablesWithIntrinsicBounds(0,0,clearDrawable,0);
        }else{
            setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }
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
