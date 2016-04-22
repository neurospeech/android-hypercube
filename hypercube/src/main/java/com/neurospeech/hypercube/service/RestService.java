package com.neurospeech.hypercube.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.neurospeech.hypercube.service.json.JacksonConverterFactory;
import com.neurospeech.hypercube.service.json.PromiseConverterFactory;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Gigster on 30-12-2015.
 */
public abstract class RestService {

    //protected final WebkitCookieManagerProxy cookieManagerProxy;
    protected final Context context;
    protected final SharedPreferences preferences;

    public OkHttpClient getClient() {
        return client;
    }

    private OkHttpClient client;

    private Retrofit retrofit;

    protected RestService(Context context){
        this.context = context;

        this.client = createHttpClient();

        //cookieManagerProxy = new WebkitCookieManagerProxy();
        preferences = context.getSharedPreferences("App",Context.MODE_PRIVATE);
        //client.setCookieHandler(cookieManagerProxy);
    }

    protected OkHttpClient createHttpClient() {
        return new OkHttpClient();
    }

    protected <T> T createAPIClient(String baseUrl, Class<T> apiClass){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.client(client);
        builder.addConverterFactory(JacksonConverterFactory.create());
        builder.addCallAdapterFactory(PromiseConverterFactory.create());

        retrofit = builder.build();

        return retrofit.create(apiClass);

    }


}
