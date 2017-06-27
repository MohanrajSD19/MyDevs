package com.demofragswitch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Mohanraj.S,innobot-linux-4 on 27/6/17.
 */

public class f02_signup extends Fragment implements  View.OnClickListener{

    private View signup_Frag;
    private Button button_SignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        signup_Frag = inflater.inflate(R.layout.f02_signup, container, false);

        return signup_Frag;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button_SignUp=(Button)signup_Frag.findViewById(R.id.button_SignUp);
        button_SignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_SignUp:
                showOtherFragment();
                break;
            default:
                break;
        }
    }

    public void showOtherFragment()
    {
        f01_dashboard fr=new f01_dashboard();
        FragmentChangeListener fc=(FragmentChangeListener)getActivity();
        fc.replaceFragment(fr);
    }
}
