package com.sqlitetransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity.java";
    EditText editTextRecordNum;
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.buttonNormalInsert:
                        new AsyncInsertData("normal",editTextRecordNum.getText().toString()).execute();
                        break;
                    case R.id.buttonFastInsert:
                        new AsyncInsertData("fast",editTextRecordNum.getText().toString()).execute();
                        break;
                }
            }
        };

        // EditText for entering desired number of records to be inserted
        editTextRecordNum = (EditText) findViewById(R.id.editTextRecordNum);

        // Button for normal and fast insert
        findViewById(R.id.buttonNormalInsert).setOnClickListener(handler);
        findViewById(R.id.buttonFastInsert).setOnClickListener(handler);

        // status TextView
        tvStatus = (TextView) findViewById(R.id.textViewStatus);
    }




    // we used AsyncTask so it won't block the UI thread during inserts.
    class AsyncInsertData extends AsyncTask<String, String, String> {

        DatabaseHandler databaseHandler;
        String type,mCount;
        long timeElapsed;

        protected AsyncInsertData(String type,String mCount){
            this.type  = type;
            this.mCount = mCount;
            this.databaseHandler = new DatabaseHandler(MainActivity.this);
        }

        // @type - can be 'normal' or 'fast'
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvStatus.setText("Inserting " + mCount + " records...");
        }

        @Override
        protected String doInBackground(String... aurl) {

            try {

                // get number of records to be inserted
                int insertCount = Integer.parseInt(mCount);

                // empty the table
                databaseHandler.deleteRecords();

                // keep track of execution time
                long lStartTime = System.nanoTime();

                if (type.equals("normal")) {
                    databaseHandler.insertNormal(insertCount);
                } else {
                    databaseHandler.insertFast(insertCount);
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
            tvStatus.setText("Done inserting " + databaseHandler.countRecords() + " records. Time elapsed: " + timeElapsed / 1000000 + " ms.");
        }

    }
}







