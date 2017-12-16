package com.example.ficheros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LeerFicheroActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtResRaw, edtAssets, edtMemoriaInterna, edtMemoriaExterna;
    TextView txvResultado;
    Button btnSuma;
    public static final String NUMERO = "numero";
    public static final String VALOR = "valor.txt";
    public static final String DATOINTERNO = "datointerno.txt";
    public static final String DATOEXTERNO = "datoexterno.txt";
    public static final String OPERACIONES = "operaciones.txt";
    public static final String CODIFICACION = "UTF-8";
    Resultado miResultado;
    Memoria miMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_fichero);
        iniciar();
    }

    private void iniciar() {
        edtResRaw = (EditText) findViewById(R.id.edtResRaw);
        edtAssets = (EditText) findViewById(R.id.edtAssets);
        edtMemoriaInterna = (EditText) findViewById(R.id.edtMemoriaInterna);
        edtMemoriaExterna = (EditText) findViewById(R.id.edtMemoriaExterna);
        btnSuma = (Button) findViewById(R.id.btnSuma);
        txvResultado = (TextView) findViewById(R.id.txvResultado);
        miMemoria = new Memoria(getApplicationContext());
        btnSuma.setOnClickListener(this);

        miResultado = miMemoria.leerRaw(NUMERO);
        if (miResultado.getCodigo()) {
            edtResRaw.setText(miResultado.getContenido());
        } else {
            edtResRaw.setText("0");
            Toast.makeText(this, "Error al leer " + NUMERO + " " + miResultado.getMensaje(), Toast.LENGTH_SHORT).show();
        }

        miResultado = miMemoria.leerAsset(VALOR);
        if (miResultado.getCodigo()) {
            edtAssets.setText(miResultado.getContenido());
        } else {
            edtAssets.setText("0");
            Toast.makeText(this, "Error al leer " + VALOR + " " + miResultado.getMensaje(), Toast.LENGTH_SHORT).show();
        }


        if (miMemoria.escribirInterna(DATOINTERNO, "7", false, CODIFICACION)) {
            miResultado = miMemoria.leerInterna(DATOINTERNO, CODIFICACION);
            if (miResultado.getCodigo()) {
                edtMemoriaInterna.setText(miResultado.getContenido());
            } else {
                edtMemoriaInterna.setText("0");
                Toast.makeText(this, "Error al leer " + DATOINTERNO + " " + miResultado.getMensaje(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error al esribir " + DATOINTERNO + " " + miResultado.getMensaje(), Toast.LENGTH_SHORT).show();
        }


        if (miMemoria.disponibleEscritura()) {
            if (miMemoria.escribirExterna(DATOEXTERNO, "9", false, CODIFICACION)) {
                miResultado = miMemoria.leerExterna(DATOEXTERNO, CODIFICACION);
                if (miResultado.getCodigo()) {
                    edtMemoriaExterna.setText(miResultado.getContenido());
                } else {
                    edtMemoriaExterna.setText("0");
                    Toast.makeText(this, "Error al leer " + DATOEXTERNO + " " + miResultado.getMensaje(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Error al esribir " + DATOEXTERNO + " " + miResultado.getMensaje(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Memoria externa no disponible " + miResultado.getMensaje(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        String strResRaw = edtResRaw.getText().toString();
        String strAssets = edtAssets.getText().toString();
        String strMemoriaInterna = edtMemoriaInterna.getText().toString();
        String strMemoriaExterna = edtMemoriaExterna.getText().toString();
        int cantidad = 0;
        String operacion, mensaje;

        if (view == btnSuma) {
            try {
                cantidad = Integer.parseInt(strResRaw) + Integer.parseInt(strAssets) + Integer.parseInt(strMemoriaInterna) + Integer.parseInt(strMemoriaExterna);
                operacion = strResRaw + " + " + strAssets + " + " + strMemoriaInterna + " + " + strMemoriaExterna;
            } catch (NumberFormatException e) {
                Log.e("Error", e.getMessage());
                operacion = "0";
                mensaje = e.getMessage();
            }
            txvResultado.setText(String.valueOf(cantidad) + " " + operacion);
            if (miMemoria.disponibleEscritura()) {
                if (miMemoria.escribirExterna(OPERACIONES, operacion + "\n", true, CODIFICACION)) {
                    mensaje = "Operacion escrita correctamente";
                } else {
                    mensaje = "Error al escribir en memoria externa";
                }
            } else {
                mensaje = "Memoria externa no disponible";
            }
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
    }
}
