package com.neurospeech.hypercube.ui;

public interface ModelFilter<T> {

    boolean filter(T item);

}