package com.retroapp.set2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.retroapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Created by Mohanraj.S on 28/7/17 for MyDevs.
 */

public class Test extends AppCompatActivity {
    private static String TAG = "Test_Activity";
    private ApiInterface apiInterface;
    protected static final String COMMUNITY_ID = "e1ecae4e-c342-0806-5ac7-587c5744a96b";
    protected static final String BASE_URL = "http://staging-monitor.accushield.com/api/kiosk/";
    protected static final String AUTHORIZATIONS_KEY = "auth";
    protected static final String RESIDENTS = "residents";
    protected static final String CONTENT_TYPE_KEY = "Accept";
    protected static final String CONTENT_TYPE_VALUE = "application/json";
    protected static final String AUTHORIZATIONS_VALUE = "46A366EF7C0CAFB8666D29A60FCF6081";
    String mResponseResult = "";
    TextView txtVw_Respo;
    Button btn_load;
    ArrayList<PojoResidents> residentsArray = new ArrayList<PojoResidents>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retroactivity);
//        grantPermissions();
        configViews();


    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
//            //resume tasks needing this permission
//            //System.out.println("I gave permission");
//            Toast.makeText(Test.this, "Permission Granted", Toast.LENGTH_SHORT).show();
//
//        } else {
//            Toast.makeText(Test.this, "Permission Denied", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void grantPermissions() {
        isStoragePermissionGranted();
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                configViews();
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }


    private void configViews() {

        txtVw_Respo = (TextView) findViewById(R.id.txtVw_response);
        btn_load = (Button) findViewById(R.id.btn_load);
        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //     isStoragePermissionGranted();
                System.out.println("Response output");
                configAPI();
//                displayListView();

            }
        });
    }

    private void configAPI() {
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call2 = apiInterface.doGetResidentList(AUTHORIZATIONS_VALUE, CONTENT_TYPE_VALUE, COMMUNITY_ID);
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        mResponseResult = parseResponse(response);
                        //txtVw_Respo.setText(mResponseResult);
                        try {
                            loadResponse(mResponseResult);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("ResponseResult:" + response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private String parseResponse(Response<ResponseBody> response) {
        String result = "";
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
        result = sb.toString();

        return result;
    }

    private void loadResponse(String parseResult) throws JSONException {
        JSONObject mJsonObj = new JSONObject(parseResult);

        String mStatus = mJsonObj.getString("status");//Constants.RESPONSE_SUCCESS.equals(mStatus)
        if (mStatus.equals(Constants.RESPONSE_SUCCESS)) {
            //System.out.println("DBIns");

            JSONArray residentArr = new JSONArray(mJsonObj.getString("residents"));
            Trn_Residents mTrnResidents = new Trn_Residents(Test.this);
            mTrnResidents.open();

            for (int i = 0; i < residentArr.length(); i++) {

                String comm_id = "";
                String id = "";
                String name = "";
                String sugar_id = "";
                String created_at = "";
                String updated_at = "";
                String do_not_disturb = "";
                String notify_on_visits = "";
                String phone_mobile = "";
                String room = "";
                String first_name = "";
                String last_name = "";
                String resident_type_id = "";
                String external_record_id = "";
                String gender = "";
                String note = "";
                String status_c = "";
                String full_name = "";

                /*JSONObject tempPostObject = residentArr.getJSONObject(i);

                comm_id = validateJSON(tempPostObject, Constants.COMMUNITY_ID);
                id = validateJSON(tempPostObject, Constants.ID);
                sugar_id = validateJSON(tempPostObject, Constants.SUGAR_ID);
                name = validateJSON(tempPostObject, Constants.NAME);
                phone_mobile = validateJSON(tempPostObject, Constants.PHONE_MOBILE);
                full_name = validateJSON(tempPostObject, Constants.FULL_NAME);


                mTrnResidents.insert_Resident(comm_id, id, name, sugar_id, created_at, updated_at, do_not_disturb, notify_on_visits, phone_mobile, room, first_name, last_name, resident_type_id, external_record_id, gender, note, status_c, full_name);

                */

                //method for bulk  and fast insertion
                JSONObject tempPostObject = residentArr.getJSONObject(i);
                PojoResidents mPojo = new PojoResidents();
                mPojo.setCommunity_id(validateJSON(tempPostObject, Constants.COMMUNITY_ID));
                mPojo.setId(validateJSON(tempPostObject,Constants.ID));
                mPojo.setName(validateJSON(tempPostObject,Constants.NAME));
                mPojo.setSugar_id(validateJSON(tempPostObject,Constants.SUGAR_ID));
                mPojo.setCreated_at(validateJSON(tempPostObject,Constants.CREATED_AT) );
                mPojo.setUpdated_at(validateJSON(tempPostObject,Constants.UPDATED_AT));
                mPojo.setDo_not_disturb(validateJSON(tempPostObject,Constants.DO_NOT_DISTURB));
                mPojo.setNotify_on_visits(validateJSON(tempPostObject,Constants.NOTIFY_ON_VISITS) );
                mPojo.setPhone_mobile(validateJSON(tempPostObject,Constants.PHONE_MOBILE));
                mPojo.setRoom(validateJSON(tempPostObject,Constants.ROOM));
                mPojo.setFirst_name(validateJSON(tempPostObject,Constants.FIRST_NAME));
                mPojo.setLast_name(validateJSON(tempPostObject,Constants.LAST_NAME));
                mPojo.setResident_type_id(validateJSON(tempPostObject,Constants.RESIDENT_TYPE_ID));
                mPojo.setExternal_record_id(validateJSON(tempPostObject,Constants.EXTERNAL_RECORD_ID));
                mPojo.setGender(validateJSON(tempPostObject,Constants.GENDER));
                mPojo.setNote(validateJSON(tempPostObject,Constants.NOTE));
                mPojo.setStatus_c(validateJSON(tempPostObject,Constants.STATUS_C));
                mPojo.setFull_name(validateJSON(tempPostObject,Constants.FULL_NAME));

                 residentsArray.add(mPojo);



            }
           // Trn_Residents.close();

            //displayListView();
        if (residentsArray.size() > 0) {
            //show the Database value
            try {
                Trn_Residents mTrn = new Trn_Residents(Test.this);
                mTrn.deleteAll();
                mTrn.open();
                mTrn.insertResidentFast(residentsArray);
                Cursor crResident = mTrn.fetch();
                if(crResident.getCount() >0){
                    Toast.makeText(getApplicationContext(), "Record Inserted:"+crResident.getCount(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            //Toast.makeText(getApplicationContext(), "Data Downloaded", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("STATUS fails");
        }

    }

    private String validateJSON(JSONObject tempPostObject, String jsonKey) {
        String result = "";
        if (!tempPostObject.isNull(jsonKey)) {
            result = tempPostObject.optString(jsonKey);
        }

        return result;
    }

    // we used AsyncTask so it won't block the UI thread during inserts.
    class AsyncInsertData extends AsyncTask<String, String, String> {

        DataBase databaseHandler;
        String type,mCount;
        long timeElapsed;

        protected AsyncInsertData(String type,String mCount){
            this.type  = type;
            this.mCount = mCount;
            this.databaseHandler = new DataBase(Test.this);
        }

        // @type - can be 'normal' or 'fast'
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //tvStatus.setText("Inserting " + mCount + " records...");
        }

        @Override
        protected String doInBackground(String... aurl) {

            try {

                // get number of records to be inserted
                //int insertCount = Integer.parseInt(mCount);

                // empty the table
                //databaseHandler.deleteRecords();

                // keep track of execution time
                long lStartTime = System.nanoTime();

                if (type.equals("normal")) {
                    //databaseHandler.insertNormal(insertCount);
                } else {
                    //databaseHandler.insertFast(insertCount);
                }

                // execution finised
                long lEndTime = System.nanoTime();

                // display execution time
                timeElapsed = lEndTime - lStartTime;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String unused) {
           /* tvStatus.setText("Done inserting " + databaseHandler.countRecords() + " records. Time elapsed: " + timeElapsed / 1000000 + " ms.");*/
        }

    }


    private void displayListView() {
        System.out.println("DisplayListView");
        Trn_Residents mTrnResidents = new Trn_Residents(Test.this);
        mTrnResidents.open();
        Cursor mCr_Resident = mTrnResidents.fetch();
        if (mCr_Resident.getCount() > 0) {
            for (int i = 0; i < mCr_Resident.getCount(); i++) {
                mCr_Resident.moveToPosition(i);
                String community_id = mCr_Resident.getString(mCr_Resident.getColumnIndex("" + Constants.COMMUNITY_ID + ""));
                String full_name = mCr_Resident.getString(mCr_Resident.getColumnIndex("" + Constants.FULL_NAME + ""));
                String phone_mobile = mCr_Resident.getString(mCr_Resident.getColumnIndex("" + Constants.PHONE_MOBILE + ""));
                String sugar_id = mCr_Resident.getString(mCr_Resident.getColumnIndex("" + Constants.SUGAR_ID + ""));

                System.out.println("-----------------------------");
                System.out.println("full_name:" + full_name);
                System.out.println("phone_mobile:" + phone_mobile);
                System.out.println("sugar_id:" + sugar_id);
                System.out.println("community_id:" + community_id);
            }
            Trn_Residents.close();

        }
    }
}






/*
try{
        db.beginTransaction();
        for each record in the list {
        do_some_processing();
        if (line represent a valid  entry) {
        db.insert(SOME_TABLE, null, SOME_VALUE);
        }
        some_other_processing();
        }
        db.setTransactionSuccessful();
        } catch (SQLException e) {
        } finally {
        db.endTranscation();
        }*/
/*
* do {
* int residentStoredDataPos = 0;
                //To set the Post Array values to List View
                PojoResidents residentPOJO = residentsArray.get(residentStoredDataPos);
                String comm_id = residentPOJO.getCommunity_id();
                String id = residentPOJO.getId();
                String name = residentPOJO.getName();
                String sugar_id = residentPOJO.getSugar_id();
                String created_at = residentPOJO.getCreated_at();
                String updated_at = residentPOJO.getUpdated_at();
                String do_not_disturb = residentPOJO.getDo_not_disturb();
                String notify_on_visits = residentPOJO.getNotify_on_visits();
                String phone_mobile = residentPOJO.getPhone_mobile();
                String room = residentPOJO.getRoom();
                String first_name = residentPOJO.getFirst_name();
                String last_name = residentPOJO.getLast_name();
                String resident_type_id = residentPOJO.getResident_type_id();
                String external_record_id = residentPOJO.getExternal_record_id();
                String gender = residentPOJO.getGender();
                String note = residentPOJO.getNote();
                String status_c = residentPOJO.getStatus_c();
                String full_name = residentPOJO.getFull_name();

                //System.out.println("------Resident:" + residentStoredDataPos + "\tcomm_id:" + comm_id + " \t name:" + name);

                residentStoredDataPos++;*/
// Increase the position of array-list    } while (residentStoredDataPos < residentsArray.size());