package com.example.ficherosred;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ficherosred.ayudas.Conexion;
import com.example.ficherosred.ayudas.Resultado;
import com.example.ficherosred.ayudas.MySingleton;
import com.example.ficherosred.ayudas.RestClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtUrl;
    RadioButton rbJava, rbAACH, rbVolley;
    Button btnConectar;
    WebView webView;
    TareaAsincrona tareaAsincrona;
    public static final String TAG = "MyTag";
    RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        rbJava = (RadioButton) findViewById(R.id.rbJava);
        rbAACH = (RadioButton) findViewById(R.id.rbAAHC);
        rbVolley = (RadioButton) findViewById(R.id.rbVolley);
        btnConectar = (Button) findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(this);
        webView = (WebView) findViewById(R.id.webview);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        mRequestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
    }

    @Override
    public void onClick(View v) {
        if(v == btnConectar){
            if(isNetworkAvailable()){
                conectar();
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public void conectar(){
        if(rbJava.isChecked()){
            conectarJava();
        }
        if(rbAACH.isChecked()){
            conectarAACH();
        }
        if(rbVolley.isChecked()){
            conectarVolley();
        }
    }

    public void conectarJava(){
        tareaAsincrona = new TareaAsincrona(this);
        tareaAsincrona.execute("Java",edtUrl.getText().toString());
    }

    public void conectarAACH(){
        final String texto = edtUrl.getText().toString();
        final ProgressDialog progreso = new ProgressDialog(WebActivity.this);
        RestClient.get(texto, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        RestClient.cancelRequests(getApplicationContext(), true);
                    }
                });
                progreso.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                progreso.dismiss();
                webView.loadDataWithBaseURL(null, response,"text/html", "UTF-8", null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable t) {
                progreso.dismiss();
                webView.loadDataWithBaseURL(null, response,"text/html", "UTF-8", null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] response, Throwable t) {
                progreso.dismiss();
                webView.loadDataWithBaseURL(null, "Error: "+ statusCode + " " + response.toString() + " " + t.getMessage(),"text/html", "UTF-8", null);
            }
        });
    }

    public void conectarVolley(){
        makeRequest(edtUrl.getText().toString());
    }

    public class TareaAsincrona extends AsyncTask<String, Integer, Resultado> {
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
                resultado = Conexion.conectarJava(cadena[1]);

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
            if (resultado.getCodigo()) {
                Log.e("Resultado", "getcodigo TRUE"+resultado.getCodigo());
                webView.loadDataWithBaseURL(null, resultado.getContenido(), "text/html", "UTF-8", null);
            }else {
                Log.e("Resultado", "getcodigo FALSE"+resultado.getCodigo());
                webView.loadDataWithBaseURL(null, resultado.getMensaje(), "text/html", "UTF-8", null);
            }
            progreso.dismiss();

        }
        protected void onCancelled() {
            progreso.dismiss();
            // mostrar cancelación
        }

    }

    public void makeRequest(String url) {
        final String enlace = url;
        final ProgressDialog progreso = new ProgressDialog(WebActivity.this);
        // Instantiate the RequestQueue.
        //mRequestQueue = Volley.newRequestQueue(this);
        // called before request is started
        progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progreso.setMessage("Conectando . . .");
        progreso.setCancelable(false);
        progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                mRequestQueue.cancelAll(TAG);
            }
        });
        progreso.show();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progreso.dismiss();
                        webView.loadDataWithBaseURL(enlace, response,"text/html", "UTF-8", null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progreso.dismiss();
                        String mensaje = "Error";
                        if (error instanceof TimeoutError || error instanceof NoConnectionError)
                            mensaje = "Timeout Error: " + error.getMessage();
                        else {
                            NetworkResponse errorResponse = error.networkResponse;
                            if (errorResponse != null && errorResponse.data != null)
                                try {
                                    mensaje = "Error: " + errorResponse.statusCode + " " + "\n" + new
                                            String(errorResponse.data, "UTF-8");
                                    Log.e("Error", mensaje);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                        }
                        webView.loadDataWithBaseURL(null, mensaje,"text/html", "UTF-8", null);
                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                    }
                });
        stringRequest.setTag(TAG);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 1, 1));
        mRequestQueue.add(stringRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }
}
