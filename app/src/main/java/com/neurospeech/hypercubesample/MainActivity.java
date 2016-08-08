package com.neurospeech.hypercubesample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.neurospeech.hypercube.HyperCubeApplication;
import com.neurospeech.hypercube.service.IResultListener;
import com.neurospeech.hypercube.service.Promise;
import com.neurospeech.hypercube.ui.AppArrayAdapter;
import com.neurospeech.hypercube.ui.AtomRecyclerView;
import com.neurospeech.hypercube.ui.HeaderedAdapter;
import com.neurospeech.hypercubesample.model.MenuModel;

public class MainActivity extends AppCompatActivity {

    MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AtomRecyclerView recyclerView = (AtomRecyclerView) findViewById(R.id.headered_list);


        adapter = new MenuAdapter(this);




        recyclerView.setAdapter(adapter);







    }

    static class MenuAdapter extends HeaderedAdapter<MenuModel,MenuAdapter.ViewHolder> {


        public MenuAdapter(Context context) {
            super(context);
        }

        @Override
        public Object getHeader(MenuModel item) {
            return item.header;
        }

        @Override
        protected void onBindItem(ViewHolder holder, MenuModel item) {
            holder.title.setText(item.name);
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
