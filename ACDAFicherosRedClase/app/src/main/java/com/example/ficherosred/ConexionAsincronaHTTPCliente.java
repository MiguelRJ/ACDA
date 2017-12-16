package com.example.ficherosred;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.loopj.android.http.TextHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

public class ConexionAsincronaHTTPCliente extends AppCompatActivity implements View.OnClickListener{

    EditText edtUrl;
    Button btnConectar;
    TextView txvTiempo;
    WebView wViewConexionHTTP;
    long inicio, fin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_asincrona_httpcliente);
        iniciar();
    }

    private void iniciar() {
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        btnConectar = (Button) findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(this);
        wViewConexionHTTP = (WebView) findViewById(R.id.wViewConexionHTTP);
        txvTiempo = (TextView) findViewById(R.id.txvTiempo);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    @Override
    public void onClick(View view) {
        if (view == btnConectar) {
            AAHC();
        }
    }

    private void AAHC() {
        final String texto = edtUrl.getText().toString();
        final ProgressDialog progreso = new ProgressDialog(ConexionAsincronaHTTPCliente.this);
        inicio = System.currentTimeMillis();
        RestClient.get(texto, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                //progreso.setCancelable(false);
                progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        RestClient.cancelRequests(getApplicationContext(), true);
                    }
                });
                progreso.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                // called when response HTTP status is "200 OK"
                fin = System.currentTimeMillis();
                progreso.dismiss();
                wViewConexionHTTP.loadDataWithBaseURL(null, response,"text/html", "UTF-8", null);
                txvTiempo.setText("Duracion: " + String.valueOf(fin-inicio) + " milisegundos");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                fin = System.currentTimeMillis();
                progreso.dismiss();
                wViewConexionHTTP.loadDataWithBaseURL(null, response,"text/html", "UTF-8", null);
                txvTiempo.setText("Duracion: " + String.valueOf(fin-inicio) + " milisegundos");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] response, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                fin = System.currentTimeMillis();
                progreso.dismiss();
                wViewConexionHTTP.loadDataWithBaseURL(null, "Error: "+ statusCode + " " + response.toString() + " " + t.getMessage(),"text/html", "UTF-8", null);
                txvTiempo.setText("Duracion: " + String.valueOf(fin-inicio) + " milisegundos");
            }
        });
    }

}
