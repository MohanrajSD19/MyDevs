package com.retroapp.set2;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Created by anupamchugh on 05/01/17.
 */

class APIClient {

    private static Retrofit retrofit;
    //private static Retrofit retrofit = null;
//
//    static Retrofit getClient() {
//        //try {
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//
//
//            retrofit = new Retrofit.Builder()
//                    .baseUrl("http://staging-monitor.accushield.com/api/kiosk/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(client)
//                    .build();
//        //}catch(Exception e){e.printStackTrace();}
//        return retrofit;
//    }

    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("http://staging-monitor.accushield.com/api/kiosk/").client(getOkhttpClient()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static OkHttpClient getOkhttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }
}
