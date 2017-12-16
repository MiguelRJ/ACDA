package com.example.tema1ej.tema1ej;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContadorActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txvCuentaACon;
    TextView txvTiempoACon;
    Button btnMenosACon;
    Button btnMasACon;
    Button btnComenzarACon;
    int numeroCafes;
    String format = "%1$02d";// para poner los minutos siempre en dos digitos String format = "%1$02d";;;; String.format(format, minutos)
    MediaPlayer sonido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);
        txvCuentaACon=(TextView)findViewById(R.id.txvCuentaACon);
        txvTiempoACon=(TextView)findViewById(R.id.txvTiempoACon);
        btnMenosACon=(Button)findViewById(R.id.btnMenosACon);
        btnMasACon=(Button)findViewById(R.id.btnMasACon);
        btnComenzarACon=(Button)findViewById(R.id.btnComenzarACon);
        btnMenosACon.setOnClickListener(this);
        btnMasACon.setOnClickListener(this);
        btnComenzarACon.setOnClickListener(this);
        txvTiempoACon.setText("05:00");
        sonido = MediaPlayer.create(this,R.raw.alerta);
    }

    @Override
    public void onClick(View view) {
        int minutos = (Integer.parseInt(txvTiempoACon.getText().toString().substring(0,2))); // para obtener los minutos en entero
        if (view == btnMenosACon){ // si minutos es mayor que 1, es decir 2, podra restar y quedar en 1, pero si es 1 ya no restara mas
            if(minutos > 1) { // el tiempo no podra ponerse a 0, ya que una parada de 0 minutos no tiene sentido
                txvTiempoACon.setText(String.format(format,--minutos)+":00");
            }
        }
        if (view == btnMasACon){
            if(minutos < 60) {
                txvTiempoACon.setText(String.format(format,++minutos)+":00");
            }
        }
        if (view == btnComenzarACon){ // al comenzar hay que deshabilitar los botones
            btnMenosACon.setEnabled(false);
            btnMasACon.setEnabled(false);
            btnComenzarACon.setEnabled(false);
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
            txvTiempoACon.setText(String.format(format,minutos) + ":" + String.format(format,segundos));
        }

        @Override
        public void onFinish() { // al acabar hay que volver a habilitar los botones
            btnMenosACon.setEnabled(true);
            btnMasACon.setEnabled(true);
            btnComenzarACon.setEnabled(true);
            txvTiempoACon.setText("05:00");// tambien vovleremos a poner 5 minutos en el contador
            txvCuentaACon.setText(Integer.toString(++numeroCafes));// y como ha acabado el reloj anadimos un cafe
            sonido.start();//suena la alarta de que ha acabado
        }
    }
}
