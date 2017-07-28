

package com.retroapp.set1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/*
 * Created by Mohan on 27/046/17.
 */

public class NetworkClass {
    //http://staging-monitor.accushield.com/api/kiosk/residents?community_id=e1ecae4e-c342-0806-5ac7-587c5744a96b
    protected static Retrofit retrofitRef;
    protected static final String BASE_URL = "http://staging-monitor.accushield.com/api/kiosk/";
    protected static final String AUTHORIZATIONS_KEY = "auth";
    protected static final String CONTENT_TYPE_KEY = "Content-Type";
    protected static final String CONTENT_TYPE_VALUE = "application/json";
    protected static final String AUTHORIZATIONS_VALUE = "46A366EF7C0CAFB8666D29A60FCF6081";
    protected static final String PARSE_STATUS_KEY = "status";
    protected static final String PARSE_STATUS_VALUE = "error";
    protected static final String PARSE_ERROR_KEY = "errormsg";
    protected static final String PARSE_ERROR_VALUE = "upgrade new version";
    protected static final String TAG_STATUS = "status";
    protected static final String TAG_ERROR = "Error";
    protected static final String TAG_ERRORMESSAGE = "Error Message";
    protected static final String COMMUNITY_ID = "e1ecae4e-c342-0806-5ac7-587c5744a96b";

    private static SharedPreferences mSharedPreference;
    private static String mUserid;




    public static String getSharedPreferencesUserid(Context context) {
        mSharedPreference = context.getSharedPreferences("LoginCredentials", MODE_PRIVATE);
        mUserid = (mSharedPreference.getString("UserId", ""));
        return mUserid;
    }

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        NetworkInfo typemo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo tywi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
        NetworkInfo tywifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (netInfo != null && netInfo.isConnectedOrConnecting()
                || typemo != null && typemo.isConnectedOrConnecting()
                || tywi != null && tywi.isConnectedOrConnecting()
                || tywifi != null && tywifi.isConnectedOrConnecting()) {

            return true;
        } else {

            return false;
        }

    }

    public Retrofit callretrofit() {

        //Start
        OkHttpClient okClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)

                .addInterceptor(new Interceptor() {
                    public Response intercept(Chain chain) throws IOException {

                        Request request = chain.request().newBuilder()
                                .build();
                        // Default  Details
                        // .addHeader("Accept","Application/JSON").build();
                        return chain.proceed(request);
                    }
                }).build();


        retrofitRef = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // End
        return retrofitRef;


    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    public static void callGetResponseString(final Context mContext, final RetrofitResultInterface resultInterface, Call<ResponseBody> result, final int msgId) {

        if(result!=null){
            System.out.println("Result Not null");
            result.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
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
                        String result = sb.toString();

                        try {
                            JSONObject mAppUpdateObj = new JSONObject(result);
                            if (mAppUpdateObj.optString(PARSE_STATUS_KEY).equals(PARSE_STATUS_VALUE)) {

                                if (mAppUpdateObj.optString(PARSE_ERROR_KEY).equals(PARSE_ERROR_VALUE)) {
                                    Toast.makeText(mContext,PARSE_ERROR_VALUE,Toast.LENGTH_SHORT).show();

                                } else if (mAppUpdateObj.optString(TAG_STATUS).equals(TAG_ERROR)) {
                                    resultInterface.onGetFailed(mAppUpdateObj.optString(TAG_ERRORMESSAGE), msgId);
                                    /*RecyclerViewWithFooter mView = new RecyclerViewWithFooter(mContext);
                                    if (mView.isLoadMoreEnable()){
                                        mView.setEnd();
                                    }*/
                                } else {
                                    resultInterface.onGetSuccess(result, msgId);
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {


                       /* APIError error = ErrorUtils.parseError(response);
                        resultInterface.onGetFailed(error.getErrorDescription(), msgId);
                        AppLog.e("getErrorDescription",response.code() + "");
                        AppLog.e("getError", error.getError() + "");*/

                        Toast.makeText(mContext,TAG_ERRORMESSAGE,Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    resultInterface.onGetFailed(t.getMessage(), msgId);
                    //AppLog.e("getErrorDescription", t.getMessage() + "");
                    Toast.makeText(mContext,TAG_ERRORMESSAGE,Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            System.out.println("Result null");
        }


        }





}

