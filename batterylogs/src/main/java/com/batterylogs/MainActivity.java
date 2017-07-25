package com.batterylogs;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends Activity {
    ImageView imgVw_BatteryStatus;
    android.content.Context Context;
    Animation animationBlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the application context
        Context = getApplicationContext();
        animationBlink = AnimationUtils.loadAnimation(this, R.anim.blink);
        imgVw_BatteryStatus = (ImageView) findViewById(R.id.imgVw_BatteryStatus);
        // Initialize a new IntentFilter instance
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        // Register the broadcast receiver
        Context.registerReceiver(mBroadcastReceiver,iFilter);
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String charging_status="",battery_condition="",power_source="Unplugged";
            // Get the battery percentage
            boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
            int  level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            // Get the status of battery Eg. Charging/Discharging
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            if(status == BatteryManager.BATTERY_STATUS_CHARGING)
            {
                charging_status = "Charging";
                imgVw_BatteryStatus.startAnimation(animationBlink);


                //switch ((int) level/10){
                switch (level){
                    case 10:
                        break;
                    case 20:
                        break;
                    case 30:
                        break;
                    case 40:
                        break;
                    case 50:
                        imgVw_BatteryStatus.setImageResource(R.drawable.ic_battery_charging_50_black_24dp);
                        break;
                    case 60:
                        imgVw_BatteryStatus.setImageResource(R.drawable.ic_battery_charging_60_black_24dp);
                        break;
                    case 70:
                        imgVw_BatteryStatus.setImageResource(R.drawable.ic_battery_charging_60_black_24dp);
                        break;
                    case 80:
                        imgVw_BatteryStatus.setImageResource(R.drawable.ic_battery_charging_80_black_24dp);
                        break;
                    case 90:break;
                    case 100:break;
                }
            }


            if (status == BatteryManager.BATTERY_STATUS_FULL || status == BatteryManager.BATTERY_STATUS_UNKNOWN || status == BatteryManager .BATTERY_STATUS_NOT_CHARGING){
                //charging_status = "Charging";
                imgVw_BatteryStatus.setImageResource(R.drawable.ic_battery_alert_black_24dp);
                imgVw_BatteryStatus.startAnimation(animationBlink);
            }






        }};
}
