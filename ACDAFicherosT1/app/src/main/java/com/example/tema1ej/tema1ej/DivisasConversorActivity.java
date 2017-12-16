package com.example.tema1ej.tema1ej;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import java.io.StringBufferInputStream;

public class DivisasConversorActivity extends AppCompatActivity implements View.OnClickListener{

    double cambio = 0.849119;
    private EditText edtDolaresADiv;
    private EditText edtEurosADiv;
    private RadioButton rdbDolarAEuroADiv;
    private RadioButton rdbEuroADolarADiv;
    private Button btnConvertirADiv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divisas_conversor);
        edtDolaresADiv=(EditText)findViewById(R.id.edtDolares);
        edtEurosADiv=(EditText)findViewById(R.id.edtEuros);
        rdbDolarAEuroADiv=(RadioButton)findViewById(R.id.rdbDolarAEuroADiv);
        rdbEuroADolarADiv=(RadioButton)findViewById(R.id.rdbEuroADolarADiv);
        btnConvertirADiv=(Button)findViewById(R.id.btnConvertirADiv);
        btnConvertirADiv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==btnConvertirADiv){
            if (rdbDolarAEuroADiv.isChecked()){
                try {
                    edtEurosADiv.setText(convertirAEuros(obtenerValor(edtDolaresADiv)));
                }
                catch (Exception e){
                    Toast.makeText(DivisasConversorActivity.this, "Erros al introducir $", Toast.LENGTH_SHORT).show();
                }
            }
            if (rdbEuroADolarADiv.isChecked()){
                try {
                    edtDolaresADiv.setText(convertirADolares(obtenerValor(edtEurosADiv)));
                }
                catch (Exception e){
                    Snackbar.make(v,"Error al introducir €",Snackbar.LENGTH_SHORT).show();
                }
            }
        }
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
