package com.neurospeech.hypercube.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by  on 06-09-2016.
 */
public abstract class HyperViewHolder<T> extends RecyclerView.ViewHolder {


    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    private RecyclerView.Adapter adapter;

    public HyperViewHolder(View itemView) {
        super(itemView);
    }

    public void bindItem(RecyclerView.Adapter adapter, Object item){
        this.adapter = adapter;
        bind((T)item);
    }

    protected abstract void bind(T item);

}
