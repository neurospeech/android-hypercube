package com.neurospeech.hypercube.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.neurospeech.hypercube.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 11-03-2016.
 */
public abstract class AppArrayListAdapter<T, VH extends AppArrayListAdapter.ViewHolder> extends ArrayAdapter<T> {

    LayoutInflater inflater;

    public AppArrayListAdapter(Context context) {
        super(context, 0);
        inflater = LayoutInflater.from(context);

    }

    public List<T> getItems(){
        int n = getCount();
        List<T> r = new ArrayList<T>(n);
        for(int i=0;i<n;i++){
            r.add(getItem(i));
        }
        return r;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        VH viewHolder = null;

        View itemView = convertView;

        if(itemView!=null){
            viewHolder = (VH)itemView.getTag(R.string.list_adapter_view_holder);
        }else{
            viewHolder = onCreateViewHolder(inflater,parent, getItemViewType(position) );
            viewHolder.itemView.setTag(R.string.list_adapter_view_holder,viewHolder);
            itemView = viewHolder.itemView;
        }

        onBind(viewHolder,getItem(position));

        return itemView;
    }

    protected abstract void onBind(VH holder, T item);

    protected abstract VH onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    public static class ViewHolder{

        public final View itemView;

        public ViewHolder(View itemView){
            this.itemView = itemView;
        }

    }

}
