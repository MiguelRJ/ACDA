package com.example.ficherosred;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.entity.mime.Header;

public class Subida extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUrl;
    private Button btnSubir;
    private TextView txvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subida);
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        btnSubir = (Button) findViewById(R.id.btnSubir);
        btnSubir.setOnClickListener(this);
        txvInfo = (TextView) findViewById(R.id.txvInfo);

    }

    @Override
    public void onClick(View view) {
        if (view == btnSubir) {
            subida();
        }
    }

    public final static String WEB = "http://alumno.mobi/~alumno/superior/rodriguez/upload.php";

    private void subida() {
        String nombreFichero = edtUrl.getText().toString();
        final ProgressDialog progreso = new ProgressDialog(this);
        File myFile;
        Boolean existe = true;
        myFile = new File(Environment.getExternalStorageDirectory(), nombreFichero);
//File myFile = new File("/path/to/file.png");
        RequestParams params = new RequestParams();
        try {
            params.put("fileToUpload", myFile);
        } catch (FileNotFoundException e) {
            existe = false;
            txvInfo.setText("Error en el fichero: " + e.getMessage());
//Toast.makeText(this, "Error en el fichero: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (existe)
            RestClient.post(WEB, params, new TextHttpResponseHandler() {
                @Override
                public void onStart() {
// called before request is started
                    progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progreso.setMessage("Conectando . . .");
//progreso.setCancelable(false);
                    progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            RestClient.cancelRequests(getApplicationContext(), true);
                        }
                    });
                    progreso.show();
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                    progreso.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                    txvInfo.setText(responseString);
                    progreso.dismiss();
                }
            });
    }
}
