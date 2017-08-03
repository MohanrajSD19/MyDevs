package com.retroapp.set2;
/*
 * Created by Mohanraj.S on 1/8/17 for MyDevs.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

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


        return mDb.insert( Constants.RESIDENT_TABLE, null,
                mCVArgs );

    }



    public void insertResidentFast(ArrayList<PojoResidents> mArrayList) {

        // you can use INSERT only
        String sql = "INSERT OR REPLACE INTO " + Constants.RESIDENT_TABLE + " ("
                + Constants.COMMUNITY_ID +","
                + Constants.ID +","
                + Constants.NAME +","
                + Constants.SUGAR_ID +","
                + Constants.CREATED_AT+","
                + Constants.UPDATED_AT+","
                + Constants.DO_NOT_DISTURB+","
                + Constants.NOTIFY_ON_VISITS+","
                + Constants.PHONE_MOBILE+","
                + Constants.ROOM+","
                + Constants.FIRST_NAME+","
                + Constants.LAST_NAME+","
                + Constants.RESIDENT_TYPE_ID+","
                + Constants.EXTERNAL_RECORD_ID+","
                + Constants.GENDER+","
                + Constants.NOTE+","
                + Constants.STATUS_C+","
                + Constants.FULL_NAME+ ") VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

        //SQLiteDatabase db = this.getWritableDatabase();
        mDb = mDbHelper.getWritableDatabase();

        /*
         * According to the docs http://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html
         * Writers should use beginTransactionNonExclusive() or beginTransactionWithListenerNonExclusive(SQLiteTransactionListener)
         * to start a transaction. Non-exclusive mode allows database file to be in readable by other threads executing queries.
         */
        mDb.beginTransactionNonExclusive();
        // db.beginTransaction();

        SQLiteStatement stmt = mDb.compileStatement(sql);

        for(int i=0;i<mArrayList.size();i++){
            PojoResidents item = mArrayList.get(i);

            /*if(i == (numberOfRows-1)){
                lastItem = item;
            }*/
            stmt.bindString(1, item.getCommunity_id());
            stmt.bindString(2, item.getId());
            stmt.bindString(3, item.getName());
            stmt.bindString(4, item.getSugar_id());
            stmt.bindString(5, item.getCreated_at());
            stmt.bindString(6, item.getUpdated_at());
            stmt.bindString(7, item.getDo_not_disturb());
            stmt.bindString(8, item.getNotify_on_visits());
            stmt.bindString(9, item.getPhone_mobile());
            stmt.bindString(10, item.getRoom());
            stmt.bindString(11, item.getFirst_name());
            stmt.bindString(12, item.getLast_name());
            stmt.bindString(13, item.getResident_type_id());
            stmt.bindString(14, item.getExternal_record_id());
            stmt.bindString(15, item.getGender());
            stmt.bindString(16, item.getNote());
            stmt.bindString(17, item.getStatus_c());
            stmt.bindString(18, item.getFull_name());
            stmt.execute();




        }

        mDb.setTransactionSuccessful();
        mDb.endTransaction();
       // mDb.close();
    }


    public Cursor fetch( ) throws SQLException {
        //mDbHelper.open();
        String query = "Select * from "
                + Constants.RESIDENT_TABLE + ";";
        Cursor mCursorFetch = mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }


    public Cursor deleteAll() throws SQLException {
        Cursor mCursorDeleteAll = mDb.rawQuery( "Delete FROM "
                + Constants.RESIDENT_TABLE, null );
        if ( mCursorDeleteAll != null ) {
            mCursorDeleteAll.moveToFirst();
        }
        return mCursorDeleteAll;
    }
    
}
