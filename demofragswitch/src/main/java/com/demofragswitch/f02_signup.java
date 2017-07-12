package com.demofragswitch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohanraj.S,innobot-linux-4 on 27/6/17.
 */

public class f02_signup extends Fragment implements  View.OnClickListener{

    private View signup_Frag;
    private Button button_SignUp;
    private RadioGroup radioGp_kf06_resident_away;
    List<RadioButton> radioButtons = new ArrayList<RadioButton>();

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
        

        radioGp_kf06_resident_away = (RadioGroup) signup_Frag.findViewById(R.id.radioGp_kf06_resident_away);
        radioButtons.add( (RadioButton)signup_Frag.findViewById(R.id.radio_kf06_1_2_hours) );
        radioButtons.add( (RadioButton)signup_Frag.findViewById(R.id.radio_kf06_halfday) );
        radioButtons.add( (RadioButton)signup_Frag.findViewById(R.id.radio_kf06_allday) );
        radioButtons.add( (RadioButton)signup_Frag.findViewById(R.id.radio_kf06_moreday) );

        //configClickListeners();
        radioButtonAction();

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

    private void radioButtonAction(){
        for (RadioButton button : radioButtons){
            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) processRadioButtonClick(buttonView);
                    String radio_Text = buttonView.getText().toString();
                    int radio_Id = buttonView.getId();
                    System.out.println("Selected the Radio:"+radio_Text+", Radio-Id:"+radio_Id);
                }
            });

        }
    }

    private void processRadioButtonClick(CompoundButton buttonView){
        for (RadioButton button : radioButtons){
            if (button != buttonView ) button.setChecked(false);
        }

    }

    public void showOtherFragment()
    {
        f01_dashboard fr=new f01_dashboard();
        FragmentChangeListener fc=(FragmentChangeListener)getActivity();
        fc.replaceFragment(fr);
    }
}
