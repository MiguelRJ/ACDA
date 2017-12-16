package com.example.ficherosred;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ficherosred.ayudas.OperacionesFichero;

public class AlarmaActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtTiempo, edtComentario;
    private Button btnGuardar, btnCargar, btnEmpezar;
    private TextView txvAlarma, txvAlarmas;
    int minTiempo=1, minComentario=10, maxAlarmas = 5;
    String nombreFichero = "Alarma";
    String fpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + nombreFichero + ".txt"; // la ruta tambien
    OperacionesFichero OpFi;
    MediaPlayer sonido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);
        edtTiempo = (EditText) findViewById(R.id.edtTiempo);
        edtComentario = (EditText) findViewById(R.id.edtComentario);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);
        btnCargar = (Button) findViewById(R.id.btnCargar);
        btnCargar.setOnClickListener(this);
        btnEmpezar = (Button) findViewById(R.id.btnEmpezar);
        btnEmpezar.setOnClickListener(this);
        btnEmpezar.setEnabled(false);
        txvAlarma = (TextView) findViewById(R.id.txvAlarma);
        txvAlarmas = (TextView) findViewById(R.id.txvAlarmas);
        OpFi = new OperacionesFichero();
        sonido = MediaPlayer.create(this,R.raw.alerta);
    }

    @Override
    public void onClick(View v) {
        if(v == btnGuardar){
            btnCargar.setEnabled(true);
            if (edtTiempo.getText().toString().length() >= minTiempo) {
                String tiempo = String.valueOf(Integer.parseInt(edtTiempo.getText().toString()));
                if (edtComentario.getText().toString().length() >= minComentario) {
                    String comentario = edtComentario.getText().toString();
                    String filecontent = tiempo+","+comentario;
                    if(OpFi.numeroLineasFichero(fpath) <maxAlarmas && OpFi.numeroLineasFichero(fpath) >=0) {
                        if (OpFi.escribirEnFichero(filecontent, fpath)) {// Al intentar escribirlo en el archivo, si de vuelve true, da aviso toast de correcto
                            Toast.makeText(getApplicationContext(), filecontent + " \n Guardado en: " + nombreFichero + ".txt", Toast.LENGTH_SHORT).show();
                        } else {// Si no da un Toast de error
                            Toast.makeText(getApplicationContext(), "I/O error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "El fichero: " + fpath.toString() + " ya contiene el maximo de alarmas: "+ maxAlarmas, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Comentario corto minimo: " + minComentario, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Tiempo corto minimo: " + minTiempo, Toast.LENGTH_SHORT).show();
            }

        }
        if(v == btnCargar){
            String text = OpFi.leerFicheroCompleto(fpath);// Lee del fichero y muestra una sola linea
            if(text != null){
                btnEmpezar.setEnabled(true);
                txvAlarmas.setText(text.replace(","," "));
            }
            else {
                Toast.makeText(getApplicationContext(), "File not Found", Toast.LENGTH_SHORT).show();
                txvAlarmas.setText(null);
            }
        }
        if(v == btnEmpezar){
            //MyCountDownTimer timer = new MyCountDownTimer(minutos*60*1000, 1000);
            //timer.start();
        }
    }

    public class MyCountDownTimer extends CountDownTimer {

        private long minutos;
        private long segundos;
        String format = "%1$02d";

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            minutos = (millisUntilFinished / 1000) / 60;
            segundos = (millisUntilFinished / 1000) % 60;
            txvAlarma.setText(String.format(format,minutos) + ":" + String.format(format,segundos));
        }

        @Override
        public void onFinish() { // al acabar hay que volver a habilitar los botones
            btnCargar.setEnabled(true);
            sonido.start();//suena la alarta de que ha acabado
        }
    }
}
