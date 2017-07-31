package com.retroapp.set2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.retroapp.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Created by Mohanraj.S on 28/7/17 for MyDevs.
 */

public class Test extends AppCompatActivity  {

    private ApiInterface apiInterface;
    protected static final String COMMUNITY_ID = "e1ecae4e-c342-0806-5ac7-587c5744a96b";
    protected static final String BASE_URL = "http://staging-monitor.accushield.com/api/kiosk/";
    protected static final String AUTHORIZATIONS_KEY = "auth";
    protected static final String RESIDENTS = "residents";
    protected static final String CONTENT_TYPE_KEY = "Accept";
    protected static final String CONTENT_TYPE_VALUE = "application/json";
    protected static final String AUTHORIZATIONS_VALUE = "46A366EF7C0CAFB8666D29A60FCF6081";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retroactivity);
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call2 = apiInterface.doGetResidentList(AUTHORIZATIONS_VALUE,CONTENT_TYPE_VALUE,COMMUNITY_ID);
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        parseResponse(response);
                    }
                }else{
                    System.out.println("onResponse:"+response.body());
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private String parseResponse(Response<ResponseBody> response){
        String result ="";
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = sb.toString();
        System.out.println("onResponse:"+result);
        return result;
    }
}
