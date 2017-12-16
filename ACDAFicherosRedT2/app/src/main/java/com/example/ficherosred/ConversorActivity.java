package com.example.ficherosred;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ficherosred.ayudas.OperacionesFichero;
import com.example.ficherosred.ayudas.RestClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;

import cz.msebera.android.httpclient.Header;

public class ConversorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txvCambioDolarEuro, txvCambioEuroDolar;
    private EditText edtDolar, edtEuro;
    private RadioButton rbDolarAEuro, rbEuroADolar;
    private Button btnCalcular;
    double cambio;
    private final String url = "https://alumno.mobi/~alumno/superior/rodriguez/uploads/cambio.txt";
    private OperacionesFichero opFi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversor);
        opFi = new OperacionesFichero();
        txvCambioDolarEuro = (TextView) findViewById(R.id.txvCambioDolarEruo);
        txvCambioEuroDolar = (TextView) findViewById(R.id.txvCambioEuroDolar);
        edtDolar = (EditText) findViewById(R.id.edtDolar);
        edtEuro = (EditText) findViewById(R.id.edtEuro);
        rbDolarAEuro = (RadioButton) findViewById(R.id.rbDolarAEuro);
        rbEuroADolar = (RadioButton) findViewById(R.id.rbEuroADolar);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);
        btnCalcular.setOnClickListener(this);
        descargarFichero(url);
    }

    @Override
    public void onClick(View v) {
        if (v==btnCalcular){
            if (rbDolarAEuro.isChecked()){
                try {
                    edtEuro.setText(convertirAEuros(obtenerValor(edtDolar)));
                }
                catch (Exception e){
                    Toast.makeText(this, "Erros al introducir $", Toast.LENGTH_SHORT).show();
                }
            }
            if (rbEuroADolar.isChecked()){
                try {
                    edtDolar.setText(convertirADolares(obtenerValor(edtEuro)));
                }
                catch (Exception e){
                    Toast.makeText(this,"Error al introducir €",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void descargarFichero(String url)  {
        File miFichero = new File (Environment.getExternalStorageDirectory().getAbsolutePath());
        RestClient.get(url, new FileAsyncHttpResponseHandler(miFichero) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(getApplicationContext(), "No se puede obtener el fichero de cambio: " + statusCode + "\n" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                Toast.makeText(getApplicationContext(), file.getName()+ " \n Guardado en: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                cambio = Double.parseDouble(opFi.leerFicheroCompleto(file.getAbsolutePath()).toString().replace(",","."));
                txvCambioDolarEuro.setText(String.valueOf(cambio).substring(0,4));
                txvCambioEuroDolar.setText(String.valueOf(1/cambio).substring(0,4));
            }
        });
    }

    public String obtenerValor(EditText edt) {
        return edt.getText().toString();
    }

    public String convertirADolares(String cantidad) {
        double valor = Double.parseDouble(cantidad) / cambio;
        return Double.toString(valor).substring(0,4);
    }

    public String convertirAEuros(String cantidad) {
        double valor = Double.parseDouble(cantidad) * cambio;
        return Double.toString(valor).substring(0,4);
    }


}
