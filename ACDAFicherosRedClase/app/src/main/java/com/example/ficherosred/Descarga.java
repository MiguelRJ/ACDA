package com.example.ficherosred;

import android.app.Activity;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;

public class Descarga extends AppCompatActivity implements View.OnClickListener {
    EditText edtUrl;
    Button btnImage, btnFile;
    ImageView imgvImage;
    TextView txvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarga);
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        btnImage = (Button) findViewById(R.id.btnImage);
        btnImage.setOnClickListener(this);
        btnFile = (Button) findViewById(R.id.btnFile);
        btnFile.setOnClickListener(this);
        imgvImage = (ImageView) findViewById(R.id.imgvImage);
        txvText = (TextView) findViewById(R.id.txvText);
    }

    @Override
    public void onClick(View v) {
        String url = edtUrl.getText().toString();
        if (v == btnImage) {
            descargaImagen(url);
        }
        if (v==btnFile) {
            descargarFichero(url);
        }
    }

    private void descargarFichero(String url)  {
        // User FileAsyncHyypResponseHandler
        //imgvImage.setImageResource(0);
        //File file = new File(edtUrl.getText().toString());
        //AsyncHttpClient cliente = new AsyncHttpClient();
        //cliente.get(url, new FileAsyncHttpResponseHandler(this) {
        File miFichero = new File (Environment.getExternalStorageDirectory().getAbsolutePath());
        RestClient.get(url, new FileAsyncHttpResponseHandler(miFichero) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(getApplicationContext(), "Fallo: " + statusCode + "\n" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                Toast.makeText(getApplicationContext(), file.getName()+ " \n Guardado en: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void descargaImagen(String url) {
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(imgvImage);
        //utilizar OkHttp3
        OkHttpClient cliente = new OkHttpClient();

        Picasso picasso = new Picasso.Builder(getApplicationContext())
                .downloader(new OkHttp3Downloader(cliente))
                .build();
    }
}
