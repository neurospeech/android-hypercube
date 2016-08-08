package com.neurospeech.hypercubesample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neurospeech.hypercube.ui.HyperRecyclerView;
import com.neurospeech.hypercube.ui.HeaderedAdapter;
import com.neurospeech.hypercubesample.model.MenuModel;

public class MainActivity extends AppCompatActivity {

    MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        HyperRecyclerView recyclerView = (HyperRecyclerView) findViewById(R.id.headered_list);


        adapter = new MenuAdapter(this);

        TextView header = new TextView(this);
        header.setText("Header");

        recyclerView.addHeader(header);

        TextView footer =new TextView(this);
        footer.setText("Footer1");
        recyclerView.addFooter(footer);

        footer =new TextView(this);
        footer.setText("Footer2");
        recyclerView.addFooter(footer);

        adapter.addAll(
                new MenuModel("Home","Recycler View",R.layout.activity_main),
                new MenuModel("Home","ListView View",R.layout.activity_main),
                new MenuModel("Home","ListView View",R.layout.activity_main),
                new MenuModel("Home","ListView View",R.layout.activity_main),
                new MenuModel("Home","ListView View",R.layout.activity_main),
                new MenuModel("Home","ListView View",R.layout.activity_main),
                new MenuModel("Home","ListView View",R.layout.activity_main),
                new MenuModel("Home","ListView View",R.layout.activity_main),
                new MenuModel("Home","ListView View",R.layout.activity_main),
                new MenuModel("Home","ListView View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main),
                new MenuModel("Logout","Logout View",R.layout.activity_main));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);







    }

    static class MenuAdapter extends HeaderedAdapter<MenuModel,MenuAdapter.ViewHolder> {


        public MenuAdapter(Context context) {
            super(context);
        }

        @Override
        protected void onBind(ViewHolder holder, MenuModel item) {
            holder.title.setText(item.name);
        }


        @Override
        public Object getHeader(MenuModel item) {
            return item.header;
        }




        @Override
        protected ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_menu,parent,false);
            return new ViewHolder(view);
        }

        static class ViewHolder extends RecyclerView.ViewHolder{

            final TextView title;

            public ViewHolder(View itemView) {
                super(itemView);

                title = (TextView)itemView.findViewById(R.id.title);
            }
        }

    }

}
