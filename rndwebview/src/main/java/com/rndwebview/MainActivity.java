package com.rndwebview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {
    TouchyWebView mywebvw;
    private Button button_back,button_next,button_forward;
    private String strCopied="",strOriginalCopied="";

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
        button_forward = (Button) findViewById(R.id.button_forward);
        button_next = (Button) findViewById(R.id.button_next);
        button_back.setOnClickListener(this);
        button_forward.setOnClickListener(this);
        button_next.setOnClickListener(this);
    }

    private void myWebConfig() {
        mywebvw.setGestureDetector(new GestureDetector(new CustomeGestureDetector()));
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
                if (progress >= 100) {

                    //mywebvw.scrollTo(100,100);
                    mywebvw.scrollTo(0, 0);
                }
                activity.setProgress(progress * 1000);

            }
        });
        mywebvw.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                mywebvw.scrollTo(0, 0);
               // Toast.makeText(activity, "Link:" + url, Toast.LENGTH_SHORT).show();
                //mywebvw.scrollTo(300, 300);
            }

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
            case R.id.button_forward:
                mywebvw.goForward();
                break;
            case R.id.button_next:
                strCopied=mywebvw.getUrl();
                strOriginalCopied=mywebvw.getOriginalUrl();
                System.out.println("I Copied url:"+strCopied);
                System.out.println("I Copied url Original:"+strOriginalCopied);


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
                strOriginalCopied=mywebvw.getOriginalUrl();
                System.out.println("I Copied url:"+strCopied);
                System.out.println("I Copied url Original:"+strOriginalCopied);
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
    public class CustomeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1 == null || e2 == null) return false;
            if(e1.getPointerCount() > 1 || e2.getPointerCount() > 1) return false;
            else {
                try { // right to left swipe .. go to next page
                    if(e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 800) {
                        //do your stuff

                        return true;
                    } //left to right swipe .. go to prev page
                    else if (e2.getX() - e1.getX() > 100 && Math.abs(velocityX) > 800) {
                        //do your stuff
                        mywebvw.canGoBack();
                        return true;
                    } //bottom to top, go to next document
                    else if(e1.getY() - e2.getY() > 100 && Math.abs(velocityY) > 800
                            && mywebvw.getScrollY() >= mywebvw.getScale() * (mywebvw.getContentHeight() - mywebvw.getHeight())) {
                        //do your stuff
                        return true;
                    } //top to bottom, go to prev document
                    else if (e2.getY() - e1.getY() > 100 && Math.abs(velocityY) > 800 ) {
                        //do your stuff
                        return true;
                    }
                } catch (Exception e) { // nothing
                }
                return false;
            }
        }
    }

}
