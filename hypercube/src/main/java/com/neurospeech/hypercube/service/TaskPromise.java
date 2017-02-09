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

            private Exception lastException = null;

            @Override
            protected RT doInBackground(IFunction<T,RT>... params) {
                try {
                    return params[0].run(input);
                }catch (Exception ex){
                    lastException = ex;
                }
                return null;
            }

            @Override
            protected void onPostExecute(RT rt) {
                super.onPostExecute(rt);

                promise.onResult(rt, lastException == null ? null : lastException.getMessage());
                if(lastException!=null){
                    Log.e("Error",HyperCubeApplication.toString(lastException));
                }
            }
        }).execute(converter);
        return promise;
    }

}
