package com.retroapp.set2;
/*
 * Created by Mohanraj.S on 1/8/17 for MyDevs.
 */

public class Query {

    protected static final String CREATEQUERY = "CREATE TABLE IF NOT EXISTS ";
    protected static final String DROPQUERY = "DROP TABLE IF EXISTS";
    protected static final String UPDATEQUERY = "UPDATE";


    protected static final String TRN_RESIDENTS = CREATEQUERY
            + Constants.RESIDENT_TABLE + "("
            + Constants.COMMUNITY_ID + " TEXT,"
            + Constants.ID + " TEXT,"
            + Constants.NAME + " TEXT,"
            + Constants.SUGAR_ID + " TEXT,"
            + Constants.CREATED_AT + " TEXT,"
            + Constants.UPDATED_AT + " TEXT,"
            + Constants.DO_NOT_DISTURB + " TEXT,"
            + Constants.NOTIFY_ON_VISITS + " TEXT,"
            + Constants.PHONE_MOBILE + " TEXT,"
            + Constants.ROOM + " TEXT,"
            + Constants.FIRST_NAME + " TEXT,"
            + Constants.LAST_NAME + " TEXT,"
            + Constants.RESIDENT_TYPE_ID + " TEXT,"
            + Constants.EXTERNAL_RECORD_ID + " TEXT,"
            + Constants.GENDER + " TEXT,"
            + Constants.NOTE + " TEXT,"
            + Constants.STATUS_C + " TEXT,"
            + Constants.FULL_NAME + " TEXT);";
}
