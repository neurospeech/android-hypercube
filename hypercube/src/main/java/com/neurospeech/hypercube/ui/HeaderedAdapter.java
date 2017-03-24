package com.neurospeech.hypercube.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.neurospeech.hypercube.HyperCubeApplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public abstract class HeaderedAdapter<T,VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<HeaderOrItem<T>> allItems = new ArrayList<HeaderOrItem<T>>();

    private List<T> source;

    public HeaderedAdapter(Context context) {
        this.context = context;
        source = new ArrayList<>();
    }

    public HeaderedAdapter(Context context, List<T> source) {
        this.context = context;

        this.source = source;

        recreate();
    }

    public Context getContext() {
        return context;
    }

    public void replace(Collection<T> items){
        source.clear();
        source.addAll(items);
        recreate();
    }

    public void replace(T... items){
        source.clear();
        for(T item:items) {
            source.add(item);
        }
        recreate();
    }

    public void addAll(Collection<T> items) {
        source.addAll(items);
        recreate();
    }

    public void addAll(T... items) {
        for (T item : items)
            source.add(item);
        recreate();
    }

    public void add(T item) {
        source.add(item);
        recreate();
    }

    public void remove(T item) {
        source.remove(item);
        recreate();
    }

    public void clear() {
        source.clear();
        recreate();
    }


    public Comparator<? super T> getSortComparator() {
        return sortComparator;
    }

    /**
     * @param comparator Here we need to just store the Comparator i.e. fields on which we have to sort
     */
    public void setSortComparator(Comparator<? super T> comparator) {
        this.sortComparator = comparator;
    }

    private Comparator<? super T> sortComparator;

    /**
     * This method is used to identify whether it is header or item and accordingly will regroup items based on header
     * so finally source would contain
     * H1
     * S1
     * S2
     * S3
     * H2
     * S4
     * S5 and so on
     */
    protected void recreate() {
        /**
         * Clear existing items from HeaderOrItem
         */
        allItems.clear();
        Object last = null;
        int i = 0;

        /**
         * If sortComparator is set, then we sort the list before recreating it
         */
        if (sortComparator != null) {
            Collections.sort(source, sortComparator);
        }




        for (T item : source) {
            Object header = getHeader(item);
            if (header != null) {
                if (last == null || !last.equals(header)) {
                    allItems.add(new HeaderOrItem<T>(header, null, i));

                }
            }
            allItems.add(new HeaderOrItem<T>(null, item, i++));

            last = header;
        }

        notifyDataSetChanged();

    }


    public abstract Object getHeader(T item);


    @Override
    public final int getItemViewType(int position) {

        HeaderOrItem<T> item = allItems.get(position);
        if (item.header != null) {
            return getHeaderItemViewType(item.header);
        }
        return getViewType(item.item);
    }

    @Override
    public int getItemCount() {
        return allItems.size();
    }


    protected int getViewType(T item) {

        Integer i = HyperCubeApplication.modelLayouts.get(item.getClass());
        if (i != null) {
            return i;
        }

        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType < 0) {
            return createHeaderViewHolder(inflater, parent, viewType);
        }
        return createViewHolder(inflater, parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderOrItem<T> item = allItems.get(position);
        if (item.header != null) {
            onBindHeader((VH) holder, item.header);
            return;
        }
        onBind((VH) holder, item.item);
    }

    protected void onBind(VH holder, T item) {
        if (holder instanceof HyperViewHolder) {
            ((HyperViewHolder) holder).bindItem(this,item);
        }
    }


    /**
     * @param holder
     * @param header Header will automatically populated here, we need not override this one, we will override this only when header has to contain only letter letter of the text or something similar
     */
    protected void onBindHeader(RecyclerView.ViewHolder holder, Object header) {
        ((HeaderViewHolder) holder).header.setText(header.toString());
    }


    protected RecyclerView.ViewHolder createHeaderViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        TextView view = new TextView(context);
        view.setPadding(5, 5, 5, 5);
        return new HeaderViewHolder(view);
    }

    protected VH createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
    {
        try {
            HyperCubeApplication.ViewHolderInfo info =
                    HyperCubeApplication.layoutViewHolders.get(viewType);
            if(info==null)
                return null;

            Class c = info.viewHolderClass;
            View view = inflater.inflate(info.layoutId, parent, false);
            return (VH)(c.getConstructor(View.class).newInstance(view));
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

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
