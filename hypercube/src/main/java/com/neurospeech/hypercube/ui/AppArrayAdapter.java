package com.neurospeech.hypercube.ui;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * AppArray Adapter
 */
public abstract class AppArrayAdapter<T,VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH>
{
    public Context getContext() {
        return context;
    }

    private final Context context;
    private final LayoutInflater inflater;

    public AppArrayAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    private List<T> items = new ArrayList<>();
    public List<T> getItems(){
        return items;
    }

    public T getItem(int position){
        return items.get(position);
    }

    public int add(T item) {
        int start = items.size();
        items.add(item);
        // notifyDataSetChanged();
        onNotifyInserted(start);
        return start;
    }

    protected void onNotifyInserted(int start) {
        notifyItemInserted(start);
    }

    public void addAll(T ... items){
        int start = this.items.size();
        int size = items.length;
        for(T item : items) {
            this.items.add(item);
        }
        //notifyDataSetChanged();
        onNotifyItemRangeInserted(start, size);
    }

    protected void onNotifyItemRangeInserted(int start, int size) {
        notifyItemRangeInserted(start, size);
    }


    public void insertAllReversed(int start, T ... items){
        int size = this.items.size();
        for(T item: items){
            this.items.add(start,item);
        }
        onNotifyItemRangeInserted(start, items.length);
    }

    public void insertAllReversed(int start, Collection<? extends T> items){
        int size = this.items.size();
        for(T item: items){
            this.items.add(start,item);
        }
        onNotifyItemRangeInserted(start, items.size());
    }

    public void addAll(Collection<? extends T> items) {
        int start = this.items.size();
        int size = items.size();
        this.items.addAll(items);
        onNotifyItemRangeInserted(start, size);
        //notifyDataSetChanged();

    }

    public void clear(){
        items.clear();
        onNotifyDataSetChanged();
    }

    protected void onNotifyDataSetChanged() {
        notifyDataSetChanged();
    }

    /**
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objects contained in this adapter.
     */
    public void sort(Comparator<? super T> comparator) {
        Collections.sort(items, comparator);
        onNotifyDataSetChanged();
    }


    /**
     * Inserts the specified object at the specified index in the array.
     *
     * @param object The object to insert into the array.
     * @param index  The index at which the object must be inserted.
     */
    public void insert(final T object, int index) {
        items.add(index, object);
        onNotifyInserted(index);

    }

    /**
     * Removes the specified object from the array.
     *
     * @param object The object to remove.
     */
    public void remove(T object) {
        final int position = items.indexOf(object);
        items.remove(object);
        onNotifyItemRemoved(position);
    }

    protected void onNotifyItemRemoved(int position) {
        notifyItemRemoved(position);
    }


    /**
     * Removes the specified item from the array.
     *
     * @param position The object to remove.
     */
    public void remove(int position) {

        items.remove(getItem(position));
        onNotifyItemRemoved(position);
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public int getPosition(final T item) {
        return items.indexOf(item);
    }

    @Override
    public int getItemViewType(int position) {
        T item = getItem(position);
        return getItemViewType(item);
    }

    protected int getItemViewType(T item){
        return 0;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(inflater,parent,viewType);
    }

    protected abstract VH onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        T item = getItem(position);
        onBind(holder,item);
    }

    protected abstract void onBind(VH holder, T item) ;

    @Override
    public int getItemCount() {
        return items.size();
    }
}
