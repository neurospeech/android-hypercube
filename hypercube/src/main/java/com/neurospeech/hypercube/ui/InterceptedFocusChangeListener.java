package com.neurospeech.hypercube.ui;

import android.view.View;

/**
 * Created by  on 11-08-2016.
 */
public class InterceptedFocusChangeListener implements View.OnFocusChangeListener {


    public final View.OnFocusChangeListener previous;

    public InterceptedFocusChangeListener(View.OnFocusChangeListener previous) {
        this.previous = previous;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(previous!=null){
            previous.onFocusChange(v,hasFocus);
        }
    }
}
