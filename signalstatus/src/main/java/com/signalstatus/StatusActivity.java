package com.signalstatus;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class StatusActivity extends TabActivity {

    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost host = getTabHost();
        
        TabSpec statusspec = host.newTabSpec("Phone");
        statusspec.setIndicator("Phone",getResources().getDrawable(R.drawable.nphone));
        Intent phoneStatusIntent = new Intent(this, PhoneStatusActivity.class);
        statusspec.setContent(phoneStatusIntent);
        
        TabSpec batteryspec = host.newTabSpec("Battery");
        batteryspec.setIndicator("Battery", getResources().getDrawable(R.drawable.nbattery));
        Intent batteryIntent = new Intent(this, BatteryStatusActivity.class);
        batteryspec.setContent(batteryIntent);
        
        // Adding all TabSpec to TabHost
        host.addTab(statusspec); 
        host.addTab(batteryspec);
        
    }


}
