package com.example.clasewebview.clasewebview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Name extends AppCompatActivity {

    private TextView txvNameVer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        txvNameVer=(TextView)findViewById(R.id.txvNameVer);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        txvNameVer.setText(bundle.getString("message"));

    }
}
