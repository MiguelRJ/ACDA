package com.example.clasewebview.clasewebview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class Segunda extends AppCompatActivity {

    private WebView nav;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);
        nav=(WebView)findViewById(R.id.nav);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        url=bundle.getString("message");
        abrir(url);
    }

    void abrir(String url){
        nav.loadUrl(url);
    }
}