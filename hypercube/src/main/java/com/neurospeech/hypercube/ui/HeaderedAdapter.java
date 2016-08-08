package com.neurospeech.hypercube.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

/**
 *
 */
public abstract class HeaderedAdapter<T,VH extends RecyclerView.ViewHolder> extends AppArrayAdapter<T,RecyclerView.ViewHolder>{

    private List<HeaderOrItem<T>> allItems;

    public HeaderedAdapter(Context context){
        super(context);
    }

    public HeaderedAdapter(Context context, List<T> source) {
        super(context);

        this.addAll(source);

        /**
         * This method is used to identify whether it is header or item and accordingly will regroup items based on header
         * so finally source would contain
         * H1
         *  S1
         *  S2
         *  S3
         * H2
         *  S4
         *  S5 and so on
         */
        recreate();
    }

    protected void recreate() {
        /**
         * Clear existing items from HeaderOrItem
         */
        allItems.clear();
        Object last = null;
        int i =0;
        for (T item : getItems()){
            Object header = getHeader(item);
            if (last == null || !last.equals(header)) {
                allItems.add(new HeaderOrItem<T>(header,null,i));

            }
            allItems.add(new HeaderOrItem<T>(null,item,i++));

            last = header;
        }

    }



    public abstract Object getHeader(T item);


    @Override
    public final int getItemViewType(int position) {

        HeaderOrItem<T> item = allItems.get(position);
        if(item.header!=null){
            return getHeaderItemViewType(item.header);
        }
        return getViewType(item.item);
    }

    @Override
    public int getItemCount() {
        return allItems.size();
    }

    protected int getViewType(T item) {
        return 0;
    }

    @Override
    protected final RecyclerView.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        if (viewType < 0){
            return createHeaderViewHolder(inflater,parent,viewType);
        }
        return createViewHolder(inflater,parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderOrItem<T> item = allItems.get(position);
        if(item.header!=null){
            onBindHeader((VH)holder,item.header);
            return;
        }
        onBind(holder,item.item);
    }

    /**
     *
     * @param holder
     * @param header
     * Header will automatically populated here, we need not override this one, we will override this only when header has to contain only letter letter of the text or something similar
     */
    protected void onBindHeader(RecyclerView.ViewHolder holder, Object header) {
        ((HeaderViewHolder)holder).header.setText(header.toString());
    }



    protected RecyclerView.ViewHolder createHeaderViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        TextView view = new TextView(getContext());
        view.setPadding(5,5,5,5);
        return new HeaderViewHolder(view);
    }

    protected abstract VH createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    protected int getHeaderItemViewType(Object header) {
        return -1;
    }


    /**
     * Class defined for Header
     */
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public final TextView header;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView;
        }
    }


    /**
     *
     */
    public static class HeaderOrItem<T> {

        public final int position;

        public HeaderOrItem(Object header, T t, int position) {
            this.header = header;
            this.item = t;
            this.position = position;
        }

        public Object getHeader() {
            return header;
        }

        Object header;

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }

        T item;

    }


}
