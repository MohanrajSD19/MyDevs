package com.sqlitetransaction;
/*
 * Created by Mohanraj.S on 3/8/17 for MyDevs.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

 public class DatabaseHandler extends SQLiteOpenHelper {

        // for our logs
        public static final String TAG = "DatabaseHandler.java";

        // database version
        private static final int DATABASE_VERSION = 7;

        // database name
        protected static final String DATABASE_NAME = "NinjaDatabase2";

        // table details
        public String tableName = "locations";
        public String fieldObjectId = "id";
        public String fieldObjectName = "name";
        public String fieldObjectDescription = "description";

        // constructor
        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // creating table
        @Override
        public void onCreate(SQLiteDatabase db) {

            String sql = "";

            sql += "CREATE TABLE " + tableName;
            sql += " ( ";
            sql += fieldObjectId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
            sql += fieldObjectName + " TEXT, ";
            sql += fieldObjectDescription + " TEXT ";
            sql += " ) ";

            db.execSQL(sql);

            // create the index for our INSERT OR REPLACE INTO statement.
            // this acts as the WHERE name="name input" AND description="description input"
            // if that WHERE clause is true, I mean, it finds the same name and description in the database,
            // it will be REPLACEd.
            // ELSE, what's in the database will remain and the input will be INSERTed (new record)
            String INDEX = "CREATE UNIQUE INDEX locations_index ON "
                    + tableName + " (name, description)";

            db.execSQL(INDEX);
        }


        // When upgrading the database, it will drop the current table and recreate.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            String sql = "DROP TABLE IF EXISTS " + tableName;
            db.execSQL(sql);

            onCreate(db);
        }

        // insert data using transaction and prepared statement
        public void insertFast(int insertCount) {

            // you can use INSERT only
            String sql = "INSERT OR REPLACE INTO " + tableName + " ( name, description ) VALUES ( ?, ? )";

            SQLiteDatabase db = this.getWritableDatabase();

        /*
         * According to the docs http://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html
         * Writers should use beginTransactionNonExclusive() or beginTransactionWithListenerNonExclusive(SQLiteTransactionListener)
         * to start a transaction. Non-exclusive mode allows database file to be in readable by other threads executing queries.
         */
            db.beginTransactionNonExclusive();
            // db.beginTransaction();

            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=1; x<=insertCount; x++){

                stmt.bindString(1, "Name # " + x);
                stmt.bindString(2, "Description # " + x);

                stmt.execute();
                stmt.clearBindings();

            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }

        // inserts the record without using transaction and prepare statement
        public void insertNormal(int insertCount){
            try{

                SQLiteDatabase db = this.getWritableDatabase();

                for(int x=1; x<=insertCount; x++){

                    ContentValues values = new ContentValues();
                    values.put(fieldObjectName, "Name # " + x);
                    values.put(fieldObjectDescription, "Description # " + x);

                    db.insert(tableName, null, values);

                }

                db.close();

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        // deletes all records
        public void deleteRecords(){

            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from "+ tableName);
            db.close();
        }

        // count records
        public int countRecords(){

            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.rawQuery("SELECT count(*) from " + tableName, null);
            cursor.moveToFirst();

            int recCount = cursor.getInt(0);

            cursor.close();
            db.close();

            return recCount;
        }

    }
