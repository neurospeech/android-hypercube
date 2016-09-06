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
        extends HeaderedAdapter<MenuModel,RecyclerView.ViewHolder> {

    public NavigationListAdapter(Context context) {
        super(context);
    }

    @Override
    public Object getHeader(MenuModel item) {
        return item.header;
    }


}
