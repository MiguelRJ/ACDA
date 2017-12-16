package com.example.ficherosred;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ConexionAsincrona extends AppCompatActivity implements View.OnClickListener{
    EditText edtUrl;
    RadioButton rdJava, rdApache;
    Button btnConectar;
    TextView txvTiempo;
    WebView wViewConexionHTTP;
    public static final String JAVA = "Java";
    public static final String APACHE = "Apache";
    TareaAsincrona tareaAsincrona;
    long inicio, fin;

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
        String tipo = APACHE;

        if (v == btnConectar) {
            inicio = System.currentTimeMillis();
            if (rdJava.isChecked())
                tipo = JAVA;
            tareaAsincrona = new TareaAsincrona(this)    ;
            tareaAsincrona.execute(tipo, texto);
            txvTiempo.setText("Descargando la pagina");
        }
    }

    public class TareaAsincrona extends AsyncTask<String, Integer, Resultado > {
        private ProgressDialog progreso;
        private Context context;

        public TareaAsincrona(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            progreso = new ProgressDialog(context);
            progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progreso.setMessage("Conectando . . .");
            progreso.setCancelable(true);
            progreso.setOnCancelListener(new DialogInterface.OnCancelListener(){
                public void onCancel(DialogInterface dialog){
                    TareaAsincrona.this.cancel(true);
                }
            });
            progreso.show();
        }

        @Override
        protected Resultado doInBackground(String... cadena) {
            Resultado resultado;
            int i = 1;

            try {
                publishProgress(i++);
                if (rdJava.isChecked()) {
                    resultado = Conexion.conectarJava(cadena[1]);
                    //txvTiempo.setText("Usando " + JAVA);
                }
                else {
                    resultado = Conexion.conectarApache(cadena[1]);
                    //txvTiempo.setText("Usando " + APACHE);
                }
                //txvTiempo.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");
                return resultado;
            } catch (Exception e) {
                Log.e("HTTP", e.getMessage(), e);
                resultado = null;
                cancel(true);
            }
            return resultado;
        }

        protected void onProgressUpdate(Integer... progress) {
            progreso.setMessage("Conectando " + Integer.toString(progress[0]));
        }
        protected void onPostExecute(Resultado resultado) {
            fin = System.currentTimeMillis();
            if (resultado.getCodigo())
                wViewConexionHTTP.loadDataWithBaseURL(null, resultado.getContenido(),"text/html", "UTF-8", null);
            else
                wViewConexionHTTP.loadDataWithBaseURL(null, resultado.getMensaje(),"text/html", "UTF-8", null);
            txvTiempo.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");
            // mostrar el resultado
            progreso.dismiss();

        }
        protected void onCancelled() {
            progreso.dismiss();
            // mostrar cancelación
        }

    }

}