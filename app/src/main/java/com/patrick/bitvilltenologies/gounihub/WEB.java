package com.patrick.bitvilltenologies.gounihub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;

public class WEB extends AppCompatActivity {

    WebView webView;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        editText=(EditText)findViewById(R.id.weburl);
        webView = (WebView) findViewById(R.id.web);

        Intent intent2 = getIntent();
        String string = intent2.getStringExtra("first_name");


        final ProgressBar pbar = (ProgressBar) findViewById(R.id.progressBar);



        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);

        webView.getSettings().setAppCacheEnabled(true);

        webView.getSettings().setLoadsImagesAutomatically(true);

        webView.loadUrl(string);


        editText.setText(string);









        webView.setWebViewClient(new WebViewClient());

        ////////////////////////////////////////////////////////

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && pbar.getVisibility() == ProgressBar.GONE) {
                    pbar.setVisibility(ProgressBar.VISIBLE);

                }

                pbar.setProgress(progress);
                if (progress == 100) {
                    pbar.setVisibility(ProgressBar.GONE);

                }
            }
        });






    }

    public void OnrecivedError(WebView view , int errorCode,String description, String faillingurl){


    }





    @Override

    public boolean onKeyDown(final int keyCode, final KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
//If there is history, then the canGoBack method will return ‘true’//
            return true;
        }else {
            Intent intent = new Intent(WEB.this,MainActivity.class);
            startActivity(intent);
        }

//If the button that’s been pressed wasn’t the ‘Back’ button, or there’s currently no
//WebView history, then the system should resort to its default behavior and return
//the user to the previous Activity//
        return super.onKeyDown(keyCode, event);
    }
}



