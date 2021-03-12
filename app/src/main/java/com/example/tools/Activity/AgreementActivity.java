package com.example.tools.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.tools.R;

public class AgreementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_agreement);
        WebView webView=findViewById(R.id.webview);
        webView.loadUrl(" file:///android_asset/ITnews.html ");
    }
}