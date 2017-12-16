package com.example.ficherosred;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ficherosred.ayudas.OperacionesFichero;
import com.example.ficherosred.ayudas.RestClient;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;

public class ImagenActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUrl;
    private ImageView imgImagen;
    private Button btnCargar, btnAnterior, btnSiguiente;
    String[] enlaces;
    OperacionesFichero opFi;
    int posicionLectura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen);
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        imgImagen = (ImageView) findViewById(R.id.imgImagen);
        btnCargar = (Button) findViewById(R.id.btnCargar);
        btnCargar.setOnClickListener(this);
        btnAnterior = (Button) findViewById(R.id.btnAnterior);
        btnAnterior.setOnClickListener(this);
        btnAnterior.setEnabled(false);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnSiguiente.setOnClickListener(this);
        btnSiguiente.setEnabled(false);
        enlaces = null;
        opFi = new OperacionesFichero();
        posicionLectura = 0;
    }

    @Override
    public void onClick(View v) {
        if (v==btnCargar) {
            descargarFichero(edtUrl.getText().toString());
            btnAnterior.setEnabled(true);
            btnSiguiente.setEnabled(true);
        }
        if (v==btnAnterior){
            posicionLectura = (posicionLectura - 1 + enlaces.length) % enlaces.length;
            descargaImagen(enlaces[posicionLectura]);

        }
        if (v==btnSiguiente){
            posicionLectura = (posicionLectura + 1) % enlaces.length;
            descargaImagen(enlaces[(posicionLectura)]);
        }
    }

    void next() {
        posicionLectura = (posicionLectura + 1) % enlaces.length;
        //document.getElementById("image").src = images[nextImage];
    }

    void prev() {
        posicionLectura = (posicionLectura - 1 + enlaces.length) % enlaces.length;
        //document.getElementById("image").src = images[nextImage];
    }

    public void leerFichero(File file){
        Toast.makeText(getApplicationContext(), file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        int numUrls = opFi.numeroLineasFichero(file.getAbsolutePath());
        enlaces = new String[numUrls];
        int i = 0;
        BufferedReader br = null;

        try {
            StringBuffer output = new StringBuffer();

            br = new BufferedReader(new FileReader(file.getAbsolutePath()));
            String line = "";
            while ((line = br.readLine()) != null) {
                //Toast.makeText(getApplicationContext(), i+" "+line, Toast.LENGTH_SHORT).show();
                enlaces[i++] = line;
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void descargarFichero(String url)  {
        File fileEnlaces = new File (Environment.getExternalStorageDirectory().getAbsolutePath());
        RestClient.get(url, new FileAsyncHttpResponseHandler(fileEnlaces) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(getApplicationContext(), "Fallo: " + statusCode + "\n" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                leerFichero(file);
                Toast.makeText(getApplicationContext(), file.getName()+ " \n Guardado en: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void descargaImagen(String url) {
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(imgImagen);
        OkHttpClient cliente = new OkHttpClient();
        Picasso picasso = new Picasso.Builder(getApplicationContext())
                .downloader(new OkHttp3Downloader(cliente))
                .build();
    }


}
