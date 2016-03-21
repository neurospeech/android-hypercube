package com.neurospeech.hypercube.service;

public interface IProgressResultListener<T> extends IResultListener<T> {

    void onProgress(int progress, int total);

}
