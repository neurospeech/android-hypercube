package com.neurospeech.hypercube.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

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

    public JsonPreferences getJsonPreferences() {
        return jsonPreferences;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    protected final JsonPreferences jsonPreferences;

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

        jsonPreferences = new JsonPreferences(preferences);

        //client.setCookieHandler(cookieManagerProxy);
    }

    protected OkHttpClient createHttpClient() {
        return new OkHttpClient();
    }

    protected <T> T createAPIClient(String baseUrl, Class<T> apiClass){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.client(client);
        builder.addConverterFactory(createConverterFactory());
        builder.addCallAdapterFactory(createCallAdapterFactory());

        retrofit = builder.build();

        return retrofit.create(apiClass);

    }

    @NonNull
    protected PromiseConverterFactory createCallAdapterFactory() {
        return PromiseConverterFactory.create();
    }

    @NonNull
    protected JacksonConverterFactory createConverterFactory() {
        return JacksonConverterFactory.create();
    }


}
