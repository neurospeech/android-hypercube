package com.neurospeech.hypercube.service;

/**
 * Created by akash.kava on 21-03-2016.
 */
public interface IFunction<T,RT> {
    RT run(T item) throws  Exception;
}
