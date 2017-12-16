package com.example.tema1ej.tema1ej;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewSegundaActivity extends AppCompatActivity {

    private WebView WebViewNavegador;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_segunda);
        WebViewNavegador=(WebView)findViewById(R.id.WebViewAWebSegunda);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        url=bundle.getString("url");
        abrirNavegador(url);
    }

    private void abrirNavegador(String url){
        WebViewNavegador.loadUrl(url);
    }
}
