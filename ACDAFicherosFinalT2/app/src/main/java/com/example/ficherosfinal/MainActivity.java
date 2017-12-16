package com.example.ficherosfinal;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtImagen, edtFrase;
    private ImageView imgImagen;
    private TextView txvTexto;
    private Button btnDescargar;
    String[] enlacesImg;
    String[] enlacesFrases;
    int tiempo;
    OperacionesFichero opFi;
    String errores = "http://alumno.mobi/~alumno/superior/rodriguez/uploads/errores.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtImagen = (EditText) findViewById(R.id.edtImagen);
        edtFrase = (EditText) findViewById(R.id.edtFrase);
        imgImagen = (ImageView) findViewById(R.id.imgImagen);
        txvTexto = (TextView) findViewById(R.id.txvTexto);
        btnDescargar = (Button) findViewById(R.id.btnDescargar);
        btnDescargar.setOnClickListener(this);
        opFi = new OperacionesFichero();
        leerTiempo();
    }

    @Override
    public void onClick(View v) {
        if (v==btnDescargar) {
            enlacesFrases = null;
            enlacesImg = null;
            descargarFichero(edtImagen.getText().toString(), edtFrase.getText().toString());
            descargaImagenes();
            descargaFrases();
        }
    }

    private void descargarFichero(final String urlImg, final String urlFrases)  {
        RestClient.get(urlImg, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, File file) {
                Toast.makeText(getApplicationContext(), "Fallo: " + statusCode + "\n" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                enlacesImg = opFi.leerURL(urlImg);
                Toast.makeText(getApplicationContext(), file.getName()+ " \n Guardado en: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }
        });
        RestClient.get(urlFrases, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, File file) {
                Toast.makeText(getApplicationContext(), "Fallo: " + statusCode + "\n" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                enlacesFrases = opFi.leerURL(urlFrases);
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
    }

    private void descargaImagenes() {
        final int[] i = {0};
        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (enlacesImg != null) {
                    descargaImagen(enlacesImg[i[0]++ % enlacesImg.length]);
                }
                ha.postDelayed(this, tiempo);
            }
        }, tiempo);
    }

    private void descargaFrase(String frase) {
        txvTexto.setText(frase);
    }

    private void descargaFrases() {
        final int[] i = {0};
        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (enlacesFrases!= null) {
                    descargaFrase(enlacesFrases[i[0]++ % enlacesFrases.length]);
                }
                ha.postDelayed(this, tiempo);
            }
        }, tiempo);
    }

    void leerTiempo(){
        InputStream inputStream = getResources().openRawResource(R.raw.intervalo);
        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        try {
            while (( line = buffreader.readLine()) != null) {
                tiempo = Integer.parseInt(line)*1000;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

}