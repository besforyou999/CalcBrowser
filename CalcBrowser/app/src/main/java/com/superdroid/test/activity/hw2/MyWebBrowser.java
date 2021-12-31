package com.superdroid.test.activity.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MyWebBrowser extends AppCompatActivity {

    EditText editText;
    WebView webView;
    Button lastPage, nextPage, executeCalculator;

    WebSettings myWebSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING); //입력기가 레이아웃 뭉개지 않게 하기

        String url = "https://www.naver.com";

        // Intent로 전달된 문자열 받기
        Intent intent = getIntent();

        if (intent.getAction() == Intent.ACTION_SEND) {
            String sharedText = intent.getStringExtra("link");
            url = URLAdapter(sharedText);
        }

        // view settings
        editText = (EditText) findViewById(R.id.edit_text_url);

        // webView settings
        webView = (WebView) findViewById(R.id.webView_display);
        myWebSettings = webView.getSettings();
        myWebSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyBrowser());
        webView.loadUrl(url);

        // Button settings
        lastPage = (Button) findViewById(R.id.last_page_button);
        nextPage = (Button) findViewById(R.id.next_page_button);
        executeCalculator = (Button) findViewById(R.id.next_page_button);

        // edit text key listener 등록 ( 엔터키 눌렀을때 )
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch(keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        String url = editText.getText().toString();
                        url = URLAdapter(url);
                        webView.getSettings().setLoadsImagesAutomatically(true);
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                        webView.loadUrl(url);
                        break;
                }
                return true;
            }
        });

    }

    public String URLAdapter(String input) {
        String temp = input;
        String front3 = input.substring(0,3);
        String adapter = "https://";

        if (front3.equals("www")) {
            adapter += temp;
        }
        else
            adapter = input;
        return adapter;
    }

    public void executeCalculatorOnClick(View v) {
        Intent intent = new Intent(this, MyCalculator.class);
        intent.putExtra("activityName", "MyWebActivity");
        startActivity(intent);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            editText.setText(url);
        }
    }

    public void onClickBackButton(View v) {
        if ( webView.canGoBack()) {
            webView.goBack();
        }
    }

    public void onClickFrontButton(View v) {
        if (webView.canGoForward()) {
            webView.goForward();
        }
    }
}