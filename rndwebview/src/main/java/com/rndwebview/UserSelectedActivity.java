package com.rndwebview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


 /*
 * Copyright (c) 2017. Created by Mohanraj.S,Innobot Systems on 21/8/17 for MyDevs
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class UserSelectedActivity extends AppCompatActivity implements View.OnClickListener {
    TouchyWebView mywebvw;
    private Button button_prev,button_use;
    String urlValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userselectedweb);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            urlValue = extras.getString("copiedurl");
        }

        configViews();
    }

    private void configViews() {
        mywebvw = (TouchyWebView) findViewById(R.id.myWebVw);
        myWebConfig();
        button_prev = (Button) findViewById(R.id.button_prev);
        button_use = (Button) findViewById(R.id.button_use);
        button_prev.setOnClickListener(this);
        button_use.setOnClickListener(this);
    }

    private void myWebConfig() {
        mywebvw.loadUrl(urlValue);
        // Enable Javascript
        /*WebSettings webSettings = mywebvw.getSettings();
        webSettings.setJavaScriptEnabled(true);*/
        // Force links and redirects to open in the WebView instead of in a browser
        mywebvw.setWebViewClient(new WebViewClient());
        final Activity activity = this;
        mywebvw.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
            }
        });
        mywebvw.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_prev:
                Intent io = new Intent(UserSelectedActivity.this, MainActivity.class);
                startActivity(io);
                break;
            case R.id.button_use:
                if( android.os.Build.VERSION_CODES.HONEYCOMB<android.os.Build.VERSION.SDK_INT ) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(urlValue);
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("Rndweb", urlValue);
                    clipboard.setPrimaryClip(clip);
                }
                break;
            default:
                break;
        }
    }
}
