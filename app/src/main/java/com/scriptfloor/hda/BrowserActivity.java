package com.scriptfloor.hda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class BrowserActivity extends AppCompatActivity {

    WebView webView;
    TextView web_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        webView=findViewById(R.id.web_view);
        web_url=findViewById(R.id.web_url);
        Intent intent = getIntent();
        String link=intent.getStringExtra("link");
        web_url.setText(link);
        webView.loadUrl("www.google.com");
    }
}
