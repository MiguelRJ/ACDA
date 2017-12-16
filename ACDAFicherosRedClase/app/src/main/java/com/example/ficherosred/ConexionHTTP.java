package com.example.ficherosred;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ConexionHTTP extends AppCompatActivity implements View.OnClickListener{

    EditText edtUrl;
    RadioButton rdJava, rdApache;
    Button btnConectar;
    TextView txvTiempo;
    WebView wViewConexionHTTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_http);
        iniciar();
    }

    private void iniciar() {
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        rdJava = (RadioButton) findViewById(R.id.rdJava);
        rdApache = (RadioButton) findViewById(R.id.rdApache);
        btnConectar = (Button) findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(this);
        wViewConexionHTTP = (WebView) findViewById(R.id.wViewConexionHTTP);
        txvTiempo = (TextView) findViewById(R.id.txvTiempo);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    @Override
    public void onClick(View v) {
        String texto = edtUrl.getText().toString();
        long inicio, fin;
        Resultado resultado;
        if (v == btnConectar) {
            inicio = System.currentTimeMillis();
            if (rdJava.isChecked())
                resultado = Conexion.conectarJava(texto);
            else
                resultado = Conexion.conectarApache(texto);
            fin = System.currentTimeMillis();

            if (resultado.getCodigo())
                wViewConexionHTTP.loadDataWithBaseURL(null, resultado.getContenido(),"text/html", "UTF-8", null);
            else
                wViewConexionHTTP.loadDataWithBaseURL(null, resultado.getMensaje(),"text/html", "UTF-8", null);
            txvTiempo.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");
        }
    }
}
