package com.example.ficheros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.regex.Pattern;

public class Codificacionactivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtFicheroLeerACod, edtFicheroGuardarACod, edtFicheroACod;
    Button btnFicheroLeerACod, btnFicheroGuardarACod;
    RadioButton rdUTF8, rdUTF16, rdISO;
    Memoria miMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codificacion_activity);
        iniciar();
    }

    private void iniciar() {
        edtFicheroLeerACod = (EditText) findViewById(R.id.edtFicheroLeerACod);
        edtFicheroGuardarACod = (EditText) findViewById(R.id.edtFicheroGuardarACod);
        edtFicheroACod = (EditText) findViewById(R.id.edtFicheroACod);
        btnFicheroLeerACod = (Button) findViewById(R.id.btnFicheroLeerACod);
        btnFicheroLeerACod.setOnClickListener(this);
        btnFicheroGuardarACod = (Button) findViewById(R.id.btnFicheroGuardarACod);
        btnFicheroGuardarACod.setOnClickListener(this);
        rdUTF8 = (RadioButton) findViewById(R.id.rdUTF8);
        rdUTF16 = (RadioButton) findViewById(R.id.rdUTF16);
        rdISO = (RadioButton) findViewById(R.id.rdISO);
        miMemoria = new Memoria(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        String ficheroLectura = edtFicheroLeerACod.getText().toString();
        String ficheroContenido = edtFicheroACod.getText().toString();
        String ficheroEscritura = edtFicheroGuardarACod.getText().toString();
        String mensaje = "";
        Resultado resultado;
        String codificacion = "UTF-8";
        if (rdUTF8.isChecked()) {
            codificacion = "UTF-8";
        } else {
            if (rdUTF16.isChecked()) {
                codificacion = "UTF-16";
            } else {
                if (rdISO.isChecked()) {
                    codificacion = "ISO-8859-15";
                }
            }
        }

        if (view == btnFicheroLeerACod) {
            if (miMemoria.disponibleLectura()) {
                resultado = miMemoria.leerExterna(ficheroLectura,codificacion);
                if(resultado.getCodigo()){
                    edtFicheroACod.setText(resultado.getContenido());
                    mensaje = "Fichero leido correctamente";
                } else{
                    mensaje = "Error a leer el fichero " + ficheroLectura + " " + resultado.getMensaje();
                    edtFicheroACod.setText(mensaje);
                }
                Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
            }
            /*new MaterialFilePicker()
                    .withActivity(this)
                    .withRequestCode(1)
                    .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
                    .withFilterDirectories(true) // Set directories filterable (false by default)
                    .withHiddenFiles(true) // Show hidden files and folders
                    .start();*/
        }

        if (view == btnFicheroGuardarACod) {
            if (ficheroEscritura.isEmpty()) {
                mensaje = "Debe introducir un nombre en el fichero a guardar";
            } else {
                if (miMemoria.disponibleEscritura()) {
                    if (miMemoria.escribirExterna(ficheroEscritura, ficheroContenido, false, codificacion)) {
                        mensaje = "Fichero escrito correctamente";
                    } else {
                        mensaje = "Error al escribir el fichero";
                    }
                } else {
                    mensaje = "Memoria externa no disponible";
                }
            }
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Resultado resultado;
        String mensaje;


        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            Toast.makeText(getApplicationContext(), filePath, Toast.LENGTH_SHORT).show();

            if (miMemoria.disponibleLectura()) {
                resultado = miMemoria.leerRutaExterna(filePath,"UTF-8");
                if (resultado.getCodigo()) {
                    edtFicheroACod.setText(resultado.getContenido());
                    mensaje = "Fichero leido correctamente";
                } else {
                    mensaje = "Error a leer el fichero " + filePath;
                    edtFicheroACod.setText(mensaje);
                }
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
