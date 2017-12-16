package com.example.tema1ej.tema1ej;


import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

/**
 * Escribe en un fichero y guarda frases linea a linea, y saca una aleatoriamente
 *
 * @author Miguel Rodriguez Jimenez
 * @version 0.0.1
 * Cuando se intenta desde el emulador da error porque la ruta de la tarjeta SD del emulador no es la misma qu eun movil
 * PROBAR EN TELEFONO MOVIL
 */

public class ConsejosMRJActivity extends Activity implements View.OnClickListener {
    EditText edtGuardarConsejoAMRJ;
    Button btnGuardarConsejoAMRJ,btnSacarConsejoAMRJ;
    TextView txvConsejoAMRJ;
    String nombreFichero = "MRJConsejos"; // el nombre del fichero a modificar sera siempre el mismo
    String fpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+nombreFichero+".txt"; // la ruta tambien
    // La ruta no funciona en emulador, PROBAR EN TELEFONO MOVIL
    OperacionesConFichero OpFi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejos_mrj);
        edtGuardarConsejoAMRJ = (EditText)findViewById(R.id.edtGuardarConsejoAMRJ);
        btnGuardarConsejoAMRJ = (Button)findViewById(R.id.btnGuardarConsejoAMRJ);
        btnSacarConsejoAMRJ = (Button)findViewById(R.id.btnSacarConsejoAMRJ);
        txvConsejoAMRJ = (TextView)findViewById(R.id.txvConsejoAMRJ);
        btnGuardarConsejoAMRJ.setOnClickListener(this);
        btnSacarConsejoAMRJ.setOnClickListener(this);
        OpFi = new OperacionesConFichero();
    }

    @Override
    public void onClick(View view) {
        if (view==btnGuardarConsejoAMRJ){
            String filecontent = edtGuardarConsejoAMRJ.getText().toString();// Obtengo lo escrito en el EditText
            if(OpFi.escribirEnFichero(filecontent,fpath)){// Al intentar escribirlo en el archivo, si de vuelve true, da aviso toast de correcto
                Toast.makeText(getApplicationContext(), filecontent+ " \n Guardado en: "+nombreFichero+".txt", Toast.LENGTH_SHORT).show();
            }else{// Si no da un Toast de error
                Toast.makeText(getApplicationContext(), "I/O error", Toast.LENGTH_SHORT).show();
            }
        }
        if (view==btnSacarConsejoAMRJ){
            String text = OpFi.leerSoloUnaLineaAleatoria(fpath);// Lee del fichero y muestra una sola linea
            if(text != null){
                txvConsejoAMRJ.setText(text);
            }
            else {
                Toast.makeText(getApplicationContext(), "File not Found", Toast.LENGTH_SHORT).show();
                txvConsejoAMRJ.setText(null);
            }
        }
    }
}
