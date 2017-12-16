package com.example.ficherosred;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ficherosred.ayudas.OperacionesFichero;
import com.example.ficherosred.ayudas.RestClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;

public class SubidaActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txvRuta,txvTexto, txvSong;
    private ImageView imgImagen;
    private Button btnPlay, btnPause;
    private Button btnBuscar, btnSubir;
    private ConstraintLayout conPlayer;
    public final static String WEB = "http://alumno.mobi/~alumno/superior/rodriguez/upload.php";
    String rutaFichero;
    OperacionesFichero opFi;
    static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subida);
        txvRuta = (TextView) findViewById(R.id.txvRuta);
        txvTexto = (TextView) findViewById(R.id.txvTexto);
        txvSong = (TextView) findViewById(R.id.txvSong);
        imgImagen = (ImageView) findViewById(R.id.imgImagen);
        conPlayer = (ConstraintLayout) findViewById(R.id.conPlayer);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnPause.setOnClickListener(this);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(this);
        btnSubir = (Button) findViewById(R.id.btnSubir);
        btnSubir.setOnClickListener(this);
        rutaFichero = null;
        opFi = new OperacionesFichero();
        ocultarTodo();
        mediaPlayer = null;
    }

    void ocultarTodo(){
        txvTexto.setVisibility(View.GONE);
        imgImagen.setVisibility(View.GONE);
        conPlayer.setVisibility(View.GONE);
        btnSubir.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == btnBuscar) {
            new MaterialFilePicker()
                    .withActivity(this)
                    .withRequestCode(1)
                    //.withFilter(Pattern.compile(".*\\.(jpg|png|html|mp3|pdf|txt)$")) // Filtering files and directories by file name using regexp
                    //.withFilterDirectories(true) // Set directories filterable (false by default)
                    .withHiddenFiles(true) // Show hidden files and folders
                    .start();
        }
        if (v==btnPlay){
            mediaPlayer.start();
        }
        if (v==btnPause){
            mediaPlayer.pause();
        }
        if (v==btnSubir){
            subida();
        }

    }

    private void subida() {
        final ProgressDialog progreso = new ProgressDialog(this);
        final File myFile;
        Boolean existe = true;
        myFile = new File(rutaFichero);
        RequestParams params = new RequestParams();
        try {
            params.put("fileToUpload", myFile);
            params.put("pass", "12345");
        } catch (FileNotFoundException e) {
            existe = false;
            txvTexto.setText("Error en el fichero: " + e.getMessage());
            //Toast.makeText(this, "Error en el fichero: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (existe)
            RestClient.post(WEB, params, new TextHttpResponseHandler() {
                @Override
                public void onStart() {
                    progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progreso.setMessage("Conectando . . .");
                    progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            RestClient.cancelRequests(getApplicationContext(), true);
                        }
                    });
                    progreso.show();
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(SubidaActivity.this,"Error de subida del fichero: "+myFile.getName().toString(),Toast.LENGTH_LONG).show();
                    progreso.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                    Toast.makeText(SubidaActivity.this,responseString+"\n "+myFile.getName().toString(),Toast.LENGTH_LONG).show();
                    progreso.dismiss();
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            File file = new File(filePath);
            txvRuta.setText(filePath.toString());
            rutaFichero = filePath;
            Toast.makeText(this,"Tamaño: "+String.valueOf(file.length()),Toast.LENGTH_LONG).show();
            ocultarTodo();
            if(mediaPlayer != null){
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
            }
            if( file.getName().toLowerCase().endsWith("txt") || file.getName().toLowerCase().endsWith("html")) {
                txvTexto.setVisibility(View.VISIBLE);
                txvTexto.setText(opFi.leerFicheroCompleto(filePath));
            }
            if( file.getName().toLowerCase().endsWith("mp3")) {
                mediaPlayer = MediaPlayer.create(this, Uri.parse(filePath));
                txvSong.setText(file.getName());
                conPlayer.setVisibility(View.VISIBLE);
                mediaPlayer.start();
            }
            if( file.getName().toLowerCase().endsWith("png") || file.getName().toLowerCase().endsWith("jpg")) {
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                imgImagen.setImageBitmap(bitmap);
                imgImagen.setVisibility(View.VISIBLE);
            }

            btnSubir.setVisibility(View.VISIBLE);
        }
    }

}
