package com.neurospeech.hypercubesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.neurospeech.hypercube.HyperCubeApplication;
import com.neurospeech.hypercube.service.IResultListener;
import com.neurospeech.hypercube.service.Promise;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        HyperCubeApplication.current.post(new Runnable() {
            @Override
            public void run() {
                runService();

            }
        },1000);

    }

    private void runService() {

        Toast.makeText(MainActivity.this,"Running Service",Toast.LENGTH_SHORT).show();

        final AppService service = new AppService(this);

        service.posts().then(new IResultListener<AppService.Post[]>() {
            @Override
            public void onResult(Promise<AppService.Post[]> promise) {
                if(promise.getError()!=null){
                    Toast.makeText(MainActivity.this,promise.getError(),Toast.LENGTH_SHORT).show();
                }else{
                    final AppService.Post first = promise.getResult()[0];
                    first.body = "testing";
                    service.put(first).then(new IResultListener<AppService.Post>() {
                        @Override
                        public void onResult(Promise<AppService.Post> promise) {
                            if(promise.getError()!=null) {
                                Toast.makeText(MainActivity.this, promise.getError(), Toast.LENGTH_SHORT).show();
                            }else{
                                if(first.body != promise.getResult().body){
                                    Toast.makeText(MainActivity.this, "Not Saved", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}
