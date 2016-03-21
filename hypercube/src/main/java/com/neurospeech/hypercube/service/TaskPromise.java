package com.neurospeech.hypercube.service;

import android.os.AsyncTask;
import android.util.Log;


import com.neurospeech.hypercube.HyperCubeApplication;

import java.util.Objects;

/**
 * Created by  on 05-03-2016.
 */
public class TaskPromise {

    public static <T,RT> Promise<RT> runAsync(final T input,IFunction<T,RT> converter){
        final Promise<RT> promise = new Promise<>();
        (new AsyncTask<IFunction<T,RT>,Integer,RT>(){

            private String lastError = null;

            @Override
            protected RT doInBackground(IFunction<T,RT>... params) {
                try {
                    return params[0].run(input);
                }catch (Exception ex){
                    lastError = HyperCubeApplication.toString(ex);
                }
                return null;
            }

            @Override
            protected void onPostExecute(RT rt) {
                super.onPostExecute(rt);

                promise.onResult(rt, lastError);
                if(lastError!=null){
                    Log.e("Error",lastError);
                }
            }
        }).execute(converter);
        return promise;
    }

}
