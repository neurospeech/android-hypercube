package com.neurospeech.hypercubesample.adapters;

import android.view.View;
import android.widget.TextView;

import com.neurospeech.hypercube.ui.HyperItemViewHolder;
import com.neurospeech.hypercube.ui.HyperViewHolder;
import com.neurospeech.hypercubesample.R;
import com.neurospeech.hypercubesample.model.MenuModel;

/**
 * Created by  on 09-09-2016.
 */

@HyperItemViewHolder(R.layout.item_menu)
public class MenuViewHolder extends HyperViewHolder<MenuModel> {


    private final TextView title;

    public MenuViewHolder(View itemView) {
        super(itemView);
        title = (TextView)itemView.findViewById(R.id.title);
    }

    @Override
    protected void bind(final MenuModel item) {
        title.setText(item.name);

        final NavigationListAdapter adapter = (NavigationListAdapter)getAdapter();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.onItemClick(item);
            }
        });
    }
}
