package com.example.ficherosred;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAgenda, btnAlarma, btnCalendario, btnWeb, btnImagen, btnConversor, btnSubida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAgenda = (Button) findViewById(R.id.btnAgenda);
        btnAgenda.setOnClickListener(this);
        btnAlarma = (Button) findViewById(R.id.btnAlarma);
        btnAlarma.setOnClickListener(this);
        btnCalendario = (Button) findViewById(R.id.btnCalendario);
        btnCalendario.setOnClickListener(this);
        btnWeb = (Button) findViewById(R.id.btnWeb);
        btnWeb.setOnClickListener(this);
        btnImagen = (Button) findViewById(R.id.btnImagen);
        btnImagen.setOnClickListener(this);
        btnConversor = (Button) findViewById(R.id.btnConversor);
        btnConversor.setOnClickListener(this);
        btnSubida = (Button) findViewById(R.id.btnSubida);
        btnSubida.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnAgenda){
            startActivity( new Intent(this,AgendaActivity.class));
        }
        if (view == btnAlarma){
            startActivity( new Intent(this,AlarmaActivity.class));
        }
        if (view == btnCalendario){
            startActivity( new Intent(this,CalendarioActivity.class));
        }
        if (view == btnWeb){
            startActivity( new Intent(this,WebActivity.class));
        }
        if (view == btnImagen){
            startActivity( new Intent(this,ImagenActivity.class));
        }
        if (view == btnConversor){
            startActivity( new Intent(this,ConversorActivity.class));
        }
        if (view == btnSubida){
            startActivity( new Intent(this,SubidaActivity.class));
        }
    }
}
