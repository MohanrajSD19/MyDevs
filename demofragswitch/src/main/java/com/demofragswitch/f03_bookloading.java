package com.demofragswitch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import bookloading.book.BookLoading;

/**
 * Created by Mohanraj.S on 10/7/17 for MyDevs.
 */

public class f03_bookloading extends Fragment {

    private View book_Frag;
    private Button button_act;
   // private BookLoading bookLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        book_Frag = inflater.inflate(R.layout.f03_bookloading, container, false);

        return book_Frag;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EditText text = (EditText) book_Frag.findViewById(R.id.edTxt_myphoneno);
        PhoneNumberUtils.formatNumber(text.getText().toString());
    }


}
