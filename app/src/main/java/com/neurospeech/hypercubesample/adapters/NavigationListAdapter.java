package com.neurospeech.hypercubesample.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neurospeech.hypercube.ui.HeaderedAdapter;
import com.neurospeech.hypercubesample.model.MenuModel;

/**
 * Created by  on 13-08-2016.
 */
public class NavigationListAdapter 
        extends HeaderedAdapter<MenuModel,NavigationListAdapter.NavigationViewHolder> {

    public NavigationListAdapter(Context context) {
        super(context);
    }

    @Override
    public Object getHeader(MenuModel item) {
        return null;
    }

    @Override
    protected void onBind(NavigationViewHolder holder, MenuModel item) {

    }

    @Override
    protected NavigationViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return null;
    }

    static class NavigationViewHolder extends RecyclerView.ViewHolder{


        public NavigationViewHolder(View itemView) {
            super(itemView);
        }
    }

}
