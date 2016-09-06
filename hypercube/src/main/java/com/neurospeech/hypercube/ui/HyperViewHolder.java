package com.neurospeech.hypercube.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by  on 06-09-2016.
 */
public abstract class HyperViewHolder<T> extends RecyclerView.ViewHolder {



    public HyperViewHolder(View itemView) {
        super(itemView);
    }

    public void bindItem(Object item){
        bind((T)item);
    }

    protected abstract void bind(T item);

}
