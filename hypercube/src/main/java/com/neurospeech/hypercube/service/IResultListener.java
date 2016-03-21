package com.neurospeech.hypercube.service;

/**
 * Created by akash.kava on 21-03-2016.
 */
public interface IResultListener<T> {

    void onResult(Promise<T> promise);

}
