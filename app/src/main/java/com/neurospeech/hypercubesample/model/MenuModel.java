package com.neurospeech.hypercubesample.model;

/**
 * Created by  on 08-08-2016.
 */
public class MenuModel {

    private final Class activity;
    public String name;

    public String header;

    public MenuModel(String header, String name, Class activity) {
        super();
        this.name = name;
        this.header = header;
        this.activity = activity;
    }
}
