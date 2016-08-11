package com.neurospeech.hypercube.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
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
        if(validateOnLostFocus){
            interceptFocusChangeListener(null);
        }
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

    public void validate() {
        Object error = null;
        if(validator!=null){
            error = validator.invalidError(getText().toString());
        }
        if(error!=null){
            String textError = null;
            if(error instanceof String || error instanceof CharSequence){
                textError = error.toString();
            }else{
                // seems integer...
                if(error instanceof Integer){
                    Integer n = (Integer) error;
                    if(n.intValue() == 0){
                        textError = null;
                    }else{
                        textError = getContext().getResources().getString(n);
                    }
                }
            }
            setError(textError);
        }else{
            setError(null);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        OnFocusChangeListener f = getOnFocusChangeListener();
        if(f instanceof InterceptedFocusChangeListener){
            InterceptedFocusChangeListener ifl = (InterceptedFocusChangeListener)f;
            setOnFocusChangeListener(ifl.previous);
        }
        if(textWatcher!=null){
            removeTextChangedListener(textWatcher);
        }
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        if(validateOnLostFocus){
            interceptFocusChangeListener(l);
            return;
        }
        super.setOnFocusChangeListener(l);
    }

    protected void interceptFocusChangeListener(final OnFocusChangeListener listener) {
        super.setOnFocusChangeListener(new InterceptedFocusChangeListener(getOnFocusChangeListener()){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                super.onFocusChange(v, hasFocus);

                if(listener!=null){
                    listener.onFocusChange(v,hasFocus);
                }

                if(v == HyperEditText.this && !hasFocus) {
                    validate();
                }
            }
        });
    }

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
