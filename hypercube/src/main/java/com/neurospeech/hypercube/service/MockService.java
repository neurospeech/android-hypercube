package com.neurospeech.hypercube.service;

import android.os.AsyncTask;

import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * You can use this class to mock your API calls, all you have to do is
 * derive from this class and implement your service API and send mock results
 * by calling
 *
 *      sendResult(result);
 *      sendError(error);
 */
public class MockService {

    /**
     * Default is 100 milliseconds...
     * @return
     */
    public long getArtificialNetworkDelay() {
        return artificialNetworkDelay;
    }

    /**
     * Sets delay before every method call to simulate network delay, in milliseconds
     * @param artificialNetworkDelay
     */
    public void setArtificialNetworkDelay(long artificialNetworkDelay) {
        this.artificialNetworkDelay = artificialNetworkDelay;
    }

    long artificialNetworkDelay = 100;

    protected Promise<ResponseBody> sendResponse(String text){
        return sendResult((ResponseBody) new MockResponse(text));
    }

    protected <T> Promise<T> sendResult(T item){
        return sendResult(item,null);
    }

    protected <T> Promise<T> sendError(String error){
        return sendResult(null,error);
    }

    protected  <T> Promise<T> sendResult(final T item, final String error){
        final Promise<T> p = new Promise<T>();
        (new AsyncTask<T,Integer,Object>(){
            @Override
            protected Object doInBackground(T... params) {
                if(artificialNetworkDelay>0){
                    try {
                        Thread.sleep(artificialNetworkDelay, 0);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                p.onResult(item,error);
                return null;
            }
        }).execute(item);
        p.onStarted();
        return p;
    }



    public  static class MockResponse extends ResponseBody{


        private final String content;
        private final MediaType _contentType;

        public MockResponse(String content, MediaType _contentType)
        {
            super();
            this.content = content;
            this._contentType = _contentType;
        }

        public  MockResponse(String content){
            this(content, MediaType.parse("text/plain"));
        }

        @Override
        public MediaType contentType() {
            return _contentType;
        }

        @Override
        public long contentLength() {
            return content.length();
        }

        @Override
        public BufferedSource source() {
            Buffer b = new Buffer();
            b.writeString(content, Charset.forName("UTF-8"));
            return b;
        }
    }
}
