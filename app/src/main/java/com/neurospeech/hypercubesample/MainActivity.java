package com.neurospeech.hypercubesample;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

        //findViewById(R.id.fragment_container)

        HyperRecyclerView recyclerView = (HyperRecyclerView) findViewById(R.id.headered_list);




        adapter.addAll(
                new MenuModel("Home","Home",MainActivity.class),
                new MenuModel("Form","Text",R.layout.activity_main));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);







    }

    public void fragmentTransaction(Fragment fragment) {
        FragmentTransaction fragmentTransactions = getSupportFragmentManager().beginTransaction();
        fragmentTransactions.replace(R.id.fragment_container,fragment);
        fragmentTransactions.addToBackStack(null);
        fragmentTransactions.commit();
        drawerLayout.closeDrawer(navigationView);
        if (fragment.getView() != null) {
            Toolbar toolbar = (Toolbar) fragment.getView().findViewById(R.id.toolbar);
            setUpActionBarToggle(toolbar);
        }
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
