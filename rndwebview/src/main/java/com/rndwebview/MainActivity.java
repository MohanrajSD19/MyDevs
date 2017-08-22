package com.rndwebview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {
    TouchyWebView mywebvw;
    private Button button_back,button_next;
    private String strCopied="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configViews();
    }

    private void configViews() {
        mywebvw = (TouchyWebView) findViewById(R.id.myWebVw);
        myWebConfig();
        button_back = (Button) findViewById(R.id.button_back);
        button_next = (Button) findViewById(R.id.button_next);
        button_back.setOnClickListener(this);
        button_next.setOnClickListener(this);
    }

    private void myWebConfig() {
        mywebvw.setLongClickable(true);
        mywebvw.setOnLongClickListener(this);
        /*mywebvw.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getBaseContext(), "Long Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });*/
        mywebvw.loadUrl("http://www.google.com/");
        // Enable Javascript
        WebSettings webSettings = mywebvw.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Force links and redirects to open in the WebView instead of in a browser
        //mywebvw.setWebViewClient(new WebViewClient());
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
            case R.id.button_back:
                mywebvw.goBack();
                break;
            case R.id.button_next:
                strCopied=mywebvw.getUrl();
                System.out.println("I Copied url:"+strCopied);
                Toast.makeText(getBaseContext(), "Long Clicked", Toast.LENGTH_SHORT).show();

                if( android.os.Build.VERSION_CODES.HONEYCOMB<android.os.Build.VERSION.SDK_INT ) {

                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(strCopied);
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("Rndweb", strCopied);
                    clipboard.setPrimaryClip(clip);
                }
                if(strCopied.length()>0) {
                    Intent io = new Intent(MainActivity.this, UserSelectedActivity.class);
                    io.putExtra("copiedurl", strCopied);
                    startActivity(io);
                }else{
                    Toast.makeText(MainActivity.this, "Please Copy URL", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch(view.getId()){
            case R.id.myWebVw:
                strCopied=mywebvw.getUrl();
                System.out.println("I Copied url:"+strCopied);
                Toast.makeText(getBaseContext(), "Long Clicked", Toast.LENGTH_SHORT).show();
                if( android.os.Build.VERSION_CODES.HONEYCOMB<android.os.Build.VERSION.SDK_INT ) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(strCopied);
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("text label", strCopied);
                    clipboard.setPrimaryClip(clip);
                }
                break;
            default:
                break;

        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
