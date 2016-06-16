package com.neurospeech.hypercube.service;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;


import com.neurospeech.hypercube.HyperCubeApplication;
import com.neurospeech.hypercube.ui.AnimatedCircleDrawable;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by .kava on 10/20/2015.
 */
public class Promise<T> {


    private boolean showProgress = true;
    public Promise<T> setShowProgress(boolean v){
        showProgress = v;
        return this;
    }


    public static PopupWindow show() {
        Activity currentActivity = HyperCubeApplication.activity;
        if(currentActivity==null)
            return null;

        PopupWindow pw = new PopupWindow(currentActivity);
        pw.setFocusable(false);
        pw.setBackgroundDrawable(new ColorDrawable(0));
        ImageView img = new ImageView(currentActivity);
        pw.setContentView(img);
        View view = currentActivity.getWindow().getDecorView();
        pw.setWidth(60);
        pw.setHeight(60);
        AnimatedCircleDrawable a = new AnimatedCircleDrawable(Color.RED,5,Color.TRANSPARENT,30);
        img.setImageDrawable(a);
        pw.showAtLocation(view, Gravity.LEFT | Gravity.TOP, view.getWidth() / 2 - 30, view.getHeight() / 2 - 30);
        a.start();
        return pw;
    }

    private List<IResultListener<T>> next;

    public Promise(){
        next = new ArrayList<IResultListener<T>>();
    }

    public T getResult() {
        return result;
    }

    public Callback<T> callback() {


        return new Callback<T>() {

            @Override
            public void onResponse(Call<T> call, Response<T> response) {

                if (response.isSuccessful()) {
                    onResult(response.body(), null);
                } else {
                    try {
                        String message = response.errorBody().string();
                        if(message.isEmpty())
                        {
                            message = response.message();
                        }
                        onResult(null, message);
                        HyperCubeApplication.current.logError(message);
                        //Log.e("Error Response:", message);
                    } catch (Exception ex) {
                        //Log.e("Error Response:",AndroidApplication.toString(ex));
                        String message = HyperCubeApplication.toString(ex);
                        HyperCubeApplication.current.logError(message);
                        onResult(null, message);
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                String e = HyperCubeApplication.toString(t);
                //Log.e("Error Response:", e);
                HyperCubeApplication.current.logError(e);
                onResult(null, e );
            }
        };
    }
    private T result;
    private String error;
    private JSONObject errorJson;
    public JSONObject getErrorJson(){
        return errorJson;
    }

    public String getError(){
        return error;
    }

    public Promise<T> then(IResultListener<T> action){
        next.add(action);
        return this;
    }

    public Promise<T> then(final Promise<T> p){
        next.add(new IResultListener<T>() {
            @Override
            public void onResult(Promise<T> promise) {
                p.onResult(promise.getResult(),promise.getError());
            }
        });
        return this;
    }

    PopupWindow busy = null;

    public void onStarted(){
        HyperCubeApplication.current.post(new Runnable() {
            @Override
            public void run() {
                if (showProgress)
                    busy = show();
            }
        });
    }

    public void onError(Exception error){
        String e = null;
        if(error != null) {
            e = HyperCubeApplication.toString(error);
        }
        onResult(null,e);
    }

    public void onResult(T r,String error){

        HyperCubeApplication.current.post(new Runnable() {
            @Override
            public void run() {
                if (busy != null) {
                    busy.dismiss();
                }
                busy = null;
            }
        });

        result = r;

        if(error!=null){
            // lets parse...
            error = error.trim();
            if(error.startsWith("{") && error.endsWith("}")) {
                try {
                    JSONTokener tokener = new JSONTokener(error);
                    errorJson = (JSONObject) tokener.nextValue();
                    if (errorJson.has("message")) {
                        this.error = errorJson.optString("message");
                    }else if (errorJson.has("error")) {
                        this.error = errorJson.optString("error");
                    }else if (errorJson.has("errors")) {
                        this.error = errorJson.optString("errors");
                    }else{
                        //this.error = errorJson.toString();
                        StringBuilder sb = new StringBuilder();
                        Iterator<String> it = errorJson.keys();
                        while(it.hasNext()){
                            String key = it.next();
                            sb.append(key);
                            sb.append(' ');
                            sb.append(errorJson.get(key).toString());
                            sb.append("\r\n");
                        }
                        this.error = sb.toString();
                    }
                } catch (Exception ex) {
                    // ignore...
                    this.error = error;
                }
            }else{
                this.error = error;
            }
        }







        HyperCubeApplication.current.post(new Runnable() {
            @Override
            public void run() {
                for (IResultListener<T> resultListener : next) {
                    try {
                        resultListener.onResult(Promise.this);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }


    /**
     *
     * @param promises
     * @return
     */
    public static Promise<String> whenAll(Promise... promises){
        final Promise<String> promise =new Promise<String>();
        final StringBuilder sb = new StringBuilder();
        final List<Object> pending = new ArrayList<Object>(promises.length);
        for (Promise p :promises) {
            pending.add(p);
            p.then(new IResultListener() {
                @Override
                public void onResult(Promise result) {
                    if(result.getError()!=null){
                        sb.append(result.getError());
                    }
                    pending.remove(result);
                    if(pending.isEmpty()){
                        String errors = sb.toString().trim();
                        if(errors.length()>0){
                            promise.onResult(null,errors);
                        }else {
                            promise.onResult(null,null);
                        }
                    }
                }
            });
        }
        return promise;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;


    public <RT> Promise<RT> toPromise(final IFunction<T,RT> converter){
        final Promise<RT> resultPromise = new Promise<>();
        this.then(new IResultListener<T>() {
            @Override
            public void onResult(Promise<T> promise) {
                T result = promise.result;
                RT rt = null;
                if (result != null) {
                    //rt = converter.convert(result);
                    TaskPromise.runAsync(result, converter).then(new IResultListener<RT>() {
                        @Override
                        public void onResult(Promise<RT> promise) {
                            resultPromise.onResult(promise.getResult(),promise.getError());
                        }
                    });
                } else {
                    resultPromise.onResult(rt, promise.getError());
                }
            }
        });
        return resultPromise;
    }


    public static <RT> Promise<RT> withResult(final RT result){
        final Promise<RT> promise = new Promise<>();
        HyperCubeApplication.current.post(new Runnable() {
            @Override
            public void run() {
                promise.onResult(result, null);
            }
        });
        return promise;
    }


    public static <RT> Promise<RT> withError(final Exception ex){
        final Promise<RT> promise = new Promise<>();
        HyperCubeApplication.current.post(new Runnable() {
            @Override
            public void run() {
                promise.onError(ex);
                       }
        });
        return promise;
    }


    public void setResult(T result) {
        this.result = result;
    }
}
