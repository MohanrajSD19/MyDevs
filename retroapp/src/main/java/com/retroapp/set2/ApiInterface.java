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

   /* @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);*/
   @GET(Test.RESIDENTS)
   Call<ResponseBody> doGetResidentList(@Header(Test.AUTHORIZATIONS_KEY)String myAuthValue,@Header(Test.CONTENT_TYPE_KEY)String myContValue, @Query("community_id") String mCommunityId);



   /*Get Method
   * http://staging-monitor.accushield.com/api/kiosk/residents?community_id=e1ecae4e-c342-0806-5ac7-587c5744a96b
   * auth = 46A366EF7C0CAFB8666D29A60FCF6081
   * Content-Type = application/json
   * */
}
