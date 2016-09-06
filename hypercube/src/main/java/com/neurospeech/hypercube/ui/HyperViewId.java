package com.neurospeech.hypercube.ui;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by akash.kava on 06-09-2016.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface HyperViewId {
    int value () ;
}
