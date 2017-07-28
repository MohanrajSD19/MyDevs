package com.retroapp.set1;

/*
 * Created by Mohanraj.S on 27/7/17 for MyDevs.
 */

public interface RetrofitResultInterface {
    void onGetFailed(String message, int msgID);

    void onGetSuccess(String getResponse, int msgID);
}
