package com.example.wallk.myapplication;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


public class MainActivity extends AppCompatActivity {
    WebView brow;
    EditText urledit;
    Button go,forward,back,clear,reload;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCenter.start(getApplication(), "27ec27cf-7dec-4dbc-bed5-ff74c39284a4",
                Analytics.class, Crashes.class);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        brow= (WebView)findViewById(R.id.wv_brow);
        urledit = (EditText)findViewById(R.id.et_url);
        //go = (Button)findViewById(R.id.btn_go);
        forward = (Button)findViewById(R.id.btn_fwd);
        back = (Button)findViewById(R.id.btn_bck);
        clear = (Button)findViewById(R.id.btn_clear);
        reload = (Button)findViewById(R.id.btn_reload);
        setupBrow(brow);
        addEvent();
    }
    public void setupBrow(WebView brow)
    {
        brow.setWebViewClient(new ourViewClient());
        brow.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);

                if(newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        //allow java script
        WebSettings websettings = brow.getSettings();
        websettings.setJavaScriptEnabled(true);

        brow.loadUrl("https://google.com.vn/");
    }
    public void addEvent()
    {
        /*go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editextvalue = urledit.getText().toString();

                if(!editextvalue.startsWith("http://"))
                    editextvalue = "http://" + editextvalue;

                String url = editextvalue.replace(" ","");
                brow.loadUrl(url);

                //Hide keyboard after using EditText
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(urledit.getWindowToken(),0);
            }
        });*/

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(brow.canGoForward())
                    brow.goForward();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(brow.canGoBack())
                    brow.goBack();
            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brow.reload();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brow.clearHistory();
            }
        });
        urledit.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    String value = urledit.getText().toString();
                    String fixedURL="";
                    if(!value.startsWith("http://"))
                        fixedURL= "http://" + value;

                    if(Patterns.WEB_URL.matcher(fixedURL).matches())
                        brow.loadUrl(fixedURL);
                    else
                        brow.loadUrl("https://www.google.com/search?q="+value);
                 //Hide keyboard after using EditText
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(urledit.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });
    }
}


