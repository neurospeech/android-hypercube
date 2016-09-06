package com.neurospeech.hypercubesample.adapters;

import android.view.Menu;
import android.view.View;

import com.neurospeech.hypercube.ui.HyperItemViewHolder;
import com.neurospeech.hypercube.ui.HyperViewHolder;
import com.neurospeech.hypercubesample.R;
import com.neurospeech.hypercubesample.model.MenuModel;

/**
 * Created by akash.kava on 06-09-2016.
 */

@HyperItemViewHolder(R.layout.item_menu)
public class NavigationViewHolder extends HyperViewHolder<MenuModel> {

    public NavigationViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void bind(MenuModel item) {

    }
}
