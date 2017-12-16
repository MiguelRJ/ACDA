package com.example.webview.webview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Abre el navegador del movil con el enlace de la app al darle al boton
 */
public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtURL;
    private Button btnIr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        edtURL=(EditText)findViewById(R.id.edtURL);
        btnIr=(Button)findViewById(R.id.btnIR);
        btnIr.setOnClickListener(this);
        Toast.makeText(this,"Aqui vamos",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view){
        try {
            Uri uri = Uri.parse(edtURL.getText().toString());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);

            try {
                startActivity(intent);
            }catch (
                    Exception a
                    ){
                Toast.makeText(this,"Error en start Activity vamos",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            Toast.makeText(this,"Error en onClick",Toast.LENGTH_SHORT).show();
        }
    }
}
