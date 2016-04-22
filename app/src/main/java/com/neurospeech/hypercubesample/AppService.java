package com.neurospeech.hypercubesample;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neurospeech.hypercube.service.Promise;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by akash.kava on 22-04-2016.
 */
public class AppService extends com.neurospeech.hypercube.service.RestService  {

    API api;

    protected AppService(Context context) {
        super(context);


        api = createAPIClient("http://jsonplaceholder.typicode.com",API.class);
    }


    public Promise<Post[]> posts(){
        return api.posts();
    }

    public Promise<Post> put(Post post){
        return api.put(post.id,post);
    }




    public interface API{

        @GET("/posts")
        Promise<Post[]> posts();


        @PUT("/posts/{id}")
        Promise<Post> put(@Path("id") int id, @Body Post post);

    }

    public static class Post{

        @JsonProperty("id")
        public int id;

        @JsonProperty("userId")
        public int userId;

        @JsonProperty("title")
        public String title;

        @JsonProperty("body")
        public String body;

    }

}
