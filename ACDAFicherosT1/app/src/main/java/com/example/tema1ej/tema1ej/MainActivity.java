package com.example.tema1ej.tema1ej;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnConversorAMain;
    private Button btnWebViewAMain;
    private Button btnContadorAMain;
    private Button btnMRJAMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConversorAMain=(Button)findViewById(R.id.btnConversorAMain);
        btnWebViewAMain=(Button)findViewById(R.id.btnWebViewAMain);
        btnContadorAMain=(Button)findViewById(R.id.btnContadorAMain);
        btnMRJAMain=(Button)findViewById(R.id.btnMRJAMain);
        btnConversorAMain.setOnClickListener(this);
        btnWebViewAMain.setOnClickListener(this);
        btnContadorAMain.setOnClickListener(this);
        btnMRJAMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnConversorAMain){
            startActivity(new Intent(this,DivisasConversorActivity.class));
        }
        if (v == btnWebViewAMain){
            startActivity(new Intent(this,WebViewPrimeraActivity.class));
        }
        if (v == btnContadorAMain){
            startActivity(new Intent(this,ContadorActivity.class));
        }
        if (v == btnMRJAMain){
            startActivity(new Intent(this,ConsejosMRJActivity.class));
        }
    }
}
