package com.retroapp.set2;
/*
 * Created by Mohanraj.S on 28/7/17 for MyDevs.
 */

import com.retroapp.set1.NetworkClass;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiInterface {

   /* @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);*/
   @GET("residents?")
   Call<Resident> doGetResidentList(@Header(Test.AUTHORIZATIONS_KEY)String myAuthValue, @Query("community_id") String mCommunityId);

}
