package com.example.tema1ej.tema1ej;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class WebViewPrimeraActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtURLAWeb;
    private Button btnIrAWeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_primera);
        edtURLAWeb=(EditText)findViewById(R.id.edtURLAWeb);
        btnIrAWeb=(Button)findViewById(R.id.btnIrAWeb);
        btnIrAWeb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==btnIrAWeb){
            abrirWebView(edtURLAWeb.getText().toString());
        }
    }

    void abrirWebView(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        Intent intent = new Intent(this, WebViewSegundaActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
