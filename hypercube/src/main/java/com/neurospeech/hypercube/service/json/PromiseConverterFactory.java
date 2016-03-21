package com.neurospeech.hypercube.service.json;


import com.fasterxml.jackson.databind.type.TypeFactory;
import com.neurospeech.hypercube.service.Promise;


import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit.Call;
import retrofit.CallAdapter;
import retrofit.Callback;
import retrofit.Converter;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Akash on 12/5/2015.
 */
public class PromiseConverterFactory implements CallAdapter.Factory {

    public static PromiseConverterFactory create(){
        return new PromiseConverterFactory();
    }


    @Override
    public CallAdapter<?> get(final Type returnType, Annotation[] annotations, Retrofit retrofit) {

        Class<?> cls = TypeFactory.rawClass(returnType);

        if(!Promise.class.isAssignableFrom(cls))
            return null;

        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException(
                    "ListenableFuture must have generic type (e.g., ListenableFuture<ResponseBody>)");
        }
        final Type responseType = ((ParameterizedType) returnType).getActualTypeArguments()[0];
        return new CallAdapter<Promise<?>>() {

            @Override
            public Type responseType() {
                return responseType;
            }

            @Override
            public <R> Promise<R> adapt(Call<R> call) {
                Promise<R> promise = new Promise<R>();
                call.enqueue(promise.callback());
                promise.onStarted();

                return promise;
            }
        };
    }

}
