package com.example.divisas.divisas;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Divisas extends AppCompatActivity {

    double cambio = 0.849119;
    private EditText edtDolares;
    private EditText edtEuros;
    private Button btnConvertir;
    private RadioButton rdbDolarAEuro;
    private RadioButton rdbEuroADolar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divisas);
        edtDolares=(EditText)findViewById(R.id.edtDolares);
        edtEuros=(EditText)findViewById(R.id.edtEuros);
        rdbDolarAEuro=(RadioButton)findViewById(R.id.rdbDolarAEuro);
        rdbEuroADolar=(RadioButton)findViewById(R.id.rdbEuroADolar);
        btnConvertir=(Button)findViewById(R.id.btnConvertir);
        btnConvertir.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (rdbDolarAEuro.isChecked()){
                                                    try {
                                                        edtEuros.setText(convertirAEuros(obtenerValor(edtDolares)));
                                                    }
                                                    catch (Exception e){
                                                        Toast.makeText(Divisas.this, "Erros al introducir $", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                if (rdbEuroADolar.isChecked()){
                                                    try {
                                                        edtDolares.setText(convertirADolares(obtenerValor(edtEuros)));
                                                    }
                                                    catch (Exception e){
                                                        Snackbar.make(view,"Error al introducir €",Snackbar.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                        }
        );

    }

    public String obtenerValor(EditText edt) {
        return edt.getText().toString();
    }

    public String convertirADolares(String cantidad) {
        double valor = Double.parseDouble(cantidad) / cambio;
        return Double.toString(valor);
    }

    public String convertirAEuros(String cantidad) {
        double valor = Double.parseDouble(cantidad) * cambio;
        return Double.toString(valor);
    }
}
