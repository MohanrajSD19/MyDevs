package com.batterylogs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Mohanraj.S on 24/7/17 for MyDevs.
 */

public class BatteryLogAcitivty extends AppCompatActivity {
    private ImageView imgVw;

    //broadcast class is used as a nested class
    private BroadcastReceiver mbcr=new BroadcastReceiver()
    {
        //onReceive method will receive updates
        public void onReceive(Context c, Intent i)
        {
            //initially level has 0 value
            //after getting update from broadcast receiver
            //it will change and give battery status
            int level=i.getIntExtra("level", 0);
            //initialize all objects
            ProgressBar pb=(ProgressBar)findViewById(R.id.progressBar1);
            TextView tv=(TextView)findViewById(R.id.textView1);
            //set level of progress bar
            pb.setProgress(level);
            //display level on text view
            tv.setText("Batterylevel:"+Integer.toString(level)+"%");
            //start a song when the battery level touches 100%
            if(level==53)
            {
                try
                {
                    //Save small.mp4 in assets folder
                    //we can not start a media file from the drawable folder directly in broadcast method
                    //hence we used the assets folder
                    AssetFileDescriptor afd=getAssets().openFd("small.mp4");
                    MediaPlayer mp=new MediaPlayer();
                    //set file and starting point and ending point in bytes
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mp.prepare();
                    //start song
                    mp.start();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }







        }
    };


    /** Called when the activity is first created. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set layout
        setContentView(R.layout.battery_log);
        imgVw =(ImageView)findViewById(R.id.imgVw);
        //register broadcast receiver
        //Get battery changed status
        //we can get more options like power connect, disconnect, call, etc.
        //To get more options, write Intent followed by a dot(.) and press CTRL+Space
        //you will get all the options
        registerReceiver(mbcr,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
}


