package com.batterylogs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import static com.batterylogs.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;
import com.batterylogs.NetworkStateChangeReceiver;

/**
 * Created by Mohanraj.S on 25/7/17 for MyDevs.
 */

public class Activity_BatteryLog extends AppCompatActivity {

    private static final String TAG = "Activity_BatteryLog";
    private BroadcastReceiver batteryInfoReceiver;
    private ImageView imgVw,imgVw_Settings;
    Animation animationBlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgVw =(ImageView)findViewById(R.id.imgVw_BatteryStatus);
        imgVw_Settings =(ImageView)findViewById(R.id.imgVw_Settings);
        animationBlink = AnimationUtils.loadAnimation(this, R.anim.blink);
        batteryInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive() called with intent " + intent);
                updateBatteryStatus(intent);
                try {

                    //boolean isVisible = MyApplication.isActivityVisible();

                    //Log.i("Activity is Visible ", "Is activity visible : " + isVisible);

                    // If it is visible then trigger the task else do nothing
                    //if (isVisible) {
                        ConnectivityManager connectivityManager = (ConnectivityManager) context
                                .getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connectivityManager
                                .getActiveNetworkInfo();

                        // Check internet connection and accrding to state change the
                        // text of activity by calling method
                        if (networkInfo != null && networkInfo.isConnected()) {
                            changeTextStatus(true);
                        } else {
                            changeTextStatus(false);
                        }
                    //}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        };

        // At activity startup we manually check the internet status and change
        // the text status
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            changeTextStatus(true);
        } else {
            changeTextStatus(false);
        }


    }


    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        registerReceiver(batteryInfoReceiver, intentFilter);

        MyApplication.activityResumed();// On Resume notify the Application
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (batteryInfoReceiver != null) {
            unregisterReceiver(batteryInfoReceiver);
        }
        MyApplication.activityPaused();// On Pause notify the Application
    }


    // Method to change the text status
    public void changeTextStatus(boolean isConnected) {

        // Change status according to boolean value
        if (isConnected) {
            System.out.println("Net Connected");
            //internetStatus.setText("Internet Connected.");
            //imgVw_Settings.setBackgroundColor(Color.parseColor("#00ff00"));
            imgVw_Settings.setImageResource(R.drawable.ic_signal_wifi_4_bar_black_24dp);
        } else {
            System.out.println("Net DisConnected");
            //internetStatus.setText("Internet Disconnected.");
            //imgVw_Settings.setBackgroundColor(Color.parseColor("#ff0000"));
            imgVw_Settings.setImageResource(R.drawable.ic_signal_wifi_off_black_24dp);
        }
    }


    private void updateBatteryStatus(Intent intent) {
        boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);

        if (present) {
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            switch (health) {
                case BatteryManager.BATTERY_HEALTH_COLD:
                    //cd.setStatus("Cold");
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    //cd.setStatus("Dead");
                    break;
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    //cd.setStatus("Good");
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    //cd.setStatus("Over Voltage");
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    //cd.setStatus("Over Heat");
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    //cd.setStatus("Unspecified Failure");
                    break;
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                default:
                    //cd.setStatus("Unkown");
                    break;
            }

            // Calculate Battery Pourcentage ...
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            if (level != -1 && scale != -1) {
                int batteryPct = (int) ((level / (float) scale) * 100f);
                //cd = new CardData();
                //cd.setFeature("Battery Percentage");
                //cd.setStatus(batteryPct + " %");
                //cardData.add(cd);
            }

            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);

            //cd = new CardData();
            //cd.setFeature("Plugged Status");

            switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    // cd.setStatus("Wireless");
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    // cd.setStatus("USB");
                    break;
                case BatteryManager.BATTERY_PLUGGED_AC:
                    //cd.setStatus("AC");
                    break;
                default:
                    //cd.setStatus("Default (None)");
                    break;
            }

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            //cd = new CardData();
            //cd.setFeature("Status");

            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    //cd.setStatus("Charging");
                    imgVw.startAnimation(animationBlink);
                    setBatteryStatus(level);
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    //cd.setStatus("Discharging");
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    //cd.setStatus("Full");
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    //cd.setStatus("Unknown");
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    break;
                default:
                    //cd.setStatus("Not Charging");
                    break;
            }

            if (intent.getExtras() != null) {
                String technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);

                if (!TextUtils.isEmpty(technology)) {
                    //cd = new CardData();
                    //cd.setFeature("Technology");
                    //cd.setStatus(technology);
                    //cardData.add(cd);
                }
            }

            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);

            if (temperature > 0) {
                float temp = ((float) temperature) / 10f;
                //cd = new CardData();
                // cd.setFeature("Temperature");
                //cd.setStatus(temp + "°C");
                //cardData.add(cd);
            }

            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

            if (voltage > 0) {
                //cd = new CardData();
                //cd.setFeature("Voltage");
                //cd.setStatus(voltage + "mV");
                // cardData.add(cd);
            }

            long capacity = getBatteryCapacity(this);

            if (capacity > 0) {
                //cd = new CardData();
                //cd.setFeature("Capacity");
                //cd.setStatus(capacity + "mAh");
                //cardData.add(cd);
            }

        }else{
            Toast.makeText(this, "No Battery present", Toast.LENGTH_SHORT).show();
        }
    }



    public long getBatteryCapacity(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BatteryManager mBatteryManager = (BatteryManager) ctx.getSystemService(Context.BATTERY_SERVICE);
            Long chargeCounter = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
            Long capacity = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

            if (chargeCounter != null && capacity != null) {
                long value = (long) (((float) chargeCounter / (float) capacity) * 100f);
                return value;
            }
        }

        return 0;
    }


    private void setBatteryStatus(int level){
        switch (level/10) {
            case 0:
                System.out.println("MY Log->"+"0-9="+level);
                imgVw.setImageResource(R.drawable.ic_battery_alert_black_24dp);
                break;
            case 1:
                System.out.println("MY Log->"+"10-19="+level);
                imgVw.setImageResource(R.drawable.ic_battery_charging_20_black_24dp);
                break;
            case 2:
                System.out.println("MY Log->"+"20-29="+level);
                imgVw.setImageResource(R.drawable.ic_battery_charging_20_black_24dp);
                break;
            case 3:
                System.out.println("MY Log->"+"30-39="+level);
                imgVw.setImageResource(R.drawable.ic_battery_charging_30_black_24dp);
                break;
            case 4:
                System.out.println("MY Log->"+"40-49="+level);
                imgVw.setImageResource(R.drawable.ic_battery_charging_30_black_24dp);
                break;
            case 5:
                System.out.println("MY Log->"+"50-59="+level);
                imgVw.setImageResource(R.drawable.ic_battery_charging_50_black_24dp);
                break;
            case 6:
                System.out.println("MY Log->"+"60-69="+level);
                imgVw.setImageResource(R.drawable.ic_battery_charging_60_black_24dp);
                break;
            case 7:
                System.out.println("MY Log->"+"70-79="+level);
                break;
            case 8:
                System.out.println("MY Log->"+"80-89="+level);
                imgVw.setImageResource(R.drawable.ic_battery_charging_80_black_24dp);
                break;
            case 9:
                System.out.println("MY Log->"+"90-99="+level);
                imgVw.setImageResource(R.drawable.ic_battery_charging_90_black_24dp);
                break;


            default:
                break;
        }
    }
}
