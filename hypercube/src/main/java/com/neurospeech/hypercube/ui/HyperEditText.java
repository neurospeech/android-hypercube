package com.neurospeech.hypercube.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by  on 08-08-2016.
 */
public class HyperEditText extends EditText {
    public HyperEditText(Context context) {
        super(context);
    }

    public HyperEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HyperEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public HyperEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    
}
