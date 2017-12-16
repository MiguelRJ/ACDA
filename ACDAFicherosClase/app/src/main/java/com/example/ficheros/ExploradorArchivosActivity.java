package com.example.ficheros;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.regex.Pattern;

public class ExploradorArchivosActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int ABRIRFICHERO_REQUEST_CODE = 1;
    private Button botonAbrir;
    private TextView txvInfo;
    Memoria miMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorador_archivos);
        botonAbrir = (Button) findViewById(R.id.btnAbrir);
        txvInfo = (TextView) findViewById(R.id.txvInfo);
        botonAbrir.setOnClickListener(this);
        miMemoria = new Memoria(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file*//*");
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(intent, ABRIRFICHERO_REQUEST_CODE);
        else
            //informar que no hay ninguna aplicación para manejar ficheros
            Toast.makeText(this, "No hay aplicación para manejar ficheros", Toast.LENGTH_SHORT).show();*/
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(1)
                .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
                .withFilterDirectories(true) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == ABRIRFICHERO_REQUEST_CODE)
            if (resultCode == RESULT_OK) {
                // Mostramos en la etiqueta la ruta del archivo seleccionado
                //String ruta = data.getData().getPath();
                //txtInfo.setText(ruta);
            } else
                Toast.makeText(this, "Error: " + resultCode, Toast.LENGTH_SHORT).show();*/

        Resultado resultado;
        String mensaje;

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            Toast.makeText(getApplicationContext(), filePath, Toast.LENGTH_SHORT).show();

            if (miMemoria.disponibleLectura()) {
                resultado = miMemoria.leerRutaExterna(filePath,"UTF-8");
                if (resultado.getCodigo()) {
                    txvInfo.setText(resultado.getContenido());
                    mensaje = "Fichero leido correctamente";
                } else {
                    mensaje = "Error a leer el fichero " + filePath;
                    txvInfo.setText(mensaje);
                }
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
            }
        }
    }
}