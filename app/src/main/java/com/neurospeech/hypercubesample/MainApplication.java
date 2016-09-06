package com.neurospeech.hypercubesample;

import android.app.Application;

import com.neurospeech.hypercube.HyperCubeApplication;
import com.neurospeech.hypercubesample.adapters.NavigationViewHolder;

/**
 * Created by akash.kava on 22-04-2016.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        HyperCubeApplication.init(this);

        HyperCubeApplication.registerViewHolderType(NavigationViewHolder.class);
    }
}
