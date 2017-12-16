package com.example.contador.contador;

import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class contador extends AppCompatActivity implements View.OnClickListener {

    TextView txvCuenta;
    TextView txvTiempo;
    Button btnMenos;
    Button btnMas;
    Button btnComenzar;
    int numeroCafes;
    String format = "%1$02d";// para poner los minutos siempre en dos digitos String format = "%1$02d";;;; String.format(format, minutos)
    MediaPlayer sonido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);
        txvCuenta=(TextView)findViewById(R.id.txvCuenta);
        txvTiempo=(TextView)findViewById(R.id.txvTiempo);
        btnMenos=(Button)findViewById(R.id.btnMenos);
        btnMas=(Button)findViewById(R.id.btnMas);
        btnComenzar=(Button)findViewById(R.id.btnComenzar);
        btnMenos.setOnClickListener(this);
        btnMas.setOnClickListener(this);
        btnComenzar.setOnClickListener(this);
        txvTiempo.setText("05:00");
        sonido = MediaPlayer.create(this,R.raw.alerta);
    }

    @Override
    public void onClick(View view) {
        int minutos = (Integer.parseInt(txvTiempo.getText().toString().substring(0,2))); // para obtener los minutos en entero
        if (view == btnMenos){ // si minutos es mayor que 1, es decir 2, podra restar y quedar en 1, pero si es 1 ya no restara mas
            if(minutos > 1) { // el tiempo no podra ponerse a 0, ya que una parada de 0 minutos no tiene sentido
                txvTiempo.setText(String.format(format,--minutos)+":00");
            }
        }
        if (view == btnMas){
            if(minutos < 60) {
                txvTiempo.setText(String.format(format,++minutos)+":00");
            }
        }
        if (view == btnComenzar){ // al comenzar hay que deshabilitar los botones
            btnMenos.setEnabled(false);
            btnMas.setEnabled(false);
            btnComenzar.setEnabled(false);
            MyCountDownTimer timer = new MyCountDownTimer(minutos*60*1000, 1000);
            timer.start();
        }

    }

    public class MyCountDownTimer extends CountDownTimer {

        private long minutos;
        private long segundos;

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            minutos = (millisUntilFinished / 1000) / 60;
            segundos = (millisUntilFinished / 1000) % 60;
            txvTiempo.setText(String.format(format,minutos) + ":" + String.format(format,segundos));
        }

        @Override
        public void onFinish() { // al acabar hay que volver a habilitar los botones
            btnMenos.setEnabled(true);
            btnMas.setEnabled(true);
            btnComenzar.setEnabled(true);
            txvTiempo.setText("05:00");// tambien vovleremos a poner 5 minutos en el contador
            txvCuenta.setText(Integer.toString(++numeroCafes));// y como ha acabado el reloj anadimos un cafe
            sonido.start();//suena la alarta de que ha acabado
        }
    }
}