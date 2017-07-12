package com.demofragswitch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Mohanraj.S,innobot-linux-4 on 27/6/17.
 */

public class f01_dashboard extends Fragment implements  View.OnClickListener {
    private View signup_Frag;
    private View dashboard_Frag;
    private Button button_Dashboard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboard_Frag = inflater.inflate(R.layout.f01_dashboard, container, false);
        return dashboard_Frag;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button_Dashboard=(Button)dashboard_Frag.findViewById(R.id.button_Dashboard);
        button_Dashboard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_Dashboard:
                showOtherFragment();
                break;
            default:
                break;
        }
    }

    public void showOtherFragment()
    {
        f03_bookloading fr=new f03_bookloading();
        FragmentChangeListener fc=(FragmentChangeListener)getActivity();
        fc.replaceFragment(fr);
    }
}
