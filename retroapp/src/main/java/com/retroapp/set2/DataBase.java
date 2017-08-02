package com.retroapp.set2;
/*
 * Created by Mohanraj.S on 1/8/17 for MyDevs.
 */

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase {

    protected final Context mCtx;


    public DataBase(Context mCtx) {
        this.mCtx = mCtx;
    }




    public DataBase open() throws SQLException {
        Constants.mDbHelper = new DataBaseHelper(mCtx);
        Constants.mDb = Constants.mDbHelper.getWritableDatabase();
        return this;
    }

    public static void close() {
        if (Constants.mDb != null) {
            // mDbHelper.close();
            Constants.mDb.close();
        }
    }

     class DataBaseHelper extends SQLiteOpenHelper {

         DataBaseHelper(Context context) {
            //super(context, name, factory, version);
            super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Query.TRN_RESIDENTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(Query.DROPQUERY + " " + Query.TRN_RESIDENTS);
        }


     }
}
