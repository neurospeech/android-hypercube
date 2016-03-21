package com.neurospeech.hypercube.ui;


import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

/**
 * Created by  on 29-10-2015.
 */
public class VerticalList extends LinearLayout {
    public VerticalList(Context context) {
        super(context);
        this.setOrientation(VERTICAL);
    }

    public VerticalList(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(VERTICAL);
    }

    public VerticalList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(VERTICAL);
    }

    @TargetApi(21)
    public VerticalList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.setOrientation(VERTICAL);
    }

    private DataSetObserver dataObserver;

    public ListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ListAdapter adapter) {

        // is it same adapter.. ignore..
        if(this.adapter==adapter) {
            return;
        }

        if(this.adapter != null && this.dataObserver != null){
            this.adapter.unregisterDataSetObserver(this.dataObserver);
        }

        this.adapter = adapter;

        if(this.adapter != null && this.dataObserver!=null){
            this.adapter.registerDataSetObserver(this.dataObserver);
            updateItems();
        }

    }

    ListAdapter adapter;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if(this.dataObserver==null){
            this.dataObserver = new DataSetObserver() {
                @Override
                public void onChanged() {
                    updateItems();
                }

                @Override
                public void onInvalidated() {
                    updateItems();
                }
            };
            if(this.adapter!=null){
                this.adapter.registerDataSetObserver(dataObserver);
            }
            this.updateItems();
        }
    }

    @Override
    protected void onDetachedFromWindow() {

        if(this.adapter!=null && this.dataObserver!=null){
            this.adapter.unregisterDataSetObserver(dataObserver);
            this.dataObserver = null;
        }

        super.onDetachedFromWindow();
    }

    private void updateItems() {
        removeAllViews();

        if(adapter==null)
            return;

        int n = adapter.getCount();
        for (int i=0;i<n;i++){
            View view = adapter.getView(i,null,this);
            this.addView(view);
        }

        this.invalidate();

    }
}
