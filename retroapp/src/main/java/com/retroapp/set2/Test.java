package com.retroapp.set2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.retroapp.R;

import java.util.List;

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
    protected static final String CONTENT_TYPE_KEY = "Content-Type";
    protected static final String CONTENT_TYPE_VALUE = "application/json";
    protected static final String AUTHORIZATIONS_VALUE = "46A366EF7C0CAFB8666D29A60FCF6081";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retroactivity);
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<Resident> call2 = apiInterface.doGetResidentList(AUTHORIZATIONS_VALUE,COMMUNITY_ID);
        call2.enqueue(new Callback<Resident>() {
            @Override
            public void onResponse(Call<Resident> call, Response<Resident> response) {
                Resident userList = response.body();
                System.out.println("onResponse:"+userList);
            }

            @Override
            public void onFailure(Call<Resident> call, Throwable t) {
                call.cancel();
            }
        });
    }


}
