package com.dyanmicip;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ShowDialog(View v) {

        Toast.makeText(getApplicationContext(), "Fill Edittext!", Toast.LENGTH_LONG)
                .show();
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.layout_row);
        dialog.setTitle("Title");
        final LinearLayout layoutmain = (LinearLayout) dialog.findViewById(R.id.layoutmain);
        Button btnadd = (Button) dialog.findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                EditText myedittext = new EditText(MainActivity.this);
                layoutmain.addView(myedittext);
            }
        });
        dialog.show();
    }
}
