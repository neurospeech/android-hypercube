package com.neurospeech.hypercubesample.model;

/**
 * Created by  on 08-08-2016.
 */
public class MenuModel {

    public String name;

    public String header;

    public int layout;

    public MenuModel(String header, String name, int layout) {
        super();
        this.name = name;
        this.header = header;
        this.layout = layout;
    }
}
