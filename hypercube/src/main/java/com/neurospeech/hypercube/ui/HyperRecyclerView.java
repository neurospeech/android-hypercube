package com.neurospeech.hypercube.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by  on 08-08-2016.
 */
public class HyperRecyclerView extends RecyclerView {
    public HyperRecyclerView(Context context) {
        super(context);
    }

    public HyperRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HyperRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {

        Adapter old = getAdapter();

        if(old instanceof ExAdapter){
            ((ExAdapter)old).dispose();
        }

        super.setAdapter(new ExAdapter(adapter));
    }

    private ArrayList<View> headers = null;
    private ArrayList<View> footers = null;

    public void addHeader(View view){
        if(headers==null){
            headers =new ArrayList<>();
        }
        headers.add(view);
        rebuild();
    }

    public void addFooter(View view){
        if(footers==null){
            footers = new ArrayList<>();
        }
        footers.add(view);
        rebuild();
    }

    public void removeHeader(View view){
        if(headers==null)
            return;
        headers.remove(view);
        if(headers.isEmpty())
            headers = null;
        rebuild();
    }

    public void removeFooter(View view){
        if(footers==null)
            return;
        footers.remove(view);
        if(footers.isEmpty())
            footers = null;
        rebuild();
    }

    private void rebuild() {
        Adapter a = getAdapter();
        if(a!=null) {
            a.notifyDataSetChanged();
        }
    }

    public static final int HEADER_FOOTER_ITEM_TYPE = Integer.MIN_VALUE;

    class ExAdapter extends Adapter{



        private final Adapter adapter;
        private final AdapterDataObserver observer;



        ExAdapter(Adapter adapter){
            this.adapter = adapter;

            this.observer = new AdapterDataObserver() {
                @Override
                public void onChanged() {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    notifyDataSetChanged();
                }
            };

            adapter.registerAdapterDataObserver(observer);
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType==HEADER_FOOTER_ITEM_TYPE){
                FrameLayout layout= new FrameLayout(getContext());
                layout.setLayoutParams(
                        new LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                HeaderFooterViewHolder hfvh = new HeaderFooterViewHolder(layout);
                return hfvh;
            }
            return adapter.onCreateViewHolder(parent,viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int headerSize = getHeaderSize();
            if(isHeader(position)){
                HeaderFooterViewHolder hfvh = (HeaderFooterViewHolder)holder;
                hfvh.layout.removeAllViews();
                View header = headers.get(position);
                hfvh.layout.addView(header,
                        new FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                return;
            }
            if(isFooter(position)){
                HeaderFooterViewHolder hfvh = (HeaderFooterViewHolder)holder;
                hfvh.layout.removeAllViews();
                View footer = footers.get(position-(headerSize+total));
                hfvh.layout.addView(footer,
                        new FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                return;
            }

            adapter.onBindViewHolder(holder,position-headerSize);
        }

        //private int headerSize = 0;
        //private int footerSize = 0;

        private int getHeaderSize(){
            return headers == null ? 0 : headers.size();
        }

        private int getFooterSize(){
            return footers == null ? 0 : footers.size();
        }


        private int total = 0;

        @Override
        public int getItemCount() {

            total = adapter.getItemCount();

            int n =  getHeaderSize() + total + getFooterSize();
            return n;
        }

        boolean isHeader(int position){
            int headerSize = getHeaderSize();
            if(headerSize==0)
                return false;
            return position < headerSize;
        }

        boolean isFooter(int position){
            int footerSize = getFooterSize();
            if(footerSize==0)
                return false;
            int headerSize = getHeaderSize();
            return  position >= headerSize + total;
        }

        @Override
        public int getItemViewType(int position) {
            try {
                if (isHeader(position))
                    return HEADER_FOOTER_ITEM_TYPE;

                if(isFooter(position))
                    return HEADER_FOOTER_ITEM_TYPE;

                int headerSize = getHeaderSize();
                int type = adapter.getItemViewType(position - headerSize);
                if (type == HEADER_FOOTER_ITEM_TYPE) {
                    throw new IllegalArgumentException("getItemViewType must not be equal to Integer.MIN_VALUE");
                }
                return type;
            }catch (Exception ex){
                Log.e("Hyper Recycler View", ex.getLocalizedMessage());
                Log.e("Hyper Recycler View", "Failed for position " + position + " with footers " + getFooterSize() + " with total " + getItemCount());
                ex.printStackTrace();
                throw new RuntimeException("Failed", ex);
            }
        }

        public void dispose(){
            adapter.unregisterAdapterDataObserver(observer);
        }



    }

    static class HeaderFooterViewHolder extends ViewHolder{

        final FrameLayout layout;

        public HeaderFooterViewHolder(View itemView) {
            super(itemView);
            layout = (FrameLayout)itemView;
        }
    }

}
