package com.retroapp.set1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.retroapp.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_sync;
    /* OkHttpClient okClient;
     Retrofit retrofitRef;*/
    NetworkClass mNetworkClass = new NetworkClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_sync = (Button) findViewById(R.id.button_sync);
        button_sync.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sync:
                // getResidentData();
                getUserData();
                break;
            default:
                break;
        }

    }

    private void getUserData() {

        if (NetworkClass.isOnline(getApplicationContext())) {
            Retrofit retrofitRef = mNetworkClass.callretrofit();
            MyApiEndPointInterface mAepi = retrofitRef.create(MyApiEndPointInterface.class);
            Call<ResponseBody> result = mAepi.getResidentData(NetworkClass.COMMUNITY_ID, NetworkClass.AUTHORIZATIONS_VALUE);
            //System.out.println("MyResult:"+result.toString());

            NetworkClass.callGetResponseString(getApplicationContext(), new RetrofitResultInterface() {
                @Override
                public void onGetFailed(String message, int msgID) {
                    System.out.println("Fail Response:" + message);
                }

                @Override
                public void onGetSuccess(String getResponse, int msgID) {
                    System.out.println("Success Response:" + getResponse);
                }
            }, result, 1);
        } else {
            Toast.makeText(getApplicationContext(), "Network fails", Toast.LENGTH_SHORT).show();
        }
    }

}

    /*private void getResidentData() {
       if(NetworkClass.isOnline(getApplicationContext())){
           Retrofit retrofitRef =mNetworkClass.callretrofit();
           MyApiEndPointInterface mAepi = retrofitRef.create(MyApiEndPointInterface.class);
           Call<ResponseBody> result = mAepi.getResidentData(NetworkClass.AUTHORIZATIONS_VALUE,NetworkClass.CONTENT_TYPE_VALUE,NetworkClass.COMMUNITY_ID);

           NetworkClass.callGetResponseString(getApplicationContext(), new RetrofitResultInterface() {
               @Override
               public void onGetFailed(String message, int msgID) {
                   System.out.println("Fail Response:"+message);
               }

               @Override
               public void onGetSuccess(String getResponse, int msgID) {
                   System.out.println("Success Response:"+getResponse);
               }
           },result,0);
       }else{
           Toast.makeText(getApplicationContext(),"Network fails",Toast.LENGTH_SHORT).show();
       }



    }
}*/
