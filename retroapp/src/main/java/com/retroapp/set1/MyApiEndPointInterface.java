package com.retroapp.set1;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/*
 * Created by Mohanraj.S on 27/7/17 for MyDevs.
 */

public interface MyApiEndPointInterface {

    //getResidentData
    //@GET("residents?community_id=e1ecae4e-c342-0806-5ac7-587c5744a96b")
    /*@GET("residents?community_id={community_id}")
    Call<ResponseBody> getResidentData(@Header(NetworkClass.AUTHORIZATIONS_KEY)String myAuthValue,@Header(NetworkClass.CONTENT_TYPE_KEY)String myContentValue,@Query("community_id") String mCommunityId);*/
   /* @Headers({
            "auth: 46A366EF7C0CAFB8666D29A60FCF6081",
            "Content-Type: application/json"

    })
    @GET("residents/community_id={community_id}")
    Call<ResponseBody> getResidentData(@Path("community_id") String mCommunityId);*/

    //getNewResidents Details
    @GET("getposts/?&community_id=e1ecae4e-c342-0806-5ac7-587c5744a96b")
    Call<ResponseBody> getTimelinePost(@Header(NetworkClass.AUTHORIZATIONS_KEY)String myAuthValue);

    @GET("residents")
    Call<ResponseBody> getResidentData(@Query("community_id") String mCommunityId ,@Header(NetworkClass.AUTHORIZATIONS_KEY)String myAuthValue);



}
