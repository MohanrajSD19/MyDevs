package com.retroapp.set2;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Created by anupamchugh on 05/01/17.
 */

class APIClient {

    private static Retrofit retrofit ;
    //private static Retrofit retrofit = null;

    static Retrofit getClient() {
        //try {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(Test.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        //}catch(Exception e){e.printStackTrace();}
        return retrofit;
    }

}
