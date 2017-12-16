package com.example.clasewebview.clasewebview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class Main extends AppCompatActivity {

    private EditText edtName;
    private EditText edtWeb;
    private Button btnName;
    private Button btnWeb;
    private Button btnWebView;
    private String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtName=(EditText)findViewById(R.id.edtName);
        edtWeb=(EditText)findViewById(R.id.edtWeb);
        btnName=(Button)findViewById(R.id.btnName);
        btnWeb=(Button)findViewById(R.id.btnWeb);
        btnWebView=(Button)findViewById(R.id.btnWebView);

        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarStringNombre();
            }
        });
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobarURL(edtWeb.getText().toString())) {
                    abrirWeb(edtWeb.getText().toString());
                }
            }
        });
        btnWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobarURL(edtWeb.getText().toString())) {
                    abrirWebView(edtWeb.getText().toString());
                }
            }
        });
    }

    void pasarStringNombre(){
        str = edtName.getText().toString() + edtWeb.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("message",str);
        Intent intent = new Intent(Main.this, Name.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    boolean comprobarURL(String url){
        if(url.substring(0,7).equals("http://") || url.substring(0,8).equals("https://")) {
            return true;
        }else{
            Toast.makeText(this,"Error en el protocolo", Toast.LENGTH_SHORT).show();
        }
        return false;

        /*try {
            URL address = new URL(url);
            return true;
        }catch(MalformedURLException e) {
            Log.e("Error","MalformedURLException");
        }
        return false;*/

        /*if(URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url)){
            return true;
        }else{
            return false;
        }*/
    }

    void abrirWeb(String url){

            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

    }

    void abrirWebView(String url){
        str = edtWeb.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("message",str);
        Intent intent = new Intent(Main.this, Segunda.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
