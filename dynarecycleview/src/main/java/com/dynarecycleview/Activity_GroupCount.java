package com.dynarecycleview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Mohanraj.S on 12/7/17 for MyDevs.
 */

public class Activity_GroupCount extends AppCompatActivity {
    EditText edTxtGpcount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_count);
        Button button_1 = (Button)findViewById(R.id.button);
         edTxtGpcount =(EditText) findViewById(R.id.edTxtGpcount);
        button_1.setOnClickListener((new OnClickListener()
        {
            public void onClick(View v)
            {
                String mCount =edTxtGpcount.getText().toString().trim();
                int mTxtCount = Integer.parseInt(mCount);
                if(mTxtCount>0 && mTxtCount<=10 ) {
                    Intent io = new Intent(Activity_GroupCount.this, ActivityGroupMembers.class);
                    io.putExtra("groupcount", mCount);
                    startActivity(io);
                    finish();
                }else if(mTxtCount>10){
                    Toast.makeText(getApplicationContext(),"Maximum 10 Groups",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Please Group Count",Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }
}
