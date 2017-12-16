package com.example.ficheros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btnMemoriaInterna, btnMemoriaExterna, btnLeerFichero, btnCodificacion, btnExploradorArchivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMemoriaInterna=(Button)findViewById(R.id.btnMemoriaInterna);
        btnMemoriaInterna.setOnClickListener(this);
        btnMemoriaExterna=(Button)findViewById(R.id.btnMemoriaExterna);
        btnMemoriaExterna.setOnClickListener(this);
        btnLeerFichero=(Button)findViewById(R.id.btnLeerFichero);
        btnLeerFichero.setOnClickListener(this);
        btnCodificacion=(Button)findViewById(R.id.btnCodificacion);
        btnCodificacion.setOnClickListener(this);
        btnExploradorArchivos=(Button)findViewById(R.id.btnExploradorArchivos);
        btnExploradorArchivos.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnMemoriaInterna){
            Intent intent = new Intent(this,EscribirMemoriaInternaActivity.class);
            startActivity(intent);
        }
        if(view == btnMemoriaExterna){
            Intent intent = new Intent(this,EscribirMemoriaExternaActivity.class);
            startActivity(intent);
        }
        if(view == btnLeerFichero){
            Intent intent = new Intent(this,LeerFicheroActivity.class);
            startActivity(intent);
        }
        if(view == btnCodificacion){
            Intent intent = new Intent(this,Codificacionactivity.class);
            startActivity(intent);
        }
        if(view == btnExploradorArchivos){
            Intent intent = new Intent(this,ExploradorArchivosActivity.class);
            startActivity(intent);
        }
    }
}
