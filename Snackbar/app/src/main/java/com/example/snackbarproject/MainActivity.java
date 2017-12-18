package com.example.snackbarproject;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button show, change, parent;
    CoordinatorLayout coordinatorLayout;
    LinearLayout parentLinearLayout;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = (Button) findViewById(R.id.button1);
        show.setOnClickListener(this);
        change = (Button) findViewById(R.id.button2);
        change.setOnClickListener(this);
        parent = (Button) findViewById(R.id.button3);
        parent.setOnClickListener(this);
    coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
    parentLinearLayout = (LinearLayout) findViewById(R.id.parentLinearLayout);
    }

        View.OnClickListener snackbarClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                snackbar.dismiss();
            }
        };

    @Override
    public void onClick(View view) {
    if(view==show)
    {
        snackbar.make(coordinatorLayout,"Mensaje", Snackbar.LENGTH_SHORT).show();

    }else if(view == change)
    {
        snackbar= Snackbar.make(coordinatorLayout, "Mensaje en otro color", Snackbar.LENGTH_INDEFINITE).setAction("Dismiss", snackbarClickListener);
        snackbar.setActionTextColor(Color.RED);
        View view1 = snackbar.getView();
        view1.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
        snackbar.show();

    }else if(view == parent){
        snackbar = Snackbar.make(parentLinearLayout, "Mensaje en el linear layout", Snackbar.LENGTH_INDEFINITE).setAction("Dissmiss",snackbarClickListener);
        View view1 = snackbar.getView();
        view1.setBackgroundColor(Color.GRAY);
        snackbar.show();
    }
    }
}
