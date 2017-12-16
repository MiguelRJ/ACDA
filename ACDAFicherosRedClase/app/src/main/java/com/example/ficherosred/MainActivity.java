package com.example.ficherosred;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnHTTP, btnAsincrona, btnAsincronaHTTPClient, btnVolley, btnDescargar, btnSubir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHTTP=(Button)findViewById(R.id.btnHTTP);
        btnHTTP.setOnClickListener(this);

        btnAsincrona=(Button)findViewById(R.id.btnAsincrona);
        btnAsincrona.setOnClickListener(this);

        btnAsincronaHTTPClient=(Button)findViewById(R.id.btnAsincronaHTTPClient);
        btnAsincronaHTTPClient.setOnClickListener(this);

        btnVolley=(Button)findViewById(R.id.btnVolley);
        btnVolley.setOnClickListener(this);

        btnDescargar=(Button)findViewById(R.id.btnDescargar);
        btnDescargar.setOnClickListener(this);

        btnSubir=(Button)findViewById(R.id.btnSubir);
        btnSubir.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view == btnHTTP){
            Intent intent = new Intent(this,ConexionHTTP.class);
            startActivity(intent);
        }

        if(view == btnAsincrona){
            Intent intent = new Intent(this,ConexionAsincrona.class);
            startActivity(intent);
        }

        if(view == btnAsincronaHTTPClient){
            Intent intent = new Intent(this,ConexionAsincronaHTTPCliente.class);
            startActivity(intent);
        }

        if(view == btnVolley){
            Intent intent = new Intent(this,ConexionVolley.class);
            startActivity(intent);
        }

        if(view == btnDescargar){
            Intent intent = new Intent(this,Descarga.class);
            startActivity(intent);
        }

        if(view == btnSubir){
            Intent intent = new Intent(this,Subida.class);
            startActivity(intent);
        }

    }
}
