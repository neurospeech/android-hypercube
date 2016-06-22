package com.neurospeech.hypercubesample;

import com.neurospeech.hypercube.service.MockService;
import com.neurospeech.hypercube.service.Promise;

import retrofit2.http.Body;
import retrofit2.http.Path;

/**
 * Created by akash.kava on 22-06-2016.
 */
public class MockAppServiceAPI extends MockService implements AppService.API
{

    public MockAppServiceAPI(){
        setArtificialNetworkDelay(5000);
    }


    @Override
    public Promise<AppService.Post[]> posts() {
        AppService.Post[] posts = new AppService.Post[1];
        posts[0] = new AppService.Post();
        return sendResult(posts);
    }

    @Override
    public Promise<AppService.Post> put(@Path("id") int id, @Body AppService.Post post) {
        return sendResult(post);
    }
}
