package com.example.ficheros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EscribirMemoriaExternaActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtOperando1, edtOperando2;
    TextView txvResultado, txvPropiedades;
    Button btnBoton;
    public final static String NOMBREFICHERO = "resultado.txt";
    Memoria miMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escribir_memoria_externa);
        edtOperando1=(EditText)findViewById(R.id.edtOperando1);
        edtOperando2=(EditText)findViewById(R.id.edtOperando2);
        txvResultado=(TextView)findViewById(R.id.txvResultado);
        txvPropiedades=(TextView)findViewById(R.id.txvPropiedades);
        btnBoton=(Button)findViewById(R.id.btnBoton);
        btnBoton.setOnClickListener(this);
        miMemoria = new Memoria(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        int r;
        String op1 = edtOperando1.getText().toString();
        String op2 = edtOperando2.getText().toString();
        String texto = "0";

        if (view == btnBoton){
            try{
                r = Integer.parseInt(op1) + Integer.parseInt(op2);
                texto = String.valueOf(r);
            }catch (NumberFormatException e) {
                Log.e("Error", e.getMessage());
                texto = "0";
                Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            txvResultado.setText(texto);
            if (miMemoria.disponibleEscritura()) {
                if (miMemoria.escribirExterna(NOMBREFICHERO, texto, false, "UTF-8")) {
                    txvPropiedades.setText(miMemoria.mostrarPropiedadesExterna(NOMBREFICHERO));
                } else {
                    txvPropiedades.setText("Error al escribir en el fichero " + NOMBREFICHERO);
                }
            }else{
                txvPropiedades.setText("La memoria externa no esta disponible o no existe");
            }
        }
    }
}
