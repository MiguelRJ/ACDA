package com.example.ficherosred;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ficherosred.ayudas.OperacionesFichero;

public class AgendaActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txvLoadText;
    private EditText edtName, edtLastName, edtNumber, edtEmail;
    private Button btnSave, btnLoad;
    private static int minName = 6, minLastName = 8, minNumber = 9;
    String nombreFichero = "Agenda";
    String fpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + nombreFichero + ".txt"; // la ruta tambien
    OperacionesFichero OpFi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        edtName = (EditText) findViewById(R.id.edtName);
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtNumber = (EditText) findViewById(R.id.edtNumber);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        txvLoadText = (TextView) findViewById(R.id.txvLoadText);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);
        OpFi = new OperacionesFichero();
    }

    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            if (edtName.getText().toString().length() >= minName) {
                if (edtLastName.getText().toString().length() >= minLastName) {
                    if (edtNumber.getText().toString().length() >= minNumber) {
                        String filecontent = edtName.getText().toString() + ";" + edtLastName.getText().toString() + ";" + edtNumber.getText().toString() + ";" + edtEmail.getText().toString();
                        if (OpFi.escribirEnFichero(filecontent, fpath)) {// Al intentar escribirlo en el archivo, si de vuelve true, da aviso toast de correcto
                            Toast.makeText(getApplicationContext(), filecontent + " \n Guardado en: " + nombreFichero + ".txt", Toast.LENGTH_SHORT).show();
                        } else {// Si no da un Toast de error
                            Toast.makeText(getApplicationContext(), "I/O error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Numero corto minimo: " + minNumber, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Apellido corto minimo: " + minLastName, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Nombre corto minimo: " + minName, Toast.LENGTH_SHORT).show();
            }
        }
        if (v == btnLoad) {
            String text = OpFi.leerFicheroCompleto(fpath);// Lee del fichero y muestra una sola linea
            if(text != null){
                txvLoadText.setText(text.replace(";"," "));
            }
            else {
                Toast.makeText(getApplicationContext(), "File not Found", Toast.LENGTH_SHORT).show();
                txvLoadText.setText(null);
            }
        }
    }
}
