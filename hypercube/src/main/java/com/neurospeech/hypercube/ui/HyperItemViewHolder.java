package com.neurospeech.hypercube.ui;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * For each ViewHolder, value will store corresponding layout id and model type
 * Retention is a way to store relationships between layout view holder and its corresponding model
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface HyperItemViewHolder {
    int value () ;
    Class modelType();
}
