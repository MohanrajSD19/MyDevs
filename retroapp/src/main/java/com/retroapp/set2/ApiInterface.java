package com.retroapp.set2;
/*
 * Created by Mohanraj.S on 28/7/17 for MyDevs.
 */

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiInterface {


   @GET(Test.RESIDENTS)
   Call<ResponseBody> doGetResidentList(@Header(Test.AUTHORIZATIONS_KEY)String myAuthValue,@Header(Test.CONTENT_TYPE_KEY)String myContValue, @Query("community_id") String mCommunityId);

}
