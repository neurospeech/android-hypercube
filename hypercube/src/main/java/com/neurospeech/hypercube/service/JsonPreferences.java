package com.neurospeech.hypercube.service;

/**
 * Created by akash.kava on 21-03-2016.
 */
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonPreferences {

    private final SharedPreferences service;
    private final ObjectMapper objectMapper;

    public JsonPreferences(SharedPreferences service) {
        super();
        this.service = service;
        this.objectMapper = new ObjectMapper();
    }


    public void save(String key, Object value) throws  Exception{
        if(value==null){
            service.edit().remove(key).commit();
            return;
        }
        String valueJson = objectMapper.writer().writeValueAsString(value);
        service.edit().putString(key,valueJson).commit();
    }

    public <T> T read(String key, TypeReference<T> typeReference) throws  Exception{
        String value = service.getString(key, null);
        if(value==null)
            return null;
        return objectMapper.reader(typeReference).readValue(value);
    }

}

