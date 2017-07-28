package com.retroapp.set1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.retroapp.R;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mohanraj.S on 27/7/17 for MyDevs.
 */

public class RetroActivity extends AppCompatActivity implements View.OnClickListener{

    OkHttpClient okClient;
    Retrofit retrofitRef;
    MyApiEndPointInterface service;
    private Button button_sync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_sync = (Button)findViewById(R.id.button_sync);
        button_sync.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_sync:
                getResidentData();
                break;
            default:
                break;
        }

    }

    private void getResidentData() {
        okClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
//.addHeader(NetworkClass.AUTHORIZATIONS_KEY, NetworkClass.AUTHORIZATIONS_VALUE)
                Request request = chain.request().newBuilder()
                        .addHeader(NetworkClass.CONTENT_TYPE_KEY, NetworkClass.CONTENT_TYPE_VALUE)
                        .build();
                return chain.proceed(request);
            }
        }).build();


        retrofitRef = new Retrofit.Builder()
                .baseUrl(NetworkClass.BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofitRef.create(MyApiEndPointInterface.class);
        try {
            Call<ResponseBody> callLogin = service.getTimelinePost(NetworkClass.AUTHORIZATIONS_VALUE);
            callLogin.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    Log.i("RETRO", response.message());
                    System.out.println("RETRO:"+response.message());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
