package com.neurospeech.hypercube.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.neurospeech.hypercube.validations.TextValidator;

/**
 * Created by  on 08-08-2016.
 */
public class HyperEditText extends EditText {

    private TextValidator validator;

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


    public void setValidator(
            boolean validateOnLostFocus,
            boolean validateOnTextChange,
            TextValidator validator){
        this.validator = validator;
        this.validateOnLostFocus = validateOnLostFocus;
        this.validateOnTextChange = validateOnTextChange;
    }







    TextWatcher textWatcher;


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        if(validateOnLostFocus){
//            interceptFocusChangeListener(null);
//        }
        if(validateOnTextChange){
            textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    validate();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            addTextChangedListener(textWatcher);
        }
    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        if(focused){
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    int pos = getText().length();
                    setSelection(pos);

                }
            },10);
        }
        else{
            if(validateOnLostFocus){
                validate();
            }
        }

    }

    private boolean isTextValid = false;
    public void validate() {
        Object error = null;
        if(validator!=null){
            error = validator.invalidError(getText().toString());
        }
        if(error!=null){
            isTextValid = false;
            String textError = null;
            if(error instanceof String || error instanceof CharSequence){
                textError = error.toString();
            }else{
                // seems integer...
                if(error instanceof Integer){
                    Integer n = (Integer) error;
                    if(n == 0){
                        textError = null;
                    }else{
                        textError = getContext().getResources().getString(n);
                    }
                }
            }
            setError(textError);
        }else{
            isTextValid = true;
            setError(null);
        }
    }

    public boolean isValid(){
        validate();
        return isTextValid;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        OnFocusChangeListener f = getOnFocusChangeListener();
//        if(f instanceof InterceptedFocusChangeListener){
//            InterceptedFocusChangeListener ifl = (InterceptedFocusChangeListener)f;
//            setOnFocusChangeListener(ifl.previous);
//        }
        if(textWatcher!=null){
            removeTextChangedListener(textWatcher);
        }
    }

//    @Override
//    public void setOnFocusChangeListener(OnFocusChangeListener l) {
//        if(validateOnLostFocus){
//            interceptFocusChangeListener(l);
//            return;
//        }
//        super.setOnFocusChangeListener(l);
//    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // check if we clicked on drawables to clear...

        if(event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            Drawable[] cds = getCompoundDrawables();
            for (int i = 0; i < 4; i++) {
                Drawable cd = cds[i];
                if (cd == null)
                    continue;

                int left = 0;
                int right = getWidth();

                switch (i){
                    case 0:

                        right = getPaddingLeft() + cd.getIntrinsicWidth();
                        break;
                    case 2:
                        left = getWidth() - getPaddingRight() - cd.getIntrinsicWidth();
                        break;
                    default:
                        break;
                }

                boolean tapped = x >= left && x <= right && y<= (getBottom()-getTop());
                if(tapped ){
                    onDrawableClick(i,cd,event);
                }

            }
        }

        return super.onTouchEvent(event);
    }

    protected void onDrawableClick(int i, Drawable cd, MotionEvent event) {
        if(drawableClickListener!=null)
            drawableClickListener.onClick(i,cd);
    }

    public OnDrawableClickListener getDrawableClickListener() {
        return drawableClickListener;
    }

    public void setDrawableClickListener(OnDrawableClickListener drawableClickListener) {
        this.drawableClickListener = drawableClickListener;
    }

    private OnDrawableClickListener drawableClickListener;

    public interface OnDrawableClickListener{
        void onClick(int position,Drawable drawable);
    }

//    protected void interceptFocusChangeListener(final OnFocusChangeListener listener) {
//        super.setOnFocusChangeListener(new InterceptedFocusChangeListener(getOnFocusChangeListener()){
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                super.onFocusChange(v, hasFocus);
//
//                if(listener!=null){
//                    listener.onFocusChange(v,hasFocus);
//                }
//
//                if(v == HyperEditText.this && !hasFocus) {
//                    validate();
//                }
//
//
//            }
//        });
//    }



    public boolean isValidateOnTextChange() {
        return validateOnTextChange;
    }

    public void setValidateOnTextChange(boolean validateOnTextChange) {
        this.validateOnTextChange = validateOnTextChange;
    }

    public boolean isValidateOnLostFocus() {
        return validateOnLostFocus;
    }

    public void setValidateOnLostFocus(boolean validateOnLostFocus) {
        this.validateOnLostFocus = validateOnLostFocus;
    }

    private boolean validateOnTextChange;
    private boolean validateOnLostFocus;






}
