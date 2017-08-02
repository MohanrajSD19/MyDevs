package com.retroapp.set2;
/*
 * Created by Mohanraj.S on 1/8/17 for MyDevs.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class Trn_Residents extends DataBase {


    public Trn_Residents(Context mCtx) {
        super(mCtx);
    }

    public long insert_Resident( String comm_id,String id,
            String name,
            String sugar_id,
            String created_at,
            String updated_at,String do_not_disturb,
            String notify_on_visits,
            String phone_mobile,
            String room,
            String first_name,
            String last_name,
            String resident_type_id,
            String external_record_id,
            String gender,
            String note,
            String status_c,
            String full_name) {

        System.out.println("DBIns");
        


        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put( Constants.COMMUNITY_ID, comm_id );
        mCVArgs.put( Constants.ID, id );
        mCVArgs.put( Constants.NAME, name );
        mCVArgs.put( Constants.SUGAR_ID, sugar_id );
        mCVArgs.put( Constants.CREATED_AT, created_at );
        mCVArgs.put( Constants.UPDATED_AT, updated_at );
        mCVArgs.put( Constants.DO_NOT_DISTURB, do_not_disturb );
        mCVArgs.put( Constants.NOTIFY_ON_VISITS, notify_on_visits );
        mCVArgs.put( Constants.PHONE_MOBILE, phone_mobile );
        mCVArgs.put(Constants.ROOM, room);
        mCVArgs.put(Constants.FIRST_NAME, first_name);
        mCVArgs.put(Constants.LAST_NAME, last_name);
        mCVArgs.put(Constants.RESIDENT_TYPE_ID, resident_type_id);
        mCVArgs.put(Constants.EXTERNAL_RECORD_ID, external_record_id);
        mCVArgs.put(Constants.GENDER, gender);
        mCVArgs.put(Constants.NOTE, note);
        mCVArgs.put(Constants.STATUS_C, status_c);
        mCVArgs.put(Constants.FULL_NAME, full_name);


        return Constants.mDb.insert( Constants.RESIDENT_TABLE, null,
                mCVArgs );

    }


    public Cursor fetch( ) throws SQLException {
        System.out.println("DBIns");System.out.println("DBIns");
        String query = "Select * from "
                + Constants.RESIDENT_TABLE + ";";
        Cursor mCursorFetch = Constants.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }
    
}
