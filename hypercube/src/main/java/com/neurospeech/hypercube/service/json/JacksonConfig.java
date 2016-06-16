package com.neurospeech.hypercube.service.json;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by  on 16-06-2016.
 */
public abstract class JacksonConfig {

    public static JacksonConfig defaultConfig;

    public abstract void setup(ObjectMapper up);
}
