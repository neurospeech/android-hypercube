package com.neurospeech.hypercube;

import android.app.Application;
import android.content.Context;

/**
 * Created by akash.kava on 21-03-2016.
 */
public class HyperCubeApplication {

    static Application application;


    public static void init(Application context){
        HyperCubeApplication.application = context;
    }

}
