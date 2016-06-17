package com.neurospeech.hypercube.service.json;


import com.fasterxml.jackson.databind.type.TypeFactory;
import com.neurospeech.hypercube.HyperCubeApplication;
import com.neurospeech.hypercube.service.Promise;


import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Akash on 12/5/2015.
 */
public class PromiseConverterFactory extends CallAdapter.Factory {

    public static PromiseConverterFactory create(){
        return new PromiseConverterFactory();
    }


    @Override
    public CallAdapter<?> get(final Type returnType, Annotation[] annotations, Retrofit retrofit) {

        try {
            Class<?> cls = TypeFactory.rawClass(returnType);

            if (!Promise.class.isAssignableFrom(cls))
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

                    try {
                        Promise<R> promise = new Promise<R>();
                        call.enqueue(promise.callback(PromiseConverterFactory.this));
                        promise.onStarted();

                        return promise;
                    }catch (Exception ex){
                        HyperCubeApplication.current.logError(ex);
                        throw ex;
                    }
                }
            };
        }catch (Exception ex){

            HyperCubeApplication.current.logError(ex);

            throw ex;
        }
    }

    public boolean isResponseSuccess(okhttp3.Response raw) {
        return raw.isSuccessful();
    }
}
